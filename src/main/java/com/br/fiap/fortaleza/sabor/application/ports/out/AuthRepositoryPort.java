package com.br.fiap.fortaleza.sabor.application.ports.out;

import com.br.fiap.fortaleza.sabor.domain.model.token.Token;

public interface AuthRepositoryPort {

    Token validateLogin(String email, String password);

    void updatePassword(String email, String password);

}
