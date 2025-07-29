package com.br.fiap.fortaleza.sabor.application.usecase.typeuser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.TypeUser;
import org.springframework.stereotype.Component;

@Component
public class CreateTypeUseCase {

    private final TypeUsersRepository typeUsersRepository;

    public CreateTypeUseCase(TypeUsersRepository typeUsersRepository) {
        this.typeUsersRepository = typeUsersRepository;
    }

    public TypeUser save(TypeUser typeUser) {
        if (typeUser.getId() != null) {
            throw new IllegalArgumentException("TypeUser ID must be null for creation");
        }
        return typeUsersRepository.create(typeUser);
    }
}
