package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {AddressMapper.class},
    imports = {TypeEnum.class, TypeEntityEnum.class}
)
public interface UserEntityMapper {
    
    @Mapping(target = "tipo", expression = "java(TypeEnum.valueOf(entity.getTipo().name()))")
    @Mapping(target = "address", source = "enderecos")
    User toUserDomain(UserEntity entity);    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", expression = "java(TypeEntityEnum.valueOf(domain.getTipo().name()))")
    @Mapping(target = "enderecos", source = "address")
    UserEntity toUserEntity(User domain);

    List<User> toUserDomainList(List<UserEntity> entities);
    
    List<UserEntity> toUserEntityList(List<User> domains);
}
