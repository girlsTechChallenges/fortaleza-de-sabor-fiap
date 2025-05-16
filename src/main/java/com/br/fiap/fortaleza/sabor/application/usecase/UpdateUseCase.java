package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UpdateUseCase {
    private final UsersRepository usersRepository;

    public UpdateUseCase(UsersRepository usersRepository) { this.usersRepository = usersRepository; }

    public Optional<User> update(Long idUsuario, User userAtualizado) { return usersRepository.update(idUsuario, userAtualizado); }

}
