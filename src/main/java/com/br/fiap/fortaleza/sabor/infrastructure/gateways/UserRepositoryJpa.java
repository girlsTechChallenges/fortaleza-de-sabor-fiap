package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.*;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.*;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserTypeRepository;
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
    private final UserEntityMapper mapper;
    private final UserTypeEntityMapper typeMapper;
    private final UserTypeRepository userTypeRepository;

    public UserRepositoryJpa(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, UserEntityMapper mapper, UserTypeEntityMapper typeMapper, UserTypeRepository userTypeRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.typeMapper = typeMapper;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream().map(mapper::toUserDomain).toList();
    }

    @Override
    public User save(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyRegisteredException(
                            "This user already exists. Check your credentials or recover your password."
                    );
                });
        userTypeRepository.getByNameType(user.getTipo().getNameType())
                .ifPresentOrElse(
                        existingUserType -> user.setTipo(typeMapper.toUserTypeDomain(existingUserType)),
                        () -> {
                            var savedType = userTypeRepository.save(typeMapper.toUserTypeEntity(user.getTipo()));
                            user.setTipo(typeMapper.toUserTypeDomain(savedType));
                        }
                );

        UserEntity userEntity = mapper.toUserEntity(user);
        var result = userRepository.save(userEntity);
        return mapper.toUserDomain(result);
    }

    @Override
    public Optional<User> update(Long idUser, User userRequest) {
        UserEntity findUserByID = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        if (userRequest != null) {
            userTypeRepository.getByNameType(userRequest.getTipo().getNameType())
                    .ifPresentOrElse(
                            existingUserType -> userRequest.setTipo(typeMapper.toUserTypeDomain(existingUserType)),
                            () -> {
                                var savedType = userTypeRepository.save(typeMapper.toUserTypeEntity(userRequest.getTipo()));
                                userRequest.setTipo(typeMapper.toUserTypeDomain(savedType));
                            }
                    );
            Optional<UserEntity> findUserByEmail = userRepository.findByEmail(userRequest.getEmail());
            if (findUserByEmail.isPresent() && !findUserByEmail.get().getId().equals(idUser))
                throw new UserAlreadyRegisteredException("This user already exists. Check your credentials or recover your password.");
            if ("DONO".equals(findUserByID.getTipo().getType())
               && findUserByID.getRestaurante() != null
               && !"DONO".equals(userRequest.getTipo().getNameType())
            ) {
                throw new UserAlreadyRegisteredException("This user is owner of a restaurant.");
            }

            findUserByID.setNome(userRequest.getNome());
            findUserByID.setSenha(userRequest.getSenha());
            findUserByID.setEmail(userRequest.getEmail());
            findUserByID.setTipo(typeMapper.toUserTypeEntity(userRequest.getTipo()));

            if (userRequest.getAddress() != null && !userRequest.getAddress().isEmpty()) {
                findUserByID.setEnderecos(new ArrayList<>(mapper.toAddressEntityList(userRequest.getAddress())));
            }
        }

        UserEntity actualization = userRepository.save(findUserByID);
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
            log.error("Erro ao atualizar a senha do usuário", e);
            throw new RuntimeException("Erro ao atualizar a senha do usuário", e);
        }
    }
}