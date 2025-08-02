package com.br.fiap.fortaleza.sabor.application.usecase.type;

import com.br.fiap.fortaleza.sabor.application.ports.in.TypeUseCasePort;
import com.br.fiap.fortaleza.sabor.application.ports.out.TypeRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TypeUseCase implements TypeUseCasePort {

    private final TypeRepositoryPort typeRepositoryPort;

    public TypeUseCase(TypeRepositoryPort typeRepositoryPort) {
        this.typeRepositoryPort = typeRepositoryPort;
    }

    @Override
    public TypeUser create(TypeUser typeUser) {
        if (typeUser.getId() != null) {
            throw new IllegalArgumentException("TypeUser ID must be null for creation");
        }
        return typeRepositoryPort.create(typeUser);
    }

    @Override
    public Optional<TypeUser> update(Long id, TypeUser typeUser) {
        return Optional.ofNullable(typeRepositoryPort.update(id, typeUser)
                .orElseThrow(() -> new IllegalArgumentException("User type not found.")));
    }

    @Override
    public Optional<TypeUser> deleteById(Long id) {
        return Optional.ofNullable(typeRepositoryPort.deleteById(id)
                .orElseThrow(() -> new RuntimeException("Type user not found with id: " + id)));
    }

    @Override
    public Optional<TypeUser> getById(Long id) {
        return Optional.ofNullable(typeRepositoryPort.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("User type not found.")));
    }

    @Override
    public List<TypeUser> getAll() {
        return typeRepositoryPort.getAll();
    }
}
