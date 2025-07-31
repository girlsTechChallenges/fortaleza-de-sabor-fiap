package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class MenuNotFoundException extends BusinessException {
    public MenuNotFoundException(String message) {
        super(message);
    }

    public MenuNotFoundException(Long menuId) {
        super(String.format("Menu item with id %d not found", menuId));
    }
}
