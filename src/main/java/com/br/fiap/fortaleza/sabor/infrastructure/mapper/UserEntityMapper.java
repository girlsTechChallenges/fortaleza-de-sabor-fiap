package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.token.Token;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserEntityMapper {

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
                TypeEnum.valueOf(userRequestDto.type().name()),
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
                TypeEnum.valueOf(updateRequestDto.type().name()),
                addresses);
    }

    public UserEntity toUserEntity(User user) {

        List<AddressEntity> addressEntities = user.getAddress()
                .stream().map(address -> new AddressEntity(
                        address.getStreet(),
                        address.getDistrict(),
                        address.getComplement(),
                        address.getNumber(),
                        address.getState(),
                        address.getCity(), address.getPostCode())).toList();

        return new UserEntity(
                null, // Assuming ID is auto-generated
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getChangeDate(),
                TypeEntityEnum.valueOf(user.getType().name()),
                addressEntities
                );
    }

    public User toUserDomain(UserEntity userEntity) {

        List<Address> addresses = userEntity.getAddresses().stream()
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
                userEntity.getPassword(),
                userEntity.getChangeDate(),
                TypeEnum.valueOf(userEntity.getType().name()),
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
        if (optionalUser.isEmpty()) {
            //Here you can throw an exception, return null, or an empty DTO, according to your business rule.
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        User user = optionalUser.get();

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