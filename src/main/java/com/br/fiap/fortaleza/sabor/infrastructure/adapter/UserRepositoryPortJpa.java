package com.br.fiap.fortaleza.sabor.infrastructure.adapter;

import com.br.fiap.fortaleza.sabor.application.ports.out.UsersRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeUserRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserRepositoryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserRepositoryPortJpa implements UsersRepositoryPort {

    Logger log = LoggerFactory.getLogger(UserRepositoryPortJpa.class);

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepositoryAdapter userRepositoryAdapter;
    private final TypeUserRepositoryAdapter typeUserRepositoryAdapter;
    private final UserMapper mapper;

    public UserRepositoryPortJpa(BCryptPasswordEncoder passwordEncoder, UserRepositoryAdapter userRepositoryAdapter, TypeUserRepositoryAdapter typeUserRepositoryAdapter, UserMapper mapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryAdapter = userRepositoryAdapter;
        this.typeUserRepositoryAdapter = typeUserRepositoryAdapter;
        this.mapper = mapper;
    }

    @Override
    public List<User> getAll() {
        return userRepositoryAdapter.findAll().stream().map(mapper::toUserDomain).toList();
    }

    @Override
    public User save(User user) {
        String typeFormat = user.getTipo().trim().toUpperCase();
        TypeEntity typeEntity = findOrCreateType(typeFormat);

        validateEmailUniqueness(user.getEmail());

        UserEntity userEntity = mapper.toUserEntity(user);
        userEntity.setTipo(typeEntity);

        return mapper.toUserDomain(userRepositoryAdapter.save(userEntity));
    }

    @Override
    public Optional<User> update(Long idUser, User user) {
        UserEntity findUser = userRepositoryAdapter.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        if (user != null) {
            findUser.setNome(user.getNome());
            findUser.setEmail(user.getEmail());
            findUser.setSenha(user.getSenha());

            TypeEntity tipo = findOrCreateType(user.getTipo());
            findUser.setTipo(tipo);

            if (user.getAddress() != null && !user.getAddress().isEmpty()) {
                findUser.setEnderecos(new ArrayList<>(mapper.toAddressEntityList(user.getAddress())));
            }
        }

        UserEntity actualization = userRepositoryAdapter.save(findUser);
        return Optional.ofNullable(mapper.toUserDomain(actualization));
    }

    @Override
    public Optional<User> getById(Long idUser) {
        var findUser = userRepositoryAdapter.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        return Optional.ofNullable(mapper.toUserDomain(findUser));
    }

    @Override
    public Optional<User> deleteById(Long idUser) {
        userRepositoryAdapter.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        try {
            log.info("Deleting user with ID: {}", idUser);
            Optional<UserEntity> user = userRepositoryAdapter.findById(idUser);
            userRepositoryAdapter.deleteById(idUser);
            return user.map(mapper::toUserDomain);

        } catch (Exception e) {
            log.error("Error deleting user with ID: {}", idUser, e);
            throw new  UserAlreadyRegisteredException("Cannot delete user who is an owner of a restaurant.");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> user = userRepositoryAdapter.findByEmail(email);
        return user.map(mapper::toUserDomain);
    }

    @Override
    public void updatePassword(String email, String password) {

        var passEncoded = passwordEncoder.encode(password);
        var user = userRepositoryAdapter.findByEmail(email);

        try {
            if(Objects.isNull(user) || user.isEmpty()) {
                throw new UserNotFoundException(email);
            }
            user.get().setSenha(passEncoded);
            userRepositoryAdapter.save(user.get());

        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(email);
        }
    }

    private TypeEntity findOrCreateType(String formattedType) {
        return typeUserRepositoryAdapter.findByNameType(formattedType)
                .orElseGet(() -> createNewTypeIfNotExists(formattedType));
    }

    private TypeEntity createNewTypeIfNotExists(String formattedType) {
        boolean typeAlreadyExists = typeUserRepositoryAdapter.findAll().stream()
                .anyMatch(type -> type.getNameType().trim().equalsIgnoreCase(formattedType));

        if (typeAlreadyExists) {
            throw new IllegalArgumentException("User type already exists with a similar name.");
        }

        return typeUserRepositoryAdapter.save(new TypeEntity(null, formattedType.trim().toUpperCase()));
    }

    private void validateEmailUniqueness(String email) {
        String formattedEmail = email.trim().toLowerCase();
        userRepositoryAdapter.findByEmail(formattedEmail)
                .ifPresent(existingUser -> {
                    throw new UserAlreadyRegisteredException(
                            "This user already exists. Check your credentials or recover your password."
                    );
                });
    }
}