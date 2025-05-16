package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetByIdUseCase {
    private final UsersRepository usersRepository;

    public GetByIdUseCase(UsersRepository usersRepository) { this.usersRepository = usersRepository; }

    public Optional<User> getById(Long idUsuario) { return usersRepository.getById(idUsuario); }

}
