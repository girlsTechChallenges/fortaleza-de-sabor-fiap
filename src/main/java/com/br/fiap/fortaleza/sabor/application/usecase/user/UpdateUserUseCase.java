package com.br.fiap.fortaleza.sabor.application.usecase.user;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateUserUseCase {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UpdateUserUseCase(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> update(Long idUser, User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return usersRepository.update(idUser, user);
    }
}


