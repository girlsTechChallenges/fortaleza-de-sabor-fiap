package com.br.fiap.fortaleza.sabor.application.usecase.typeUser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetTypeUserUseCase {

    private final TypeUsersRepository typeUsersRepository;

    public GetTypeUserUseCase(TypeUsersRepository typeUsersRepository) {
        this.typeUsersRepository = typeUsersRepository;
    }

    public List<TypeUser> getAll() {
        return typeUsersRepository.getAll();
    }

    public Optional<TypeUser> getById(Long idTypeUser) {
        return typeUsersRepository.getById(idTypeUser);
    }
}
