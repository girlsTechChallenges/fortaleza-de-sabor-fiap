package com.br.fiap.fortaleza.sabor.application.usecase.typeUser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import org.springframework.stereotype.Component;

@Component
public class CreateTypeUserUseCase {

    private final TypeUsersRepository typeUserRepository;

    public CreateTypeUserUseCase(TypeUsersRepository typeUserRepository) {
        this.typeUserRepository = typeUserRepository;
    }

    public TypeUser save (TypeUser typeUser) {
        return typeUserRepository.save(typeUser);
    }
}
