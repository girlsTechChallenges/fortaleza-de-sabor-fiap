package com.br.fiap.fortaleza.sabor.infrastructure.config.exception;

public class UserTypeNotFoundException extends RuntimeException {

    public UserTypeNotFoundException(Long typeUserId) {
        super(String.format("User Type %s not found", typeUserId));
    }

    public UserTypeNotFoundException(String nomeUserType) {
        super(String.format("User Type %s not found", nomeUserType));
    }

}
