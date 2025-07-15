package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException(String message) {
        super(message);
    }
}
