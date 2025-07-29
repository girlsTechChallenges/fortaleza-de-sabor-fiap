package com.br.fiap.fortaleza.sabor.application.usecase.typeuser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.TypeUser;
import org.springframework.stereotype.Component;

@Component
public class UpdateTypeUseCase {

    private final TypeUsersRepository typeUsersRepository;

    public UpdateTypeUseCase(TypeUsersRepository typeUsersRepository) {
        this.typeUsersRepository = typeUsersRepository;
    }

    public TypeUser update(Long id, TypeUser typeUser) {
        return typeUsersRepository.update(id, typeUser)
                .orElseThrow(() -> new IllegalArgumentException("User type not found."));
    }

}
