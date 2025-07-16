package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.token.Token;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthUseCaseTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtEncoder jwtEncoder;
    @Mock
    private JwtEncoderParameters jwtEncoderParameters;

    @InjectMocks
    private AuthUseCase authUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should validate login successfully")
    void shouldValidateLoginSuccessfully() {
        String email = "test@example.com";
        String password = "senha1234";
        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        User user = new User();
        TypeUser typeUser = new TypeUser("CLINETE");
        user.setEmail(email);
        user.setSenha(hashedPassword);
        user.setTipo(typeUser);

        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getSenha())).thenReturn(true);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("BackendFortalezaSabor")
                .subject(email)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(300))
                .build();

        Jwt mockJwt = mock(Jwt.class);
        when(mockJwt.getTokenValue()).thenReturn("mocked-jwt-token");
        when(jwtEncoder.encode(any())).thenReturn(mockJwt);

        Token token = authUseCase.validateLogin(email, password);

        assertNotNull(token);
        assertNotNull(token.getAccessToken());
    }

    @Test
    @DisplayName("should throw exception when credentials are invalid")
    void shouldThrowExceptionWhenCredentialsAreInvalid() {
        String email = "test@example.com";
        String password = "wrongPassword";
        String hashedPassword = new BCryptPasswordEncoder().encode("senha1234");
        User user = new User();
        user.setEmail(email);
        user.setSenha(hashedPassword);

        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);

        assertThrows(UserCredentialsException.class, () -> authUseCase.validateLogin(email, password));
    }

    @Test
    @DisplayName("should update password successfully")
    void shouldUpdatePasswordSuccessfully() {
        String email = "test@example.com";
        String newPassword = "newPassword123";

        authUseCase.updatePassword(email, newPassword);

        verify(usersRepository).updatePassword(email, newPassword);
    }
}