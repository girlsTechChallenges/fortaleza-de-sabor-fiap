package com.br.fiap.fortaleza.sabor.application.ports.in;

import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;

import java.util.List;
import java.util.Optional;

public interface TypeUseCasePort {

    TypeUser create(TypeUser typeUser);

    Optional<TypeUser> update(Long id, TypeUser typeUser);

    Optional<TypeUser> deleteById(Long id);

    Optional<TypeUser> getById(Long id);

    List<TypeUser> getAll();

}
