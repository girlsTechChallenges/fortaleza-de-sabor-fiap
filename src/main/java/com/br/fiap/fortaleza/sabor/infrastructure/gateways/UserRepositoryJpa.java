package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserRepositoryJpa implements UsersRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryJpa.class);
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;    private final UserMapper userMapper;

    public UserRepositoryJpa(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserDomain).toList();
    }

    @Override
    public User save(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyRegisteredException(
                            "This user already exists. Check your credentials or recover your password."
                    );
                });        UserEntity userEntity = userMapper.toUserEntity(user);
        return userMapper.toUserDomain(userRepository.save(userEntity));
    }

    @Override
    public Optional<User> update(Long idUser, User user) {
        UserEntity findUser = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        if (user != null) {
            findUser.setNome(user.getNome());
            findUser.setEmail(user.getEmail());
            findUser.setSenha(user.getSenha());
            findUser.setTipo(TypeEntityEnum.valueOf(user.getTipo().name()));

            if (user.getAddress() != null && !user.getAddress().isEmpty()) {                List<AddressEntity> addressEntities = userMapper.toAddressEntityList(user.getAddress());
                findUser.setEnderecos(addressEntities);
            }
        }

        UserEntity actualization = userRepository.save(findUser);
        return Optional.ofNullable(userMapper.toUserDomain(actualization));
    }

    @Override
    public Optional<User> getById(Long idUser) {
        var findUser = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));

        return Optional.ofNullable(userMapper.toUserDomain(findUser));
    }

    @Override
    public Optional<User> deleteById(Long idUser) {
        Optional<UserEntity> user = userRepository.findById(idUser);
        if (user.isEmpty()) {
            throw new UserNotFoundException(idUser);
        }
        userRepository.deleteById(idUser);
        return user.map(userMapper::toUserDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(userMapper::toUserDomain);
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