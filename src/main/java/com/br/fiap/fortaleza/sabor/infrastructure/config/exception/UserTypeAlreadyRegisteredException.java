package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class UserTypeAlreadyRegisteredException extends RuntimeException {

    public UserTypeAlreadyRegisteredException(String typeName) {
        super(String.format("User Type %s already exists", typeName));
    }
}
