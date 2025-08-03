package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("OwnerRequestDto Validation Tests")
class OwnerRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should pass validation with valid owner request")
    void shouldPassValidationWithValidOwnerRequest() {
        // Arrange
        OwnerRequestDto ownerDto = new OwnerRequestDto("João Silva", "joao@email.com");

        // Act
        Set<ConstraintViolation<OwnerRequestDto>> violations = validator.validate(ownerDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when owner is null")
    void shouldFailValidationWhenOwnerIsNull() {
        // Arrange
        OwnerRequestDto ownerDto = new OwnerRequestDto(null, "joao@email.com");

        // Act
        Set<ConstraintViolation<OwnerRequestDto>> violations = validator.validate(ownerDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when owner is blank")
    void shouldFailValidationWhenOwnerIsBlank() {
        // Arrange
        OwnerRequestDto ownerDto = new OwnerRequestDto("   ", "joao@email.com");

        // Act
        Set<ConstraintViolation<OwnerRequestDto>> violations = validator.validate(ownerDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when email is null")
    void shouldFailValidationWhenEmailIsNull() {
        // Arrange
        OwnerRequestDto ownerDto = new OwnerRequestDto("João Silva", null);

        // Act
        Set<ConstraintViolation<OwnerRequestDto>> violations = validator.validate(ownerDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when email is blank")
    void shouldFailValidationWhenEmailIsBlank() {
        // Arrange
        OwnerRequestDto ownerDto = new OwnerRequestDto("João Silva", "   ");

        // Act
        Set<ConstraintViolation<OwnerRequestDto>> violations = validator.validate(ownerDto);

        // Assert
        assertThat(violations).hasSize(2); // Both @NotBlank and @Email will fail
    }

    @Test
    @DisplayName("Should fail validation when email format is invalid")
    void shouldFailValidationWhenEmailFormatIsInvalid() {
        // Arrange
        OwnerRequestDto ownerDto = new OwnerRequestDto("João Silva", "email-invalido");

        // Act
        Set<ConstraintViolation<OwnerRequestDto>> violations = validator.validate(ownerDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("email");
    }

    @Test
    @DisplayName("Should fail validation when both fields are invalid")
    void shouldFailValidationWhenBothFieldsAreInvalid() {
        // Arrange
        OwnerRequestDto ownerDto = new OwnerRequestDto("", "email-invalido");

        // Act
        Set<ConstraintViolation<OwnerRequestDto>> violations = validator.validate(ownerDto);

        // Assert
        assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        OwnerRequestDto owner1 = new OwnerRequestDto("João Silva", "joao@email.com");
        OwnerRequestDto owner2 = new OwnerRequestDto("João Silva", "joao@email.com");
        OwnerRequestDto owner3 = new OwnerRequestDto("Maria Silva", "maria@email.com");

        // Act & Assert
        assertThat(owner1.owner()).isEqualTo("João Silva");
        assertThat(owner1.email()).isEqualTo("joao@email.com");
        
        assertThat(owner1).isEqualTo(owner2);
        assertThat(owner1).isNotEqualTo(owner3);
        assertThat(owner1.hashCode()).isEqualTo(owner2.hashCode());
        assertThat(owner1.toString()).contains("João Silva");
        assertThat(owner1.toString()).contains("joao@email.com");
    }

    @Test
    @DisplayName("Should accept empty string as valid for owner")
    void shouldAcceptEmptyStringAsValidForOwner() {
        // Arrange
        OwnerRequestDto ownerDto = new OwnerRequestDto("", "joao@email.com");

        // Act
        Set<ConstraintViolation<OwnerRequestDto>> violations = validator.validate(ownerDto);

        // Assert
        assertThat(violations).hasSize(1); // Only owner blank validation should fail
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }
}
