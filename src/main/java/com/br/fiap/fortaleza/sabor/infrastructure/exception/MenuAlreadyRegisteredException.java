package com.br.fiap.fortaleza.sabor.infrastructure.exception;

public class MenuAlreadyRegisteredException extends BusinessException {
    public MenuAlreadyRegisteredException(String message) {
        super(message);
    }

    public MenuAlreadyRegisteredException(String name, Long restaurantId) {
        super(String.format("Menu item with name '%s' already exists for restaurant id %d", name, restaurantId));
    }
}
