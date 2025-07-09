package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.typeUser.*;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.TypeUserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.TypeUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeUserRepositoryJpa implements TypeUsersRepository {

    private static final Logger log = LoggerFactory.getLogger(TypeUserRepositoryJpa.class);

    private final TypeUserRepository typeUserRepository;
    private final TypeUserEntityMapper mapper;

    public TypeUserRepositoryJpa(TypeUserRepository typeUserRepository, TypeUserEntityMapper mapper) {
        this.typeUserRepository = typeUserRepository;
        this.mapper = mapper;
    }

    @Override
    public TypeUser save(TypeUser typeUser) {
        typeUserRepository.getByNameType(typeUser.getNameType())
                .ifPresent(existingTypeUser -> {
                    throw new TypeUserAlreadyRegisteredException(
                            "This typeUser " + typeUser.getNameType() + " already exists."
                    );
                });
        TypeUserEntity typeUserEntity = mapper.toTypeUserEntity(typeUser);
        return mapper.toTypeUserDomain(typeUserRepository.save(typeUserEntity));
    }

    @Override
    public Optional<TypeUser> update(Long idTypeUser, TypeUser typeUser) {
        TypeUserEntity findTypeUser = typeUserRepository.findById(idTypeUser)
                .orElseThrow(() -> new TypeUserNotFoundException(idTypeUser));

        if (typeUser != null) {
            findTypeUser.setType(typeUser.getNameType());
        }

        TypeUserEntity actualization = typeUserRepository.save(findTypeUser);
        return Optional.ofNullable(mapper.toTypeUserDomain(actualization));
    }

    @Override
    public Optional<TypeUser> deleteById(Long idTypeUser) {
        TypeUserEntity findTypeUser = typeUserRepository.findById(idTypeUser)
                .orElseThrow(() -> new TypeUserNotFoundException(idTypeUser));

        Optional<TypeUserEntity> typeUser = typeUserRepository.findById(idTypeUser);
        typeUserRepository.deleteById(idTypeUser);
        return typeUser.map(mapper::toTypeUserDomain);
    }

    @Override
    public Optional<TypeUser> getById(Long idTypeUser) {
        var findTypeUser = typeUserRepository.findById(idTypeUser)
                .orElseThrow(() -> new TypeUserNotFoundException(idTypeUser));

        return Optional.ofNullable(mapper.toTypeUserDomain(findTypeUser));
    }

    @Override
    public Optional<TypeUser> getByNameType(String nameTypeUser) {
        Optional<TypeUserEntity> findTypeUser = typeUserRepository.getByNameType(nameTypeUser);

        return findTypeUser.map(mapper::toTypeUserDomain);
    }

    @Override
    public List<TypeUser> getAll() {
        return typeUserRepository.findAll().stream().map(mapper::toTypeUserDomain).toList();
    }

}