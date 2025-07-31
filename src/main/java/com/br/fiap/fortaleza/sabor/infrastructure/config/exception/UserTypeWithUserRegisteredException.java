package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class UserTypeWithUserRegisteredException extends RuntimeException {

    public UserTypeWithUserRegisteredException(String typeName) {
        super(String.format("Cannot delete User Type %s: it is associated with one or more users.", typeName));
    }
}
