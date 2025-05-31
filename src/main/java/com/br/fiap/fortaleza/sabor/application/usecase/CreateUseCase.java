package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUseCase {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUseCase(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save (User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return usersRepository.save(user);
    }
}
