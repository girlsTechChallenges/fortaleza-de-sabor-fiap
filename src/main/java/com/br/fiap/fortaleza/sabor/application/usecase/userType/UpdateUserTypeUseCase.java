package com.br.fiap.fortaleza.sabor.application.usecase.userType;

import com.br.fiap.fortaleza.sabor.application.gateways.UserTypesRepository;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateUserTypeUseCase {
    private final UserTypesRepository userTypeRepository;

    public UpdateUserTypeUseCase(UserTypesRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public Optional<UserType> update(Long idUserType, UserType userType) {
        return userTypeRepository.update(idUserType, userType);
    }
}


