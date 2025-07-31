package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class RestaurantNotFoundException extends BusinessException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long restaurantId) {
        super(String.format("Restaurant with id %d not found", restaurantId));
    }
}
