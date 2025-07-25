package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.typeUser.TypeUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeUserEntityMapperTest {

    private TypeUserEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TypeUserEntityMapper();
    }

    @Test
    void shouldMapEntityToDomain() {
        TypeUserEntity entity = new TypeUserEntity(1L,"CLIENTE");
        entity.setId(10L);

        TypeUser domain = mapper.toTypeUserDomain(entity);

        assertNotNull(domain);
        assertEquals(10L, domain.getIdType());
        assertEquals("CLIENTE", domain.getNameType());
    }

    @Test
    void shouldMapRequestDtoToDomain() {
        TypeUserRequestDto requestDto = new TypeUserRequestDto("ADMIN");

        TypeUser domain = mapper.toTypeUserDomain(requestDto);

        assertNotNull(domain);
        assertEquals("ADMIN", domain.getNameType());
    }

    @Test
    void shouldMapDomainToEntity() {
        TypeUser domain = new TypeUser("GESTOR");

        TypeUserEntity entity = mapper.toTypeUserEntity(domain);

        assertNotNull(entity);
        assertEquals("GESTOR", entity.getType());
    }

    @Test
    void shouldMapDomainToRequestDto() {
        TypeUser domain = new TypeUser("CLIENTE");

        TypeUserRequestDto dto = mapper.toTypeUserRequestDto(domain);

        assertNotNull(dto);
        assertEquals("CLIENTE", dto.nameType());
    }

    @Test
    void shouldMapDomainToResponseDto() {
        TypeUser domain = new TypeUser(5L, "SUPERVISOR");

        TypeUserResponseDto response = mapper.toTypeUserResponseDto(domain);

        assertNotNull(response);
        assertEquals(5L, response.idType());
        assertEquals("SUPERVISOR", response.nameType());
    }

    @Test
    void shouldMapEntityToResponseDto() {
        TypeUserEntity entity = new TypeUserEntity(1L,"ANALISTA");
        entity.setId(7L);

        TypeUserResponseDto response = mapper.toTypeUserResponseDto(entity);

        assertNotNull(response);
        assertEquals(7L, response.idType());
        assertEquals("ANALISTA", response.nameType());
    }
}
