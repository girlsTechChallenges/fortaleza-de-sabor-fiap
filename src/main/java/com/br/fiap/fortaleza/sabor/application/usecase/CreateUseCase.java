package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class CreateUseCase {

    private final UsersRepository usersRepository;

    public CreateUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User save (User user) {
        return usersRepository.save(user);
    }
}
