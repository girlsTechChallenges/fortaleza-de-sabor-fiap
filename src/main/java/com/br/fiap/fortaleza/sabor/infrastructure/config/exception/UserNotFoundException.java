package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super(String.format("User %s not found", userId));
    }
}
