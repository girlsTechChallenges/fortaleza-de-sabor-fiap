package com.br.fiap.fortaleza.sabor.application.usecase.userType;

import com.br.fiap.fortaleza.sabor.application.gateways.UserTypesRepository;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteUserTypeUseCase {

    private final UserTypesRepository userTypeRepository;

    public DeleteUserTypeUseCase(UserTypesRepository userTypeRepository) { this.userTypeRepository = userTypeRepository; }

    public Optional<UserType> delete(Long idUserType) {
        return userTypeRepository.deleteById(idUserType);
    }
}
