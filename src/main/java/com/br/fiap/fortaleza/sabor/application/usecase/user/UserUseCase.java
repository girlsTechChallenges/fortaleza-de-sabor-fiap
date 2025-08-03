package com.br.fiap.fortaleza.sabor.application.usecase.user;

import com.br.fiap.fortaleza.sabor.application.ports.in.UserUseCasePort;
import com.br.fiap.fortaleza.sabor.application.ports.out.UsersRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserUseCase implements UserUseCasePort {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepositoryPort usersRepositoryPort;

    public UserUseCase(PasswordEncoder passwordEncoder, UsersRepositoryPort usersRepositoryPort) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepositoryPort = usersRepositoryPort;
    }

    @Override
    public List<User> getAll() {
        return usersRepositoryPort.getAll();
    }

    @Override
    public User save(User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return usersRepositoryPort.save(user);
    }

    @Override
    public Optional<User> update(Long idUser, User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return usersRepositoryPort.update(idUser, user);
    }

    @Override
    public Optional<User> getById(Long idUser) {
        return usersRepositoryPort.getById(idUser);
    }

    @Override
    public Optional<User> deleteById(Long idUser) {
        return usersRepositoryPort.deleteById(idUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return usersRepositoryPort.findByEmail(email);
    }

    @Override
    public void updatePassword(String email, String password) {
        usersRepositoryPort.updatePassword(email, password);

    }
}
