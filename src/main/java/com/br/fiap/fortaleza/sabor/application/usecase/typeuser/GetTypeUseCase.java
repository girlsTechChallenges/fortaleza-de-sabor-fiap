package com.br.fiap.fortaleza.sabor.application.usecase.typeuser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.TypeUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetTypeUseCase {

    private final TypeUsersRepository typeUsersRepository;

    public GetTypeUseCase(TypeUsersRepository typeUsersRepository) {
        this.typeUsersRepository = typeUsersRepository;
    }

    public Optional<TypeUser> getById(Long id) {
        return Optional.ofNullable(typeUsersRepository.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("User type not found.")));
    }

    public List<TypeUser> getAll() {
        return typeUsersRepository.getAll();
    }
}
