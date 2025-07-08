package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.TypeUserDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.TypeUserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TypeUserEntityMapper {

    public TypeUser toTypeUserDomain(TypeUserEntity typeUserEntity) {
        return new TypeUser(typeUserEntity.getTipo());
    }

    public TypeUser toTypeUserDomain(TypeUserDto typeUserDto) {
        return new TypeUser(typeUserDto.nome_tipo());
    }

    public TypeUserEntity toTypeUserEntity(TypeUser typeUser) {
        return new TypeUserEntity(typeUser.getNome_tipo());
    }

    public TypeUserDto toTypeUserDto(TypeUser typeUser) {
        return new TypeUserDto(typeUser.getNome_tipo());
    }

}
