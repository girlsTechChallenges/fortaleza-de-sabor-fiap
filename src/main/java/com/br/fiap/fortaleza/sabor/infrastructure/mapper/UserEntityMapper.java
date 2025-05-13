package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEntityMapper {

    public UserEntity toUserEntity(User user) {

        List<AddressEntity> addressEntities = user.getAddress()
                .stream().map(address -> new AddressEntity(
                        null, address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getEstado(),
                        address.getCidade(), address.getCep(), null)).toList();

        return new UserEntity(
                user.getNome(),
                user.getEmail(),
                user.getLogin(),
                user.getDataAlteracao(),
                TypeEntityEnum.valueOf(user.getTipo().name()),
                addressEntities,
                user.getSenha());
    }

    public User toUserDomain(UserEntity userEntity) {

        List<Address> addresses = userEntity.getEnderecos().stream()
                .map(addressEntity -> new Address(
                        addressEntity.getRua(),
                        addressEntity.getBairro(),
                        addressEntity.getComplemento(),
                        addressEntity.getNumero(),
                        addressEntity.getEstado(),
                        addressEntity.getCidade(),
                        addressEntity.getCep()
                )).toList();

        return new User(
                userEntity.getNome(),
                userEntity.getEmail(),
                userEntity.getLogin(),
                userEntity.getSenha(),
                userEntity.getDataAlteracao(), TypeEnum.valueOf(userEntity.getTipo().name()),
                addresses);
    }

    public User toUserDomain(UserRequestDto userRequestDto) {

        List<Address> addresses = userRequestDto.address().stream()
                .map(addressEntity -> new Address(
                        addressEntity.rua(),
                        addressEntity.bairro(),
                        addressEntity.complemento(),
                        addressEntity.numero(),
                        addressEntity.estado(),
                        addressEntity.cidade(),
                        addressEntity.cep()
                )).toList();

        return new User(
                userRequestDto.nome(),
                userRequestDto.email(),
                userRequestDto.login(),
                userRequestDto.senha(),
                userRequestDto.dataAlteracao(), TypeEnum.valueOf(userRequestDto.tipo().name()),
                addresses);
    }
}
