package com.br.fiap.fortaleza.sabor.application.usecase.usuario;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.token.Token;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserCredentialsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUserUseCaseTest {

    @InjectMocks
    private AuthUserUseCase authUseCase;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private JwtEncoderParameters jwtEncoderParameters;

    @Test
    @DisplayName("Should validate login successfully")
    void shouldValidateLoginSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String password = "senha1234";
        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        User user = new User();
        user.setEmail(email);
        user.setSenha(hashedPassword);
        user.setTipo(TypeEnum.CLIENTE);

        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getSenha())).thenReturn(true);

        Jwt mockJwt = mock(Jwt.class);
        when(mockJwt.getTokenValue()).thenReturn("mocked-jwt-token");
        when(jwtEncoder.encode(any())).thenReturn(mockJwt);

        // Act
        Token token = authUseCase.validateLogin(email, password);

        // Assert
        assertNotNull(token);
        assertNotNull(token.getAccessToken());
    }

    @Test
    @DisplayName("Should throw exception when credentials are invalid")
    void shouldThrowExceptionWhenCredentialsAreInvalid() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongPassword";
        String hashedPassword = new BCryptPasswordEncoder().encode("senha1234");
        User user = new User();
        user.setEmail(email);
        user.setSenha(hashedPassword);

        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);

        // Act & Assert
        assertThrows(UserCredentialsException.class, () -> authUseCase.validateLogin(email, password));
    }

    @Test
    @DisplayName("Should update password successfully")
    void shouldUpdatePasswordSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String newPassword = "newPassword123";

        // Act
        authUseCase.updatePassword(email, newPassword);

        // Assert
        verify(usersRepository).updatePassword(email, newPassword);
    }
}