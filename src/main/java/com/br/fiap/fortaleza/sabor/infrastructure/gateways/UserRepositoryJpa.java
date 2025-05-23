package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserRepositoryJpa implements UsersRepository {

    private final UserRepository userRepository;
    private final UserEntityMapper mapper;

    public UserRepositoryJpa(UserRepository userRepository, UserEntityMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream().map(mapper::toUserDomain).toList();
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = mapper.toUserEntity(user);
        var resp = userRepository.save(userEntity);
        return mapper.toUserDomain(resp);
    }

    @Override
    public Optional<User> update(Long idUsuario, User user) {
        UserEntity findUser = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + idUsuario));

        findUser.setNome(user.getNome());
        findUser.setEmail(user.getEmail());
        findUser.setSenha(user.getSenha());
        findUser.setDataAlteracao(LocalDate.now());
        findUser.setTipo(TypeEntityEnum.valueOf(user.getTipo().name()));
        findUser.setEnderecos(mapper.toAddressEntityList(user.getAddress()));

        var atualizado = userRepository.save(findUser);
        return Optional.ofNullable(mapper.toUserDomain(atualizado));
    }

    @Override
    public Optional<User> getById(Long idUsuario) {
        var findUser = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + idUsuario));

        return Optional.ofNullable(mapper.toUserDomain(findUser));
    }

    @Override
    public Optional<User> deleteById(Long idUsuario) {
        Optional<UserEntity> user = userRepository.findById(idUsuario);
        userRepository.deleteById(idUsuario);
        return user.map(mapper::toUserDomain);
    }

}