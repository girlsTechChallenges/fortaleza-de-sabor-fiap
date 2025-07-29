package com.br.fiap.fortaleza.sabor.application.gateways;

import com.br.fiap.fortaleza.sabor.domain.user.TypeUser;

import java.util.List;
import java.util.Optional;

public interface TypeUsersRepository {

    TypeUser create(TypeUser typeUser);

    Optional<TypeUser> update(Long id, TypeUser typeUser);

    Optional<TypeUser> deleteById(Long id);

    Optional<TypeUser> getById(Long id);

    List<TypeUser> getAll();

}
