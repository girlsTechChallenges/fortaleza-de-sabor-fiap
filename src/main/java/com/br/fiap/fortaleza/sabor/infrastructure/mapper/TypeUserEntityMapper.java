package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.typeUser.TypeUserEntity;
import org.springframework.stereotype.Component;

@Component
public class TypeUserEntityMapper {

    public TypeUser toTypeUserDomain(TypeUserEntity typeUserEntity) {
        return new TypeUser(typeUserEntity.getId(),typeUserEntity.getType());
    }

    public TypeUser toTypeUserDomain(TypeUserRequestDto typeUserRequestDto) {
        return new TypeUser(typeUserRequestDto.nameType());
    }

    public TypeUserEntity toTypeUserEntity(TypeUser typeUser) {
        return new TypeUserEntity(typeUser.getNameType());
    }

    public TypeUserRequestDto toTypeUserRequestDto(TypeUser typeUser) {
        return new TypeUserRequestDto(typeUser.getNameType());
    }

    public TypeUserResponseDto toTypeUserResponseDto(TypeUser typeUser) {
        return new TypeUserResponseDto(typeUser.getIdType(),typeUser.getNameType());
    }

    public TypeUserResponseDto toTypeUserResponseDto(TypeUserEntity typeUserEntity) {
        return new TypeUserResponseDto(typeUserEntity.getId(),typeUserEntity.getType());
    }

}
