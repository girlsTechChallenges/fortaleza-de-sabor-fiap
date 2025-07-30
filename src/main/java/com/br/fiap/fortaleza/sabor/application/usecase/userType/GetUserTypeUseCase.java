package com.br.fiap.fortaleza.sabor.application.usecase.userType;

import com.br.fiap.fortaleza.sabor.application.gateways.UserTypesRepository;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetUserTypeUseCase {

    private final UserTypesRepository userTypesRepository;

    public GetUserTypeUseCase(UserTypesRepository userTypesRepository) {
        this.userTypesRepository = userTypesRepository;
    }

    public List<UserType> getAll() {
        return userTypesRepository.getAll();
    }

    public Optional<UserType> getById(Long idUserType) {
        return userTypesRepository.getById(idUserType);
    }

    public Optional<UserType> getByNameType(String nameUserType) {
        return userTypesRepository.getByNameType(nameUserType);
    }
}
