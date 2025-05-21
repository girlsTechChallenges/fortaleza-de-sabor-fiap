package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "address", target = "enderecos")
    UserEntity toUserEntity(User user);

    @Mapping(source = "enderecos", target = "address")
    User toUserDomain(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    User toUserDomain(UserRequestDto userRequestDto);

    UserResponseDto toUserResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    AddressEntity toAddressEntity(Address address);

    Address toAddressDomain(AddressEntity addressEntity);

    Address toAddressDomain(AddressDto addressDto);

    default TypeEnum toTypeEnum(TypeEntityEnum typeEntityEnum) {
        if (typeEntityEnum == null) return null;
        return TypeEnum.valueOf(typeEntityEnum.name());
    }

    default TypeEntityEnum toTypeEntityEnum(TypeEnum typeEnum) {
        if (typeEnum == null) return null;
        return TypeEntityEnum.valueOf(typeEnum.name());
    }
}
