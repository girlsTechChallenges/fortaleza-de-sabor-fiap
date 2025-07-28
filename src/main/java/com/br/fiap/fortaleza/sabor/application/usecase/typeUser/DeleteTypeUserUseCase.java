package com.br.fiap.fortaleza.sabor.application.usecase.typeUser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteTypeUserUseCase {

    private final TypeUsersRepository typeUserRepository;

    public DeleteTypeUserUseCase(TypeUsersRepository typeUserRepository) { this.typeUserRepository = typeUserRepository; }

    public Optional<TypeUser> delete(Long idTypeUser) {
        return typeUserRepository.deleteById(idTypeUser);
    }
}
