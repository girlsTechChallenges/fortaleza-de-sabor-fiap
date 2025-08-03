package com.br.fiap.fortaleza.sabor.infrastructure.exception;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(Long userId) {
        super(String.format("User %s not found", userId));
    }

    public UserNotFoundException(String email) {
        super(String.format("User %s not found", email));
    }
}
