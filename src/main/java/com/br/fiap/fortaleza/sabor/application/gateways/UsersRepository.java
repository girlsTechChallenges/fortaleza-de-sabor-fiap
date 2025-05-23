package com.br.fiap.fortaleza.sabor.application.gateways;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateRequestDto;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {

    List<User> getAll();

    User save(User user);

    Optional<User> update(Long idUsuario, User user);

    Optional<User> getById(Long idUsuario);

    Optional<User> deleteById(Long idUsuario);
}
