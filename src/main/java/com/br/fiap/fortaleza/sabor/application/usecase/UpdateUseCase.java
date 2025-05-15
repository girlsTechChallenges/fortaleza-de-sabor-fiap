package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateUseCase {
    private final UsersRepository usersRepository;

    public UpdateUseCase(UsersRepository usersRepository) { this.usersRepository = usersRepository; }

    public User update(Long idUsuario, User userAtualizado) { return usersRepository.update(idUsuario, userAtualizado); }
}
