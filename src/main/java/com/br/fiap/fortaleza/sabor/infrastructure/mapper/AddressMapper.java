package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AddressMapper {
    
    Address toEntity(AddressDto dto);
    
    AddressDto toDto(Address address);
    
    @Mapping(target = "id", ignore = true)
    AddressEntity toAddressEntity(Address address);
    
    Address toAddress(AddressEntity entity);
    
    List<AddressEntity> toAddressEntityList(List<Address> addresses);
    
    List<Address> toAddressList(List<AddressEntity> entities);
}
