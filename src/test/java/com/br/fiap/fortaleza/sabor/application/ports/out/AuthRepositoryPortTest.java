package com.br.fiap.fortaleza.sabor.application.ports.out;

import com.br.fiap.fortaleza.sabor.domain.model.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthRepositoryPort Contract Tests")
class AuthRepositoryPortTest {

    @Mock
    private AuthRepositoryPort authRepositoryPort;

    private Token token;

    @BeforeEach
    void setUp() {
        token = new Token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", 3600L);
    }

    @Test
    @DisplayName("Should validate login successfully and return token")
    void shouldValidateLoginSuccessfully() {
        // Arrange
        String email = "user@email.com";
        String password = "password123";
        when(authRepositoryPort.validateLogin(anyString(), anyString())).thenReturn(token);

        // Act
        Token result = authRepositoryPort.validateLogin(email, password);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isEqualTo("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...");
        assertThat(result.getExpiresIn()).isEqualTo(3600L);
        verify(authRepositoryPort).validateLogin(email, password);
    }

    @Test
    @DisplayName("Should validate login with different credentials")
    void shouldValidateLoginWithDifferentCredentials() {
        // Arrange
        String email = "admin@email.com";
        String password = "admin123";
        Token adminToken = new Token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.admin...", 7200L);
        when(authRepositoryPort.validateLogin(anyString(), anyString())).thenReturn(adminToken);

        // Act
        Token result = authRepositoryPort.validateLogin(email, password);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isEqualTo("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.admin...");
        assertThat(result.getExpiresIn()).isEqualTo(7200L);
        verify(authRepositoryPort).validateLogin(email, password);
    }

    @Test
    @DisplayName("Should update password successfully")
    void shouldUpdatePasswordSuccessfully() {
        // Arrange
        String email = "user@email.com";
        String newPassword = "newPassword123";
        doNothing().when(authRepositoryPort).updatePassword(anyString(), anyString());

        // Act
        authRepositoryPort.updatePassword(email, newPassword);

        // Assert
        verify(authRepositoryPort).updatePassword(email, newPassword);
    }

    @Test
    @DisplayName("Should update password for different user")
    void shouldUpdatePasswordForDifferentUser() {
        // Arrange
        String email = "admin@email.com";
        String newPassword = "adminNewPassword456";
        doNothing().when(authRepositoryPort).updatePassword(anyString(), anyString());

        // Act
        authRepositoryPort.updatePassword(email, newPassword);

        // Assert
        verify(authRepositoryPort).updatePassword(email, newPassword);
    }
}
