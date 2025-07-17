package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleValidationExceptions() {
        // Mock FieldError
        FieldError fieldError = new FieldError("objectName", "fieldName", "must not be blank");

        // Mock BindingResult
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        // Mock MethodArgumentNotValidException
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ApiErrorMessage> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getErrors().containsKey("fieldName"));
        assertEquals(List.of("must not be blank"), response.getBody().getErrors().get("fieldName"));
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("Generic error");

        ResponseEntity<Map<String, String>> response = handler.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Generic error", response.getBody().get("error"));
    }

    @Test
    void testHandleAccessDeniedException() {
        AuthorizationDeniedException ex = new AuthorizationDeniedException("Access denied message");

        ResponseEntity<Map<String, String>> response = handler.handleAccessDeniedException(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access Denied", response.getBody().get("error"));
        assertEquals("Access denied message", response.getBody().get("message"));
    }

    @Test
    void testUserCredentialsException() {
        UserCredentialsException ex = new UserCredentialsException("Invalid credentials");

        ResponseEntity<Map<String, String>> response = handler.userCredentialsException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody().get("error"));
        assertEquals("Invalid credentials", response.getBody().get("message"));
    }

    @Test
    void testUserAlreadyRegisteredException() {
        UserAlreadyRegisteredException ex = new UserAlreadyRegisteredException("User already exists");

        ResponseEntity<Map<String, String>> response = handler.eserAlreadyRegisteredException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody().get("error"));
        assertEquals("User already exists", response.getBody().get("message"));
    }
}
