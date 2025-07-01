package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.token.Token;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.*;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = {TypeEnum.class, TypeEntityEnum.class}
)
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAlteracao", expression = "java(java.time.LocalDate.now())")
    User toUser(UserRequestDto dto);
    
    UserResponseDto toResponseDto(User user);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAlteracao", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "login", ignore = true)
    void updateUserFromDto(UpdateRequestDto dto, @MappingTarget User user);

    Address toAddress(AddressDto addressDto);
    
    AddressDto toAddressDto(Address address);

    default UserAuthDto toTokenResponseDto(Token token) {
        if (token == null) {
            return null;
        }
        return new UserAuthDto(token.getAccessToken(), token.getExpiresIn());
    }
    
    @Mapping(target = "tipo", expression = "java(TypeEnum.valueOf(entity.getTipo().name()))")
    @Mapping(target = "address", source = "enderecos")
    User toUserDomain(UserEntity entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", expression = "java(TypeEntityEnum.valueOf(domain.getTipo().name()))")
    @Mapping(target = "enderecos", source = "address")
    UserEntity toUserEntity(User domain);

    @Mapping(target = "id", ignore = true)
    AddressEntity toAddressEntity(Address address);

    Address toAddress(AddressEntity entity);

    List<AddressEntity> toAddressEntityList(List<Address> addresses);

    List<Address> toAddressList(List<AddressEntity> addressEntities);
}
