package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserTypeRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserTypeResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserTypeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeEntityMapperTest {

    private UserTypeEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserTypeEntityMapper();
    }

    @Test
    void shouldMapEntityToDomain() {
        UserTypeEntity entity = new UserTypeEntity(1L,"CLIENTE");
        entity.setId(10L);

        UserType domain = mapper.toUserTypeDomain(entity);

        assertNotNull(domain);
        assertEquals(10L, domain.getIdType());
        assertEquals("CLIENTE", domain.getNameType());
    }

    @Test
    void shouldMapRequestDtoToDomain() {
        UserTypeRequestDto requestDto = new UserTypeRequestDto("ADMIN");

        UserType domain = mapper.toUserTypeDomain(requestDto);

        assertNotNull(domain);
        assertEquals("ADMIN", domain.getNameType());
    }

    @Test
    void shouldMapDomainToEntity() {
        UserType domain = new UserType("GESTOR");

        UserTypeEntity entity = mapper.toUserTypeEntity(domain);

        assertNotNull(entity);
        assertEquals("GESTOR", entity.getType());
    }

    @Test
    void shouldMapDomainToRequestDto() {
        UserType domain = new UserType("CLIENTE");

        UserTypeRequestDto dto = mapper.toUserTypeRequestDto(domain);

        assertNotNull(dto);
        assertEquals("CLIENTE", dto.nameType());
    }

    @Test
    void shouldMapDomainToResponseDto() {
        UserType domain = new UserType(5L, "SUPERVISOR");

        UserTypeResponseDto response = mapper.toUserTypeResponseDto(domain);

        assertNotNull(response);
        assertEquals(5L, response.idType());
        assertEquals("SUPERVISOR", response.nameType());
    }

    @Test
    void shouldMapEntityToResponseDto() {
        UserTypeEntity entity = new UserTypeEntity(1L,"ANALISTA");
        entity.setId(7L);

        UserTypeResponseDto response = mapper.toUserTypeResponseDto(entity);

        assertNotNull(response);
        assertEquals(7L, response.idType());
        assertEquals("ANALISTA", response.nameType());
    }
}
