package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class GetByIdUseCase {
    private final UsersRepository usersRepository;

    public GetByIdUseCase(UsersRepository usersRepository) { this.usersRepository = usersRepository; }

    public User getById(Long idUsuario) { return usersRepository.getById(idUsuario); }

}
