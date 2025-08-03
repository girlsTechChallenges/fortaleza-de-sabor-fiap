package com.br.fiap.fortaleza.sabor.application.usecase.auth;

import com.br.fiap.fortaleza.sabor.application.ports.out.UsersRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.token.Token;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthUseCase Tests")
class AuthUseCaseTest {

    @Mock
    private UsersRepositoryPort usersRepositoryPort;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private AuthUseCase authUseCase;

    private User user;
    private final String email = "joao@test.com";
    private final String password = "password123";
    private final String encodedPassword = "$2a$10$encodedPasswordHash";
    private final String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0In0.test";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setNome("João Silva");
        user.setEmail(email);
        user.setLogin("joao123");
        user.setSenha(encodedPassword);
        user.setDataAlteracao(LocalDate.now());
        user.setTipo("CLIENTE");
    }

    @Test
    @DisplayName("Should generate token when validateLogin is called with valid credentials")
    void shouldGenerateTokenWhenValidateLoginIsCalledWithValidCredentials() {
        // Arrange
        when(usersRepositoryPort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwt.getTokenValue()).thenReturn(jwtToken);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        // Act
        Token result = authUseCase.validateLogin(email, password);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isEqualTo(jwtToken);
        assertThat(result.getExpiresIn()).isEqualTo(300L);
        
        verify(usersRepositoryPort, times(2)).findByEmail(email); // Called twice: once for user validation, once for password check
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(jwtEncoder, times(1)).encode(any(JwtEncoderParameters.class));
    }

    @Test
    @DisplayName("Should throw UserCredentialsException when validateLogin is called with invalid email")
    void shouldThrowUserCredentialsExceptionWhenValidateLoginIsCalledWithInvalidEmail() {
        // Arrange
        String invalidEmail = "invalid@test.com";
        when(usersRepositoryPort.findByEmail(invalidEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authUseCase.validateLogin(invalidEmail, password))
                .isInstanceOf(UserCredentialsException.class)
                .hasMessage("Invalid email or password");
        
        verify(usersRepositoryPort, times(1)).findByEmail(invalidEmail);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtEncoder, never()).encode(any(JwtEncoderParameters.class));
    }

    @Test
    @DisplayName("Should throw UserCredentialsException when validateLogin is called with invalid password")
    void shouldThrowUserCredentialsExceptionWhenValidateLoginIsCalledWithInvalidPassword() {
        // Arrange
        String invalidPassword = "wrongpassword";
        when(usersRepositoryPort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(invalidPassword, encodedPassword)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> authUseCase.validateLogin(email, invalidPassword))
                .isInstanceOf(UserCredentialsException.class)
                .hasMessage("Invalid email or password");
        
        verify(usersRepositoryPort, times(2)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(invalidPassword, encodedPassword);
        verify(jwtEncoder, never()).encode(any(JwtEncoderParameters.class));
    }

    @Test
    @DisplayName("Should call updatePassword when updatePassword is called")
    void shouldCallUpdatePasswordWhenUpdatePasswordIsCalled() {
        // Arrange
        String newPassword = "newpassword456";
        doNothing().when(usersRepositoryPort).updatePassword(email, newPassword);

        // Act
        authUseCase.updatePassword(email, newPassword);

        // Assert
        verify(usersRepositoryPort, times(1)).updatePassword(email, newPassword);
    }

    @Test
    @DisplayName("Should generate token with correct claims when validateLogin is called with admin user")
    void shouldGenerateTokenWithCorrectClaimsWhenValidateLoginIsCalledWithAdminUser() {
        // Arrange
        User adminUser = new User();
        adminUser.setNome("Admin User");
        adminUser.setEmail(email);
        adminUser.setLogin("admin123");
        adminUser.setSenha(encodedPassword);
        adminUser.setDataAlteracao(LocalDate.now());
        adminUser.setTipo("ADMIN");

        when(usersRepositoryPort.findByEmail(email)).thenReturn(Optional.of(adminUser));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwt.getTokenValue()).thenReturn(jwtToken);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        // Act
        Token result = authUseCase.validateLogin(email, password);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isEqualTo(jwtToken);
        assertThat(result.getExpiresIn()).isEqualTo(300L);
        
        verify(jwtEncoder).encode(argThat(params -> {
            JwtClaimsSet claims = params.getClaims();
            return claims.getSubject().equals(email) &&
                   claims.getClaim("scope").equals("ADMIN");
        }));
    }

    @Test
    @DisplayName("Should handle password verification correctly when user exists")
    void shouldHandlePasswordVerificationCorrectlyWhenUserExists() {
        // Arrange
        when(usersRepositoryPort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwt.getTokenValue()).thenReturn(jwtToken);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        // Act
        Token result = authUseCase.validateLogin(email, password);

        // Assert
        assertThat(result).isNotNull();
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
    }

    @Test
    @DisplayName("Should throw UserCredentialsException when password encoder returns false")
    void shouldThrowUserCredentialsExceptionWhenPasswordEncoderReturnsFalse() {
        // Arrange
        when(usersRepositoryPort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> authUseCase.validateLogin(email, password))
                .isInstanceOf(UserCredentialsException.class)
                .hasMessage("Invalid email or password");
    }

    @Test
    @DisplayName("Should handle empty password correctly")
    void shouldHandleEmptyPasswordCorrectly() {
        // Arrange
        String emptyPassword = "";
        when(usersRepositoryPort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(emptyPassword, encodedPassword)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> authUseCase.validateLogin(email, emptyPassword))
                .isInstanceOf(UserCredentialsException.class)
                .hasMessage("Invalid email or password");
        
        verify(passwordEncoder, times(1)).matches(emptyPassword, encodedPassword);
    }

    @Test
    @DisplayName("Should handle null password correctly")
    void shouldHandleNullPasswordCorrectly() {
        // Arrange
        String nullPassword = null;
        when(usersRepositoryPort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(nullPassword, encodedPassword)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> authUseCase.validateLogin(email, nullPassword))
                .isInstanceOf(UserCredentialsException.class)
                .hasMessage("Invalid email or password");
        
        verify(passwordEncoder, times(1)).matches(nullPassword, encodedPassword);
    }
}
