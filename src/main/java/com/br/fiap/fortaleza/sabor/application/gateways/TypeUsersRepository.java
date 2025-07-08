package com.br.fiap.fortaleza.sabor.application.gateways;

import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;

import java.util.List;
import java.util.Optional;

public interface TypeUsersRepository {

    TypeUser save(TypeUser user);

    Optional<TypeUser> update(Long idTypeUser, TypeUser user);

    Optional<TypeUser> deleteById(Long idTypeUser);

    Optional<TypeUser> getById(Long idTypeUser);

    Optional<TypeUser> getByNomeTipo(String nomeTypeUser);

    List<TypeUser> getAll();

}
