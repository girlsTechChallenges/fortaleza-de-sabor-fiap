package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class MenuAlreadyRegisteredException extends RuntimeException {
    public MenuAlreadyRegisteredException(String message) {
        super(message);
    }
}
