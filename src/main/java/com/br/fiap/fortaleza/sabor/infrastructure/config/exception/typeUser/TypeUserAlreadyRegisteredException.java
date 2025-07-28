package com.br.fiap.fortaleza.sabor.infrastructure.config.exception.typeUser;

public class TypeUserAlreadyRegisteredException extends RuntimeException {

    public TypeUserAlreadyRegisteredException(String message) {
        super(message);
    }
}
