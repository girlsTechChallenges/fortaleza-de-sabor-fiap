package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserTypeRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserTypeResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class UserTypeEntityMapper {

    public UserType toUserTypeDomain(UserTypeEntity userTypeEntity) {
        return new UserType(userTypeEntity.getId(),userTypeEntity.getType());
    }

    public UserType toUserTypeDomain(UserTypeRequestDto userTypeRequestDto) {
        return new UserType(userTypeRequestDto.nameType());
    }

    public UserTypeEntity toUserTypeEntity(UserType userType) {
        return new UserTypeEntity(userType.getIdType(), userType.getNameType());
    }

    public UserTypeRequestDto toUserTypeRequestDto(UserType userType) {
        return new UserTypeRequestDto(userType.getNameType());
    }

    public UserTypeResponseDto toUserTypeResponseDto(UserType userType) {
        return new UserTypeResponseDto(userType.getIdType(),userType.getNameType());
    }

    public UserTypeResponseDto toUserTypeResponseDto(UserTypeEntity userTypeEntity) {
        return new UserTypeResponseDto(userTypeEntity.getId(),userTypeEntity.getType());
    }

}
