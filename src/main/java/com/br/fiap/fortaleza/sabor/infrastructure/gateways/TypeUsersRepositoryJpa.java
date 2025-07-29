package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.TypeUserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeUsersRepositoryJpa implements TypeUsersRepository {

    private static final Logger log = LoggerFactory.getLogger(TypeUsersRepositoryJpa.class);
    private final TypeUserRepository typeUserRepository;
    private final TypeUserMapper typeMapper;

    public TypeUsersRepositoryJpa(TypeUserRepository typeUserRepository, TypeUserMapper typeMapper) {
        this.typeUserRepository = typeUserRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public TypeUser create(TypeUser typeUser) {
        log.info("Creating TypeUser with type: {}", typeUser.getType());

        TypeEntity savedEntity = typeUserRepository.save(typeMapper.toTypeEntity(typeUser));
        TypeUser createdTypeUser = typeMapper.toTypeUserEntity(savedEntity);

        log.info("TypeUser created with ID: {}", createdTypeUser.getId());
        return createdTypeUser;
    }

    @Override
    public Optional<TypeUser> update(Long id, TypeUser typeUser) {
        log.info("Received request to update TypeUser with id: {}", id);

        TypeEntity existingType = typeUserRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("TypeUser with id {} not found for update", id);
                    return new UserNotFoundException("TypeUser not found with id: " + id);
                });

        existingType.setNameType(typeUser.getType());
        TypeEntity updatedEntity = typeUserRepository.save(existingType);
        TypeUser updatedTypeUser = typeMapper.toTypeUserEntity(updatedEntity);

        log.info("TypeUser updated successfully: {}", updatedTypeUser);
        return Optional.ofNullable(updatedTypeUser);
    }

    @Override
    public Optional<TypeUser> deleteById(Long id) {
        log.info("Received request to delete TypeUser with id: {}", id);

        return typeUserRepository.findById(id)
                .map(typeEntity -> {
                    typeUserRepository.delete(typeEntity);
                    log.info("TypeUser with id {} deleted successfully", id);
                    return Optional.of(typeMapper.toTypeUserEntity(typeEntity));
                })
                .orElseGet(() -> {
                    log.warn("TypeUser with id {} not found for deletion", id);
                    return Optional.empty();
                });
    }

    @Override
    public Optional<TypeUser> getById(Long id) {
        log.info("Received request to get TypeUser with id: {}", id);

        return typeUserRepository.findById(id)
                .map(typeMapper::toTypeUserEntity)
                .map(typeUser -> {
                    log.info("TypeUser found: {}", typeUser);
                    return typeUser;
                })
                .or(() -> {
                    log.warn("TypeUser with id {} not found", id);
                    return Optional.empty();
                });
    }

    @Override
    public List<TypeUser> getAll() {
        log.info("Received request to get all TypeUsers");

        List<TypeEntity> typeEntities = typeUserRepository.findAll();

        if (!typeEntities.isEmpty()) {
            log.info("Found {} TypeUsers", typeEntities.size());
            return typeEntities.stream()
                    .map(typeMapper::toTypeUserEntity)
                    .toList();
        } else {
            log.info("No TypeUsers found in database.");
            return List.of();
        }
    }
}
