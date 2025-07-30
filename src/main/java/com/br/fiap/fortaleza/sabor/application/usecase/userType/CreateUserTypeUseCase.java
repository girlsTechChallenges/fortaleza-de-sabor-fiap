package com.br.fiap.fortaleza.sabor.application.usecase.userType;

import com.br.fiap.fortaleza.sabor.application.gateways.UserTypesRepository;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import org.springframework.stereotype.Component;

@Component
public class CreateUserTypeUseCase {

    private final UserTypesRepository userTypeRepository;

    public CreateUserTypeUseCase(UserTypesRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public UserType save (UserType userType) {
        return userTypeRepository.save(userType);
    }
}
