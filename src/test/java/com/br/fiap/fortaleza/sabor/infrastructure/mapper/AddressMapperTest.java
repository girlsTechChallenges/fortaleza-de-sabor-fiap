package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.AddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = Mappers.getMapper(AddressMapper.class);
    }

    @Test
    @DisplayName("Should convert AddressDto to Address entity")
    void shouldConvertAddressDtoToAddress() {
        // Given
        AddressDto addressDto = new AddressDto(
                "Rua das Palmeiras", "Vila Madalena", "Casa 10", 250,
                "SP", "São Paulo", "05432-010"
        );

        // When
        Address address = addressMapper.toEntity(addressDto);

        // Then
        assertNotNull(address);
        assertEquals("Rua das Palmeiras", address.getRua());
        assertEquals("Vila Madalena", address.getBairro());
        assertEquals("Casa 10", address.getComplemento());
        assertEquals(250, address.getNumero());
        assertEquals("SP", address.getEstado());
        assertEquals("São Paulo", address.getCidade());
        assertEquals("05432-010", address.getCep());
    }

    @Test
    @DisplayName("Should convert Address entity to AddressDto")
    void shouldConvertAddressToAddressDto() {
        // Given
        Address address = new Address(
                "Av. Faria Lima", "Itaim Bibi", "Sala 1201", 1000,
                "SP", "São Paulo", "04538-132"
        );

        // When
        AddressDto addressDto = addressMapper.toDto(address);

        // Then
        assertNotNull(addressDto);
        assertEquals("Av. Faria Lima", addressDto.rua());
        assertEquals("Itaim Bibi", addressDto.bairro());
        assertEquals("Sala 1201", addressDto.complemento());
        assertEquals(1000, addressDto.numero());
        assertEquals("SP", addressDto.estado());
        assertEquals("São Paulo", addressDto.cidade());
        assertEquals("04538-132", addressDto.cep());
    }

    @Test
    @DisplayName("Should handle null values gracefully")
    void shouldHandleNullValues() {
        // When/Then - Should not throw exceptions for null inputs
        assertDoesNotThrow(() -> {
            Address address = addressMapper.toEntity(null);
            AddressDto addressDto = addressMapper.toDto(null);
            
            assertNull(address);
            assertNull(addressDto);
        });
    }

    @Test
    @DisplayName("Should handle AddressDto with null fields")
    void shouldHandleAddressDtoWithNullFields() {
        // Given
        AddressDto addressDto = new AddressDto(
                null, null, null, 0, null, null, null
        );

        // When
        Address address = addressMapper.toEntity(addressDto);

        // Then
        assertNotNull(address);
        assertNull(address.getRua());
        assertNull(address.getBairro());
        assertNull(address.getComplemento());
        assertEquals(0, address.getNumero());
        assertNull(address.getEstado());
        assertNull(address.getCidade());
        assertNull(address.getCep());
    }

    @Test
    @DisplayName("Should handle Address with null fields")
    void shouldHandleAddressWithNullFields() {
        // Given
        Address address = new Address(
                null, null, null, 0, null, null, null
        );

        // When
        AddressDto addressDto = addressMapper.toDto(address);

        // Then
        assertNotNull(addressDto);
        assertNull(addressDto.rua());
        assertNull(addressDto.bairro());
        assertNull(addressDto.complemento());
        assertEquals(0, addressDto.numero());
        assertNull(addressDto.estado());
        assertNull(addressDto.cidade());
        assertNull(addressDto.cep());
    }
}
