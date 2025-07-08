package com.br.fiap.fortaleza.sabor.infrastructure.config.exception.typeUser;

public class TypeUserNotFoundException extends RuntimeException {

    public TypeUserNotFoundException(Long typeUserId) {
        super(String.format("Type User %s not found", typeUserId));
    }

    public TypeUserNotFoundException(String nomeTypeUser) {
        super(String.format("Type User %s not found", nomeTypeUser));
    }

}
