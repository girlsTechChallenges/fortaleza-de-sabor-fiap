package com.br.fiap.fortaleza.sabor.application.ports.out;

import com.br.fiap.fortaleza.sabor.domain.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepositoryPort {

    List<User> getAll();

    User save(User user);

    Optional<User> update(Long idUser, User user);

    Optional<User> getById(Long idUser);

    Optional<User> deleteById(Long idUser);

    Optional<User> findByEmail(String email);

    void updatePassword(String email, String password);
}
