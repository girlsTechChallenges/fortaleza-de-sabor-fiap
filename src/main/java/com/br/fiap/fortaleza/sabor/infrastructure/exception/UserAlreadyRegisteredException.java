package com.br.fiap.fortaleza.sabor.infrastructure.exception;

public class UserAlreadyRegisteredException extends BusinessException {

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }

}
