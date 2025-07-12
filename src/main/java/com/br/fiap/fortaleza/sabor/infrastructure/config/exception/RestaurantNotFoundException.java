package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
