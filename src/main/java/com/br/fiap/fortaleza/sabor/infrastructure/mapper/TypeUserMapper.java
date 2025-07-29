package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.user.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponse;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import org.springframework.stereotype.Component;

@Component
public class TypeUserMapper {

    public TypeUser toTypeUserDomain(TypeUserRequestDto typeUserRequestDto) {
        return new TypeUser(null, typeUserRequestDto.type());
    }

    public TypeEntity toTypeEntity(TypeUser user) {
        return new TypeEntity(null, user.getType());
    }

    public TypeUser toTypeUserEntity(TypeEntity typeEntity) {
        return new TypeUser(typeEntity.getId(), typeEntity.getNameType());
    }

    public TypeUserResponse typeUserResponse(TypeUser typeUser) {
        return new TypeUserResponse(typeUser.getId(), typeUser.getType());
    }

}
