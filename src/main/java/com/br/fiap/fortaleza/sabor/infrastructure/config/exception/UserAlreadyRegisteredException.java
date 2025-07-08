package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
