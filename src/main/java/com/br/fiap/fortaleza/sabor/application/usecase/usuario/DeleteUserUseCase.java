package com.br.fiap.fortaleza.sabor.application.usecase.usuario;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteUserUseCase {

    private final UsersRepository usersRepository;

    public DeleteUserUseCase(UsersRepository usersRepository) { this.usersRepository = usersRepository; }

    public Optional<User> delete(Long idUser) {
        return usersRepository.deleteById(idUser);
    }
}
