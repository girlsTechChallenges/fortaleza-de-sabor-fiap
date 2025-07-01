package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    @DisplayName("Should create exception with user ID")
    void shouldCreateExceptionWithUserId() {
        // Given
        Long userId = 123L;

        // When
        UserNotFoundException exception = new UserNotFoundException(userId);

        // Then
        assertNotNull(exception);
        assertEquals("User 123 not found", exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should create exception with email")
    void shouldCreateExceptionWithEmail() {
        // Given
        String email = "user@example.com";

        // When
        UserNotFoundException exception = new UserNotFoundException(email);

        // Then
        assertNotNull(exception);
        assertEquals("User user@example.com not found", exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should handle null user ID")
    void shouldHandleNullUserId() {
        // Given
        Long userId = null;

        // When
        UserNotFoundException exception = new UserNotFoundException(userId);

        // Then
        assertNotNull(exception);
        assertEquals("User null not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle null email")
    void shouldHandleNullEmail() {
        // Given
        String email = null;

        // When
        UserNotFoundException exception = new UserNotFoundException(email);

        // Then
        assertNotNull(exception);
        assertEquals("User null not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle empty email")
    void shouldHandleEmptyEmail() {
        // Given
        String email = "";

        // When
        UserNotFoundException exception = new UserNotFoundException(email);

        // Then
        assertNotNull(exception);
        assertEquals("User  not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle zero user ID")
    void shouldHandleZeroUserId() {
        // Given
        Long userId = 0L;

        // When
        UserNotFoundException exception = new UserNotFoundException(userId);

        // Then
        assertNotNull(exception);
        assertEquals("User 0 not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle negative user ID")
    void shouldHandleNegativeUserId() {
        // Given
        Long userId = -1L;

        // When
        UserNotFoundException exception = new UserNotFoundException(userId);

        // Then
        assertNotNull(exception);
        assertEquals("User -1 not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle very large user ID")
    void shouldHandleVeryLargeUserId() {
        // Given
        Long userId = Long.MAX_VALUE;

        // When
        UserNotFoundException exception = new UserNotFoundException(userId);

        // Then
        assertNotNull(exception);
        assertEquals("User " + Long.MAX_VALUE + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle complex email format")
    void shouldHandleComplexEmailFormat() {
        // Given
        String email = "user.name+tag@example.co.uk";

        // When
        UserNotFoundException exception = new UserNotFoundException(email);

        // Then
        assertNotNull(exception);
        assertEquals("User user.name+tag@example.co.uk not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should be throwable")
    void shouldBeThrowable() {
        // Given
        Long userId = 456L;

        // When & Then
        assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException(userId);
        });
    }

    @Test
    @DisplayName("Should maintain exception cause chain")
    void shouldMaintainExceptionCauseChain() {
        // Given
        Long userId = 789L;
        Throwable cause = new RuntimeException("Original cause");

        // When
        UserNotFoundException exception = new UserNotFoundException(userId);
        exception.initCause(cause);

        // Then
        assertEquals(cause, exception.getCause());
        assertEquals("User 789 not found", exception.getMessage());
    }
}
