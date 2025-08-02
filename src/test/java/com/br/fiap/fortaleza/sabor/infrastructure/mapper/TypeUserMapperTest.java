package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponse;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("TypeUserMapper Tests")
class TypeUserMapperTest {

    @InjectMocks
    private TypeUserMapper typeUserMapper;

    private TypeUserRequestDto typeUserRequestDto;
    private TypeUser typeUserDomain;
    private TypeEntity typeEntity;

    @BeforeEach
    void setUp() {
        typeUserRequestDto = new TypeUserRequestDto("CLIENTE");
        typeUserDomain = TestDataBuilder.createValidTypeUser();
        typeEntity = TestDataBuilder.createValidTypeEntity();
    }

    @Test
    @DisplayName("Should convert TypeUserRequestDto to TypeUser domain successfully")
    void shouldConvertTypeUserRequestDtoToTypeUserDomainSuccessfully() {
        // Act
        TypeUser result = typeUserMapper.toTypeUserDomain(typeUserRequestDto);

        // Assert
        assertNotNull(result, "Converted type user should not be null");
        assertNull(result.getId(), "ID should be null for new domain object");
        assertEquals(typeUserRequestDto.type(), result.getType(), "Type should match");
    }

    @Test
    @DisplayName("Should convert TypeUser domain to TypeEntity successfully")
    void shouldConvertTypeUserDomainToTypeEntitySuccessfully() {
        // Act
        TypeEntity result = typeUserMapper.toTypeEntity(typeUserDomain);

        // Assert
        assertNotNull(result, "Converted entity should not be null");
        assertNull(result.getId(), "ID should be null for new entity");
        assertEquals(typeUserDomain.getType(), result.getNameType(), "Type should match");
    }

    @Test
    @DisplayName("Should convert TypeEntity to TypeUser domain successfully")
    void shouldConvertTypeEntityToTypeUserDomainSuccessfully() {
        // Act
        TypeUser result = typeUserMapper.toTypeUserEntity(typeEntity);

        // Assert
        assertNotNull(result, "Converted domain should not be null");
        assertEquals(typeEntity.getId(), result.getId(), "ID should match");
        assertEquals(typeEntity.getNameType(), result.getType(), "Type should match");
    }

    @Test
    @DisplayName("Should convert TypeUser domain to TypeUserResponse successfully")
    void shouldConvertTypeUserDomainToTypeUserResponseSuccessfully() {
        // Act
        TypeUserResponse result = typeUserMapper.typeUserResponse(typeUserDomain);

        // Assert
        assertNotNull(result, "Converted response should not be null");
        assertEquals(typeUserDomain.getId(), result.id(), "ID should match");
        assertEquals(typeUserDomain.getType(), result.nameType(), "Type should match");
    }

    @Test
    @DisplayName("Should handle null values gracefully when converting domain to response")
    void shouldHandleNullValuesGracefullyWhenConvertingDomainToResponse() {
        // Arrange
        TypeUser typeUserWithNullId = new TypeUser(null, "ADMIN");

        // Act
        TypeUserResponse result = typeUserMapper.typeUserResponse(typeUserWithNullId);

        // Assert
        assertNotNull(result, "Converted response should not be null");
        assertNull(result.id(), "ID should be null");
        assertEquals("ADMIN", result.nameType(), "Type should match");
    }

    @Test
    @DisplayName("Should preserve data integrity across conversions")
    void shouldPreserveDataIntegrityAcrossConversions() {
        // Arrange
        String originalType = "GERENTE";
        TypeUserRequestDto requestDto = new TypeUserRequestDto(originalType);

        // Act - Chain conversions
        TypeUser domainFromDto = typeUserMapper.toTypeUserDomain(requestDto);
        TypeEntity entityFromDomain = typeUserMapper.toTypeEntity(domainFromDto);
        
        // Simulate entity with ID (as if from database)
        TypeEntity persistedEntity = new TypeEntity(1L, entityFromDomain.getNameType());
        TypeUser domainFromEntity = typeUserMapper.toTypeUserEntity(persistedEntity);
        TypeUserResponse responseFromDomain = typeUserMapper.typeUserResponse(domainFromEntity);

        // Assert - Data integrity maintained
        assertEquals(originalType, domainFromDto.getType(), "Original type preserved in domain");
        assertEquals(originalType, entityFromDomain.getNameType(), "Original type preserved in entity");
        assertEquals(originalType, domainFromEntity.getType(), "Original type preserved back to domain");
        assertEquals(originalType, responseFromDomain.nameType(), "Original type preserved in response");
        assertEquals(1L, responseFromDomain.id(), "ID correctly set from entity");
    }
}
