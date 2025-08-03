package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponse;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("TypeUserMapper Tests")
class TypeUserMapperTest {

    @InjectMocks
    private TypeUserMapper typeUserMapper;

    private TypeUserRequestDto typeUserRequestDto;
    private TypeEntity typeEntity;
    private TypeUser typeUser;

    @BeforeEach
    void setUp() {
        typeUserRequestDto = new TypeUserRequestDto("ADMIN");
        typeEntity = new TypeEntity(1L, "ADMIN");
        typeUser = new TypeUser(1L, "ADMIN");
    }

    @Test
    @DisplayName("Should convert TypeUserRequestDto to TypeUser domain correctly")
    void shouldConvertTypeUserRequestDtoToTypeUserDomainCorrectly() {
        // Act
        TypeUser result = typeUserMapper.toTypeUserDomain(typeUserRequestDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull(); // ID is set to null for new entities
        assertThat(result.getType()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Should convert TypeUser domain to TypeEntity correctly")
    void shouldConvertTypeUserDomainToTypeEntityCorrectly() {
        // Act
        TypeEntity result = typeUserMapper.toTypeEntity(typeUser);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull(); // ID is set to null for new entities
        assertThat(result.getNameType()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Should convert TypeEntity to TypeUser domain correctly")
    void shouldConvertTypeEntityToTypeUserDomainCorrectly() {
        // Act
        TypeUser result = typeUserMapper.toTypeUserEntity(typeEntity);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getType()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Should convert TypeUser to TypeUserResponse correctly")
    void shouldConvertTypeUserToTypeUserResponseCorrectly() {
        // Act
        TypeUserResponse result = typeUserMapper.typeUserResponse(typeUser);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.nameType()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Should convert Optional TypeUser to TypeUserResponse when present")
    void shouldConvertOptionalTypeUserToTypeUserResponseWhenPresent() {
        // Arrange
        Optional<TypeUser> optionalTypeUser = Optional.of(typeUser);

        // Act
        TypeUserResponse result = typeUserMapper.typeUserResponse(optionalTypeUser);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.nameType()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when Optional TypeUser is empty")
    void shouldThrowIllegalArgumentExceptionWhenOptionalTypeUserIsEmpty() {
        // Arrange
        Optional<TypeUser> emptyOptional = Optional.empty();

        // Act & Assert
        assertThatThrownBy(() -> typeUserMapper.typeUserResponse(emptyOptional))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("TypeUser not found");
    }

    @Test
    @DisplayName("Should handle different user types correctly")
    void shouldHandleDifferentUserTypesCorrectly() {
        // Arrange
        TypeUserRequestDto customerRequest = new TypeUserRequestDto("CUSTOMER");
        TypeUserRequestDto employeeRequest = new TypeUserRequestDto("EMPLOYEE");
        TypeUserRequestDto moderatorRequest = new TypeUserRequestDto("MODERATOR");

        // Act
        TypeUser customerResult = typeUserMapper.toTypeUserDomain(customerRequest);
        TypeUser employeeResult = typeUserMapper.toTypeUserDomain(employeeRequest);
        TypeUser moderatorResult = typeUserMapper.toTypeUserDomain(moderatorRequest);

        // Assert
        assertThat(customerResult.getType()).isEqualTo("CUSTOMER");
        assertThat(employeeResult.getType()).isEqualTo("EMPLOYEE");
        assertThat(moderatorResult.getType()).isEqualTo("MODERATOR");
    }

    @Test
    @DisplayName("Should preserve data integrity during bidirectional conversion")
    void shouldPreserveDataIntegrityDuringBidirectionalConversion() {
        // Arrange
        TypeUser originalTypeUser = new TypeUser(5L, "MANAGER");

        // Act - Convert to entity and back to domain
        TypeEntity entity = typeUserMapper.toTypeEntity(originalTypeUser);
        entity.setId(5L); // Simulate ID assignment after persistence
        TypeUser convertedBack = typeUserMapper.toTypeUserEntity(entity);

        // Assert
        assertThat(convertedBack).isNotNull();
        assertThat(convertedBack.getId()).isEqualTo(originalTypeUser.getId());
        assertThat(convertedBack.getType()).isEqualTo(originalTypeUser.getType());
    }

    @Test
    @DisplayName("Should handle null values appropriately in domain conversion")
    void shouldHandleNullValuesAppropriatelyInDomainConversion() {
        // Arrange
        TypeUser typeUserWithNullId = new TypeUser(null, "GUEST");

        // Act
        TypeEntity result = typeUserMapper.toTypeEntity(typeUserWithNullId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getNameType()).isEqualTo("GUEST");
    }

    @Test
    @DisplayName("Should convert TypeEntity with different IDs correctly")
    void shouldConvertTypeEntityWithDifferentIdsCorrectly() {
        // Arrange
        TypeEntity entityWithId999 = new TypeEntity(999L, "SUPER_ADMIN");

        // Act
        TypeUser result = typeUserMapper.toTypeUserEntity(entityWithId999);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(999L);
        assertThat(result.getType()).isEqualTo("SUPER_ADMIN");
    }

    @Test
    @DisplayName("Should create TypeUserResponse with correct field mapping")
    void shouldCreateTypeUserResponseWithCorrectFieldMapping() {
        // Arrange
        TypeUser typeUserWithSpecialChars = new TypeUser(42L, "SPECIALIZED_USER");

        // Act
        TypeUserResponse result = typeUserMapper.typeUserResponse(typeUserWithSpecialChars);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(42L);
        assertThat(result.nameType()).isEqualTo("SPECIALIZED_USER");
    }
}
