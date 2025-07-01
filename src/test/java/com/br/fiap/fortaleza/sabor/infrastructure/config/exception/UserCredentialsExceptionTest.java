package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCredentialsExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void shouldCreateExceptionWithMessage() {
        // Given
        String message = "Invalid email or password";

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should handle null message")
    void shouldHandleNullMessage() {
        // Given
        String message = null;

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should handle empty message")
    void shouldHandleEmptyMessage() {
        // Given
        String message = "";

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle whitespace only message")
    void shouldHandleWhitespaceOnlyMessage() {
        // Given
        String message = "   ";

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertNotNull(exception);
        assertEquals("   ", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle long message")
    void shouldHandleLongMessage() {
        // Given
        String message = "This is a very long error message that describes in detail what went wrong with the user credentials validation process and provides comprehensive information about the failure";

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should handle message with special characters")
    void shouldHandleMessageWithSpecialCharacters() {
        // Given
        String message = "Credenciais inválidas: usuário não encontrado! @#$%^&*()";

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should handle message with line breaks")
    void shouldHandleMessageWithLineBreaks() {
        // Given
        String message = "Line 1\nLine 2\rLine 3\r\nLine 4";

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should be throwable")
    void shouldBeThrowable() {
        // Given
        String message = "Authentication failed";

        // When & Then
        assertThrows(UserCredentialsException.class, () -> {
            throw new UserCredentialsException(message);
        });
    }

    @Test
    @DisplayName("Should maintain exception cause chain")
    void shouldMaintainExceptionCauseChain() {
        // Given
        String message = "Credentials error";
        Throwable cause = new RuntimeException("Database connection failed");

        // When
        UserCredentialsException exception = new UserCredentialsException(message);
        exception.initCause(cause);

        // Then
        assertEquals(cause, exception.getCause());
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        // Given
        String message = "Test message";

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    @DisplayName("Should have proper inheritance hierarchy")
    void shouldHaveProperInheritanceHierarchy() {
        // Given
        String message = "Test credentials error";

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
        assertEquals(UserCredentialsException.class, exception.getClass());
    }

    @Test
    @DisplayName("Should handle typical authentication failure message")
    void shouldHandleTypicalAuthenticationFailureMessage() {
        // Given
        String message = "Invalid username or password. Please check your credentials and try again.";

        // When
        UserCredentialsException exception = new UserCredentialsException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("Invalid"));
        assertTrue(exception.getMessage().contains("password"));
    }
}
