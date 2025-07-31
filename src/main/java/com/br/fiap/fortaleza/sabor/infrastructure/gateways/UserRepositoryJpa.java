package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.TypeUserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserRepositoryJpa implements UsersRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryJpa.class);
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TypeUserRepository typeUserRepository;
    private final UserMapper mapper;

    public UserRepositoryJpa(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, TypeUserRepository typeUserRepository, UserMapper mapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.typeUserRepository = typeUserRepository;
        this.mapper = mapper;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream().map(mapper::toUserDomain).toList();
    }

    @Override
    public User save(User user) {
        String typeFormat = user.getTipo().trim().toUpperCase();
        TypeEntity typeEntity = findOrCreateType(typeFormat);

        validateEmailUniqueness(user.getEmail());

        UserEntity userEntity = mapper.toUserEntity(user);
        userEntity.setTipo(typeEntity);

        return mapper.toUserDomain(userRepository.save(userEntity));
    }

    @Override
    public Optional<User> update(Long idUser, User user) {
        UserEntity findUser = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        if (user != null) {
            findUser.setNome(user.getNome());
            findUser.setEmail(user.getEmail());
            findUser.setSenha(user.getSenha());
            findUser.setTipo(new TypeEntity(null, user.getTipo()));

            if (user.getAddress() != null && !user.getAddress().isEmpty()) {
                findUser.setEnderecos(new ArrayList<>(mapper.toAddressEntityList(user.getAddress())));
            }
        }

        UserEntity actualization = userRepository.save(findUser);
        return Optional.ofNullable(mapper.toUserDomain(actualization));
    }

    @Override
    public Optional<User> getById(Long idUser) {
        var findUser = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        return Optional.ofNullable(mapper.toUserDomain(findUser));
    }

    @Override
    public Optional<User> deleteById(Long idUser) {
        UserEntity findUser = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        Optional<UserEntity> user = userRepository.findById(idUser);
        userRepository.deleteById(idUser);
        return user.map(mapper::toUserDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(mapper::toUserDomain);
    }

    @Override
    public void updatePassword(String email, String password) {

        var passEncoded = passwordEncoder.encode(password);
        var user = userRepository.findByEmail(email);

        try {
            if(Objects.isNull(user)){
                throw new UserNotFoundException(email);
            }
            user.get().setSenha(passEncoded);
            userRepository.save(user.get());

        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(email);

        } catch (Exception e) {
            log.error("Error updating user password", e);
            throw new RuntimeException("Error updating user password", e);
        }
    }

    private TypeEntity findOrCreateType(String formattedType) {
        return typeUserRepository.findByNameType(formattedType)
                .orElseGet(() -> createNewTypeIfNotExists(formattedType));
    }

    private TypeEntity createNewTypeIfNotExists(String formattedType) {
        boolean typeAlreadyExists = typeUserRepository.findAll().stream()
                .anyMatch(type -> type.getNameType().trim().equalsIgnoreCase(formattedType));

        if (typeAlreadyExists) {
            throw new IllegalArgumentException("User type already exists with a similar name.");
        }

        return typeUserRepository.save(new TypeEntity(null, formattedType.trim().toUpperCase()));
    }

    private void validateEmailUniqueness(String email) {
        String formattedEmail = email.trim().toLowerCase();
        userRepository.findByEmail(formattedEmail)
                .ifPresent(existingUser -> {
                    throw new UserAlreadyRegisteredException(
                            "This user already exists. Check your credentials or recover your password."
                    );
                });
    }
}