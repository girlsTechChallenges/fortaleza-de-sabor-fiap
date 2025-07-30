package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class UserTypeAlreadyRegisteredException extends RuntimeException {

    public UserTypeAlreadyRegisteredException(String message) {
        super(message);
    }
}
