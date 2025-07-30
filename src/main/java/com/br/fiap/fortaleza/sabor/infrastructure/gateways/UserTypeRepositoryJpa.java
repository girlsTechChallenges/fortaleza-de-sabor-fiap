package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.UserTypesRepository;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.*;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserTypeEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserTypeEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTypeRepositoryJpa implements UserTypesRepository {

    private static final Logger log = LoggerFactory.getLogger(UserTypeRepositoryJpa.class);

    private final UserTypeRepository userTypeRepository;
    private final UserTypeEntityMapper mapper;

    public UserTypeRepositoryJpa(UserTypeRepository userTypeRepository, UserTypeEntityMapper mapper) {
        this.userTypeRepository = userTypeRepository;
        this.mapper = mapper;
    }

    @Override
    public UserType save(UserType userType) {
        userTypeRepository.getByNameType(userType.getNameType())
                .ifPresent(existingUserType -> {
                    throw new UserTypeAlreadyRegisteredException(
                            "This userType " + userType.getNameType() + " already exists."
                    );
                });
        UserTypeEntity userTypeEntity = mapper.toUserTypeEntity(userType);
        return mapper.toUserTypeDomain(userTypeRepository.save(userTypeEntity));
    }

    @Override
    public Optional<UserType> update(Long idUserType, UserType userType) {
        userTypeRepository.getByNameType(userType.getNameType())
                .ifPresent(existingUserType -> {
                    throw new UserTypeAlreadyRegisteredException(
                            "This userType " + userType.getNameType() + " already exists."
                    );
                });

        UserTypeEntity findUserType = userTypeRepository.findById(idUserType)
                .orElseThrow(() -> new UserTypeNotFoundException(idUserType));

        findUserType.setType(userType.getNameType());

        UserTypeEntity actualization = userTypeRepository.save(findUserType);
        return Optional.ofNullable(mapper.toUserTypeDomain(actualization));
    }

    @Override
    public Optional<UserType> deleteById(Long idUserType) {
        UserTypeEntity findUserType = userTypeRepository.findById(idUserType)
                .orElseThrow(() -> new UserTypeNotFoundException(idUserType));

        Optional<UserTypeEntity> userType = userTypeRepository.findById(idUserType);
        userTypeRepository.deleteById(idUserType);
        return userType.map(mapper::toUserTypeDomain);
    }

    @Override
    public Optional<UserType> getById(Long idUserType) {
        var findUserType = userTypeRepository.findById(idUserType)
                .orElseThrow(() -> new UserTypeNotFoundException(idUserType));

        return Optional.ofNullable(mapper.toUserTypeDomain(findUserType));
    }

    @Override
    public Optional<UserType> getByNameType(String nameUserType) {
        Optional<UserTypeEntity> findUserType = userTypeRepository.getByNameType(nameUserType);

        return findUserType.map(mapper::toUserTypeDomain);
    }

    @Override
    public List<UserType> getAll() {
        return userTypeRepository.findAll().stream().map(mapper::toUserTypeDomain).toList();
    }

}