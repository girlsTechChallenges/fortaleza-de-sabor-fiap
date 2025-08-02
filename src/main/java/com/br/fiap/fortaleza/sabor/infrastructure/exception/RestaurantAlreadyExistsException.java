package com.br.fiap.fortaleza.sabor.infrastructure.exception;

public class RestaurantAlreadyExistsException extends BusinessException {
    public RestaurantAlreadyExistsException(String message) {
        super(message);
    }

    public RestaurantAlreadyExistsException(String name, String cnpj) {
        super(String.format("Restaurant with name '%s' or CNPJ '%s' already exists", name, cnpj));
    }
}
