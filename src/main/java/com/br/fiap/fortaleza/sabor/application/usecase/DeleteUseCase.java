package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class DeleteUseCase {
    private final UsersRepository usersRepository;

    public DeleteUseCase(UsersRepository usersRepository) { this.usersRepository = usersRepository; }

    public User delete(Long idUsuario) { return usersRepository.deleteById(idUsuario); }

}
