package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.token.Token;
import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.br.fiap.fortaleza.sabor.infrastructure.common.MessageConstants.USER_NOT_FOUND;

@Component
public class UserMapper {

    private final TypeUserMapper typeUserMapper;

    public UserMapper(TypeUserMapper typeUserMapper) {
        this.typeUserMapper = typeUserMapper;
    }

    public User toUserDomain(UserRequestDto userRequestDto) {

        List<Address> addresses = userRequestDto.address().stream()
                .map(addressEntity -> new Address(
                        addressEntity.street(),
                        addressEntity.district(),
                        addressEntity.complement(),
                        addressEntity.number(),
                        addressEntity.state(),
                        addressEntity.city(),
                        addressEntity.postCode()
                )).toList();

        return new User(
                userRequestDto.name(),
                userRequestDto.email(),
                userRequestDto.login(),
                userRequestDto.password(),
                userRequestDto.changeDate(),
                userRequestDto.type(),
                addresses);
    }

    public User updateToUserDomain(UpdateRequestDto updateRequestDto) {

        List<Address> addresses = updateRequestDto.address().stream()
                .map(addressEntity -> new Address(
                        addressEntity.street(),
                        addressEntity.district(),
                        addressEntity.complement(),
                        addressEntity.number(),
                        addressEntity.state(),
                        addressEntity.city(),
                        addressEntity.postCode()
                )).toList();

        return new User(
                updateRequestDto.name(),
                updateRequestDto.email(),
                updateRequestDto.password(),
                updateRequestDto.type(),
                addresses);
    }

    public UserEntity toUserEntity(User user) {
        List<AddressEntity> addressEntities = user.getAddress()
                .stream()
                .map(address -> new AddressEntity(
                        address.getStreet(),
                        address.getDistrict(),
                        address.getComplement(),
                        address.getNumber(),
                        address.getState(),
                        address.getCity(),
                        address.getPostCode()))
                .toList();

        return new UserEntity.Builder()
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .changeDate(user.getChangeDate())
                .type(typeUserMapper.toTypeEntity(new TypeUser(null, user.getType())))
                .enderecos(addressEntities)
                .build();
    }

    public User toUserDomain(UserEntity userEntity) {

        List<Address> addresses = userEntity.getEnderecos().stream()
                .map(addressEntity -> new Address(
                        addressEntity.getStreet(),
                        addressEntity.getDistrict(),
                        addressEntity.getComplement(),
                        addressEntity.getNumber(),
                        addressEntity.getState(),
                        addressEntity.getCity(),
                        addressEntity.getPostCode()
                )).toList();

        return new User(
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getLogin(),
                userEntity.getSenha(),
                userEntity.getDataAlteracao(),
                userEntity.getTipo().getNameType(),
                addresses
                );
    }

    public UserResponseDto toUserResponseDto(User user) {

        List<AddressDto> addressDtos = user.getAddress().stream()
                .map(address -> new AddressDto(
                        address.getStreet(),
                        address.getDistrict(),
                        address.getComplement(),
                        address.getNumber(),
                        address.getCity(),
                        address.getState(),
                        address.getPostCode()))  .toList();

        return new UserResponseDto(user.getName(), user.getLogin(), user.getEmail(), user.getType(), addressDtos);
    }

    public UserResponseDto getUserByIdToUserResponseDto(Optional<User> optionalUser) {

        User user = optionalUser.orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        List<AddressDto> addressDtos = user.getAddress().stream()
                .map(address -> new AddressDto(
                        address.getStreet(),
                        address.getDistrict(),
                        address.getComplement(),
                        address.getNumber(),
                        address.getCity(),
                        address.getState(),
                        address.getPostCode()))
                .toList();

        return new UserResponseDto(user.getName(), user.getLogin(), user.getEmail(), user.getType(), addressDtos);
    }

    public List<AddressEntity> toAddressEntityList(List<Address> addresses) {
        return addresses.stream()
                .map(address -> new AddressEntity(
                        address.getStreet(),
                        address.getDistrict(),
                        address.getComplement(),
                        address.getNumber(),
                        address.getState(),
                        address.getCity(),
                        address.getPostCode()))
                .toList();
    }

    public UserAuthDto toTokenResponseDto(Token token) {
        return new UserAuthDto(token.getAccessToken(), token.getExpiresIn());
    }

}