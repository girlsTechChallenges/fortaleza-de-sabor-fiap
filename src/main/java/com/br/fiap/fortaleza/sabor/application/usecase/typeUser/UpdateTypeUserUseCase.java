package com.br.fiap.fortaleza.sabor.application.usecase.typeUser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateTypeUserUseCase {
    private final TypeUsersRepository typeUserRepository;

    public UpdateTypeUserUseCase(TypeUsersRepository typeUserRepository) {
        this.typeUserRepository = typeUserRepository;
    }

    public Optional<TypeUser> update(Long idTypeUser, TypeUser typeUser) {
        return typeUserRepository.update(idTypeUser, typeUser);
    }
}


