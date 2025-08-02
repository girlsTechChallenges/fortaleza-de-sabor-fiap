package com.br.fiap.fortaleza.sabor.infrastructure.adapter;

import com.br.fiap.fortaleza.sabor.application.ports.out.TypeRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserTypeMismatchException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeUserRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeRepositoryPortJpa implements TypeRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(TypeRepositoryPortJpa.class);
    private final TypeUserRepositoryAdapter typeUserRepositoryAdapter;
    private final UserRepositoryAdapter userRepositoryAdapter;
    private final TypeUserMapper typeMapper;

    public TypeRepositoryPortJpa(TypeUserRepositoryAdapter typeUserRepositoryAdapter, UserRepositoryAdapter userRepositoryAdapter, TypeUserMapper typeMapper) {
        this.typeUserRepositoryAdapter = typeUserRepositoryAdapter;
        this.userRepositoryAdapter = userRepositoryAdapter;
        this.typeMapper = typeMapper;
    }

    @Override
    public TypeUser create(TypeUser typeUser) {
        log.info("Creating TypeUser with type: {}", typeUser.getType());

        String normalizedType = normalizeTypeName(typeUser.getType());
        TypeEntity typeEntity = findOrCreateType(normalizedType);

        TypeUser createdTypeUser = typeMapper.toTypeUserEntity(typeEntity);
        log.info("TypeUser created with ID: {}", createdTypeUser.getId());

        return createdTypeUser;
    }

    @Override
    public Optional<TypeUser> update(Long id, TypeUser typeUser) {
        log.info("Received request to update TypeUser with id: {}", id);

        TypeEntity existingType = typeUserRepositoryAdapter.findById(id)
                .orElseThrow(() -> {
                    log.warn("TypeUser with id {} not found for update", id);
                    return new UserNotFoundException("TypeUser not found with id: " + id);
                });

        existingType.setNameType(typeUser.getType());
        TypeEntity updatedEntity = typeUserRepositoryAdapter.save(existingType);
        TypeUser updatedTypeUser = typeMapper.toTypeUserEntity(updatedEntity);

        log.info("TypeUser updated successfully: {}", updatedTypeUser);
        return Optional.ofNullable(updatedTypeUser);
    }

    @Override
    public Optional<TypeUser> deleteById(Long id) {
        log.info("Received request to delete TypeUser with id: {}", id);
        boolean isTypeLinked = userRepositoryAdapter.existsByTipoId(id);

        if (isTypeLinked) {
            log.error("Cannot delete TypeUser with id {}. It is linked to existing users.", id);
            throw new UserTypeMismatchException("It is not possible to delete the type. There are users linked to it.");
        }

        return typeUserRepositoryAdapter.findById(id)
                .map(typeEntity -> {
                    typeUserRepositoryAdapter.delete(typeEntity);
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

        return typeUserRepositoryAdapter.findById(id)
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

        List<TypeEntity> typeEntities = typeUserRepositoryAdapter.findAll();

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

    private String normalizeTypeName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User type cannot be empty.");
        }
        return name.trim().toUpperCase();
    }

    private TypeEntity findOrCreateType(String normalizedType) {
        return typeUserRepositoryAdapter.findByNameType(normalizedType)
                .orElseGet(() -> safelySaveType(normalizedType));
    }

    private TypeEntity safelySaveType(String normalizedType) {
        try {
            return typeUserRepositoryAdapter.save(new TypeEntity(null, normalizedType));
        } catch (DataIntegrityViolationException e) {
            return typeUserRepositoryAdapter.findByNameType(normalizedType)
                    .orElseThrow(() -> new IllegalArgumentException("User type already exists."));
        }
    }
}
