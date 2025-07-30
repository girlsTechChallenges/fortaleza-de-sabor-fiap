package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class UserTypeNotFoundException extends RuntimeException {

    public UserTypeNotFoundException(Long typeUserId) {
        super(String.format("Type User %s not found", typeUserId));
    }

    public UserTypeNotFoundException(String nomeUserType) {
        super(String.format("Type User %s not found", nomeUserType));
    }

}
