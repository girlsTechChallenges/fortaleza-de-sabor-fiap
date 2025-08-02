package com.br.fiap.fortaleza.sabor.application.ports.in;

import com.br.fiap.fortaleza.sabor.domain.model.token.Token;

public interface AuthUseCasePort {

    Token validateLogin(String email, String password);

    void updatePassword(String email, String password);
}
