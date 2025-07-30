package com.br.fiap.fortaleza.sabor.application.gateways;

import com.br.fiap.fortaleza.sabor.domain.userType.UserType;

import java.util.List;
import java.util.Optional;

public interface UserTypesRepository {

    UserType save(UserType user);

    Optional<UserType> update(Long idUserType, UserType user);

    Optional<UserType> deleteById(Long idUserType);

    Optional<UserType> getById(Long idUserType);

    Optional<UserType> getByNameType(String nameUserType);

    List<UserType> getAll();

}
