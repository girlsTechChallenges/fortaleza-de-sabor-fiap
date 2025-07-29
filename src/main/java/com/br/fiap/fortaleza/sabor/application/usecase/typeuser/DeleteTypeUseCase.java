package com.br.fiap.fortaleza.sabor.application.usecase.typeuser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteTypeUseCase {

    private final TypeUsersRepository typeUsersRepository;

    public DeleteTypeUseCase(TypeUsersRepository typeUsersRepository) {
        this.typeUsersRepository = typeUsersRepository;
    }

    public void delete(Long id) {
        typeUsersRepository.deleteById(id)
                .orElseThrow(() -> new RuntimeException("Type user not found with id: " + id));
    }
}
