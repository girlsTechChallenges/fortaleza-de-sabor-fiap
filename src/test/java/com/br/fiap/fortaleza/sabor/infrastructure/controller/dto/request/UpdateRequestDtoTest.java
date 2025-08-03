package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UpdateRequestDto Validation Tests")
class UpdateRequestDtoTest {

    private Validator validator;
    private List<AddressDto> validAddress;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        validAddress = List.of(
            new AddressDto("Rua das Flores", "Centro", "Apt 123", 123, "SP", "São Paulo", "01234567")
        );
    }

    @Test
    @DisplayName("Should pass validation with valid update request")
    void shouldPassValidationWithValidUpdateRequest() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            "João Silva",
            "joao@email.com",
            "password123",
            "ADMIN",
            validAddress
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when nome is null")
    void shouldFailValidationWhenNomeIsNull() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            null,
            "joao@email.com",
            "password123",
            "ADMIN",
            validAddress
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSizeGreaterThanOrEqualTo(1);
        assertThat(violations.stream().anyMatch(v -> v.getMessage().contains("nulo") || v.getMessage().contains("empty"))).isTrue();
    }

    @Test
    @DisplayName("Should fail validation when nome is blank")
    void shouldFailValidationWhenNomeIsBlank() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            "   ",
            "joao@email.com",
            "password123",
            "ADMIN",
            validAddress
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("branco");
    }

    @Test
    @DisplayName("Should fail validation when nome is too short")
    void shouldFailValidationWhenNomeIsTooShort() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            "A",
            "joao@email.com",
            "password123",
            "ADMIN",
            validAddress
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("tamanho deve ser entre");
    }

    @Test
    @DisplayName("Should fail validation when email is null")
    void shouldFailValidationWhenEmailIsNull() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            "João Silva",
            null,
            "password123",
            "ADMIN",
            validAddress
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSizeGreaterThanOrEqualTo(1);
        assertThat(violations.stream().anyMatch(v -> v.getMessage().contains("nulo"))).isTrue();
    }

    @Test
    @DisplayName("Should fail validation when email format is invalid")
    void shouldFailValidationWhenEmailFormatIsInvalid() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            "João Silva",
            "email-invalido",
            "password123",
            "ADMIN",
            validAddress
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("e-mail");
    }

    @Test
    @DisplayName("Should fail validation when senha is too short")
    void shouldFailValidationWhenSenhaIsTooShort() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            "João Silva",
            "joao@email.com",
            "123456",
            "ADMIN",
            validAddress
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("tamanho deve ser entre");
    }

    @Test
    @DisplayName("Should fail validation when tipo is null")
    void shouldFailValidationWhenTipoIsNull() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            "João Silva",
            "joao@email.com",
            "password123",
            null,
            validAddress
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("nulo");
    }

    @Test
    @DisplayName("Should fail validation when address list is null")
    void shouldFailValidationWhenAddressListIsNull() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            "João Silva",
            "joao@email.com",
            "password123",
            "ADMIN",
            null
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("nulo");
    }

    @Test
    @DisplayName("Should fail validation when address list is empty")
    void shouldFailValidationWhenAddressListIsEmpty() {
        // Arrange
        UpdateRequestDto updateDto = new UpdateRequestDto(
            "João Silva",
            "joao@email.com",
            "password123",
            "ADMIN",
            List.of()
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("tamanho deve ser entre");
    }

    @Test
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        UpdateRequestDto update1 = new UpdateRequestDto(
            "João Silva",
            "joao@email.com",
            "password123",
            "ADMIN",
            validAddress
        );
        
        UpdateRequestDto update2 = new UpdateRequestDto(
            "João Silva",
            "joao@email.com",
            "password123",
            "ADMIN",
            validAddress
        );

        // Act & Assert
        assertThat(update1.nome()).isEqualTo("João Silva");
        assertThat(update1.email()).isEqualTo("joao@email.com");
        assertThat(update1.senha()).isEqualTo("password123");
        assertThat(update1.tipo()).isEqualTo("ADMIN");
        assertThat(update1.address()).isEqualTo(validAddress);
        
        assertThat(update1).isEqualTo(update2);
        assertThat(update1.hashCode()).isEqualTo(update2.hashCode());
        assertThat(update1.toString()).contains("João Silva");
    }
}
