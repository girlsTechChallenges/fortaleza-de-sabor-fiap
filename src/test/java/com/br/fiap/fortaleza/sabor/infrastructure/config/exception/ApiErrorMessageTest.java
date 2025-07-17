package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorMessageTest {

    @Test
    void shouldCreateApiErrorMessageWithMap() {
        Map<String, List<String>> errors = Map.of(
                "name", List.of("must not be empty"),
                "email", List.of("invalid format")
        );

        ApiErrorMessage errorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, errors);

        assertEquals(HttpStatus.BAD_REQUEST, errorMessage.getStatus());
        assertEquals(2, errorMessage.getErrors().size());
        assertEquals(List.of("must not be empty"), errorMessage.getErrors().get("name"));
        assertEquals(List.of("invalid format"), errorMessage.getErrors().get("email"));
    }

    @Test
    void shouldCreateApiErrorMessageWithSingleFieldError() {
        ApiErrorMessage errorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, "user", "User not found");

        assertEquals(HttpStatus.NOT_FOUND, errorMessage.getStatus());
        assertEquals(1, errorMessage.getErrors().size());
        assertEquals(List.of("User not found"), errorMessage.getErrors().get("user"));
    }

    @Test
    void shouldCreateApiErrorMessageWithNullErrorMap() {
        ApiErrorMessage errorMessage = new ApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage.getStatus());
        assertNotNull(errorMessage.getErrors());
        assertTrue(errorMessage.getErrors().isEmpty());
    }
}
