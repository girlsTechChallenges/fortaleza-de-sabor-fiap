package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;

import java.util.List;

public class UsersUseCase {

    private final UsersRepository usersRepository;

    public UsersUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> getAll() {
        return usersRepository.getAll();
    }

    public Long save (User user) {
        return usersRepository.save(user);
    }
}
