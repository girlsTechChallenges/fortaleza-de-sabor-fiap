package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public User update(Long idUsuario, User userAtualizado) {
        var findUser = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + idUsuario));

        findUser.setNome(userAtualizado.getNome());
        findUser.setSenha(userAtualizado.getSenha());
        findUser.setDataAlteracao(LocalDate.now());
        findUser.setEnderecos(mapper.toAddressEntityList(userAtualizado.getAddress()));

        var atualizado = userRepository.save(findUser);
        return mapper.toUserDomain(atualizado);
    }

    @Override
    public User getById(Long idUsuario) {
        var findUser = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + idUsuario));

        return mapper.toUserDomain(findUser);
    }

}