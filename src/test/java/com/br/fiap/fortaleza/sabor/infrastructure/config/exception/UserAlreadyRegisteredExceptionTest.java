package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAlreadyRegisteredExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void shouldCreateExceptionWithMessage() {
        // Given
        String message = "This user already exists. Check your credentials or recover your password.";

        // When
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

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
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

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
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

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
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

        // Then
        assertNotNull(exception);
        assertEquals("   ", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle long message")
    void shouldHandleLongMessage() {
        // Given
        String message = "The user you are trying to register already exists in our system. This could happen if you previously created an account with the same email address. Please check your credentials or use the password recovery feature to regain access to your existing account.";

        // When
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should handle message with special characters")
    void shouldHandleMessageWithSpecialCharacters() {
        // Given
        String message = "Usuário já cadastrado! Verifique suas credenciais @ #$%^&*()";

        // When
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should handle message with line breaks")
    void shouldHandleMessageWithLineBreaks() {
        // Given
        String message = "User already registered.\nPlease check your email.\rTry password recovery.";

        // When
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should be throwable")
    void shouldBeThrowable() {
        // Given
        String message = "User registration conflict";

        // When & Then
        assertThrows(UserAlreadyRegisteredException.class, () -> {
            throw new UserAlreadyRegisteredException(message);
        });
    }

    @Test
    @DisplayName("Should maintain exception cause chain")
    void shouldMaintainExceptionCauseChain() {
        // Given
        String message = "Registration failed";
        Throwable cause = new RuntimeException("Database constraint violation");

        // When
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);
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
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

        // Then
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    @DisplayName("Should have proper inheritance hierarchy")
    void shouldHaveProperInheritanceHierarchy() {
        // Given
        String message = "Test registration error";

        // When
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

        // Then
        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
        assertEquals(UserAlreadyRegisteredException.class, exception.getClass());
    }

    @Test
    @DisplayName("Should handle typical registration conflict message")
    void shouldHandleTypicalRegistrationConflictMessage() {
        // Given
        String message = "A user with this email address already exists. Please use a different email or sign in to your existing account.";

        // When
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("already exists"));
        assertTrue(exception.getMessage().contains("email"));
    }

    @Test
    @DisplayName("Should handle email-specific conflict message")
    void shouldHandleEmailSpecificConflictMessage() {
        // Given
        String email = "user@example.com";
        String message = String.format("User with email %s is already registered", email);

        // When
        UserAlreadyRegisteredException exception = new UserAlreadyRegisteredException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains(email));
        assertTrue(exception.getMessage().contains("already registered"));
    }
}
