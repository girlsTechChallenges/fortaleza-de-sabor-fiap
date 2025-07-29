package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.token.Token;
import com.br.fiap.fortaleza.sabor.domain.user.TypeUser;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.*;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponse;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {

    private final TypeUserMapper typeUserMapper;

    public UserMapper(TypeUserMapper typeUserMapper) {
        this.typeUserMapper = typeUserMapper;
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
                userRequestDto.dataAlteracao(),
                typeUserMapper.toTypeUserDomain(new TypeUserRequestDto(userRequestDto.tipo().type())),
                addresses);
    }

    public User updateToUserDomain(UpdateRequestDto updateRequestDto) {

        List<Address> addresses = updateRequestDto.address().stream()
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
                updateRequestDto.nome(),
                updateRequestDto.email(),
                updateRequestDto.senha(),
                typeUserMapper.toTypeUserDomain(new TypeUserRequestDto(updateRequestDto.tipo().type())),
                addresses);
    }

    public UserEntity toUserEntity(User user) {

        List<AddressEntity> addressEntities = user.getAddress()
                .stream().map(address -> new AddressEntity(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getEstado(),
                        address.getCidade(), address.getCep())).toList();

        return new UserEntity(
                null,
                user.getNome(),
                user.getEmail(),
                user.getLogin(),
                user.getSenha(),
                user.getDataAlteracao(),
                typeUserMapper.toTypeEntity(new TypeUser(user.getTipo().getId(), user.getTipo().getType())),
                addressEntities
                );
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
                userEntity.getDataAlteracao(),
                typeUserMapper.toTypeUserDomain(new TypeUserRequestDto(userEntity.getTipo().getNameType())),
                addresses
                );
    }

    public UserResponseDto toUserResponseDto(User user) {

        List<AddressDto> addressDtos = user.getAddress().stream()
                .map(address -> new AddressDto(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getCidade(),
                        address.getEstado(),
                        address.getCep()))  .toList();

        return new UserResponseDto(user.getNome(), user.getLogin(), user.getEmail(),
                new TypeUserResponse(
                        null,
                        user.getTipo().getType()),
                addressDtos);
    }

    public UserResponseDto getUserByIdToUserResponseDto(Optional<User> optionalUser) {

        User user = optionalUser.get();

        List<AddressDto> addressDtos = user.getAddress().stream()
                .map(address -> new AddressDto(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getCidade(),
                        address.getEstado(),
                        address.getCep()))
                .toList();

        return new UserResponseDto(user.getNome(), user.getLogin(), user.getEmail(),
                new TypeUserResponse(
                        user.getTipo().getId(),
                        user.getTipo().getType()),
                addressDtos);
    }

    public List<AddressEntity> toAddressEntityList(List<Address> addresses) {
        return addresses.stream()
                .map(address -> new AddressEntity(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getEstado(),
                        address.getCidade(),
                        address.getCep()))
                .toList();
    }

    public UserAuthDto toTokenResponseDto(Token token) {
        return new UserAuthDto(token.getAccessToken(), token.getExpiresIn());
    }

}