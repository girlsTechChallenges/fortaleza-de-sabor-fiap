package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class UserTypeMismatchException extends BusinessException {
    public UserTypeMismatchException(String message) {
        super(message);
    }

    public UserTypeMismatchException(String expectedType, String actualType) {
        super(String.format("User type mismatch. Expected: %s, but got: %s", expectedType, actualType));
    }
}
