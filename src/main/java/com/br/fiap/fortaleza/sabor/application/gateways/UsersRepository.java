package com.br.fiap.fortaleza.sabor.application.gateways;

import com.br.fiap.fortaleza.sabor.domain.user.User;

import java.util.List;

public interface UsersRepository {

    List<User> getAll();

    Long save(User user);
}
