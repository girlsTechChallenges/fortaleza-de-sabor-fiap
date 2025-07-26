package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        // Setup for specific tests that need it
    }

    @Test
    @DisplayName("Should handle UserNotFoundException")
    void shouldHandleUserNotFoundException() {
        // Arrange
        UserNotFoundException exception = new UserNotFoundException(1L);

        // Act
        ResponseEntity<ApiErrorMessage> response = globalExceptionHandler.handleUserNotFoundException(exception, webRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertTrue(response.getBody().getErrors().containsKey("message"));
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException")
    void shouldHandleMethodArgumentNotValidException() {
        // Arrange
        FieldError fieldError1 = new FieldError("user", "name", "Name is required");
        FieldError fieldError2 = new FieldError("user", "email", "Email is invalid");
        
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        // Act
        ResponseEntity<ApiErrorMessage> response = globalExceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());
        assertTrue(response.getBody().getErrors().containsKey("name"));
        assertTrue(response.getBody().getErrors().containsKey("email"));
        assertTrue(response.getBody().getErrors().get("name").contains("Name is required"));
        assertTrue(response.getBody().getErrors().get("email").contains("Email is invalid"));
    }

    @Test
    @DisplayName("Should handle general exceptions")
    void shouldHandleGeneralExceptions() {
        // Arrange
        String errorMessage = "Something went wrong";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<?> response = globalExceptionHandler.handleException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle validation with empty field errors")
    void shouldHandleValidationWithEmptyFieldErrors() {
        // Arrange
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of());

        // Act
        ResponseEntity<ApiErrorMessage> response = globalExceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());
        assertTrue(response.getBody().getErrors().isEmpty());
    }

    @Test
    @DisplayName("Should handle multiple validation errors for same field")
    void shouldHandleMultipleValidationErrorsForSameField() {
        // Arrange
        FieldError fieldError1 = new FieldError("user", "email", "Email is required");
        FieldError fieldError2 = new FieldError("user", "email", "Email is invalid");
        
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        // Act
        ResponseEntity<ApiErrorMessage> response = globalExceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getErrors().containsKey("email"));
        assertEquals(2, response.getBody().getErrors().get("email").size());
        assertTrue(response.getBody().getErrors().get("email").contains("Email is required"));
        assertTrue(response.getBody().getErrors().get("email").contains("Email is invalid"));
    }
}
