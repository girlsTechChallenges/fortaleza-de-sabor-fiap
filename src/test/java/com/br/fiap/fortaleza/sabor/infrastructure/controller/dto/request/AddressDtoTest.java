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

@DisplayName("AddressDto Validation Tests")
class AddressDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should pass validation with valid AddressDto")
    void shouldPassValidationWithValidAddressDto() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when street is null")
    void shouldFailValidationWhenStreetIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                null,
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("branco");
    }

    @Test
    @DisplayName("Should fail validation when street is empty")
    void shouldFailValidationWhenStreetIsEmpty() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "",
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(2); // @NotBlank and @Size violations
    }

    @Test
    @DisplayName("Should fail validation when street is too short")
    void shouldFailValidationWhenStreetIsTooShort() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "R",
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("tamanho deve ser entre 2 e 100");
    }

    @Test
    @DisplayName("Should fail validation when street is too long")
    void shouldFailValidationWhenStreetIsTooLong() {
        // Arrange
        String longRua = "R".repeat(101);
        AddressDto addressDto = new AddressDto(
                longRua,
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("tamanho deve ser entre 2 e 100");
    }

    @Test
    @DisplayName("Should fail validation when district is blank")
    void shouldFailValidationWhenBairroIsBlank() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(2); // @NotBlank and @Size violations
    }

    @Test
    @DisplayName("Should pass validation when complement is null")
    void shouldPassValidationWhenComplementoIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                null,
                123,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should pass validation when complement is empty")
    void shouldPassValidationWhenComplementoIsEmpty() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "",
                123,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when complement is too long")
    void shouldFailValidationWhenComplementoIsTooLong() {
        // Arrange
        String longComplemento = "C".repeat(101);
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                longComplemento,
                123,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("tamanho deve ser entre 0 e 100");
    }

    @Test
    @DisplayName("Should fail validation when number is null")
    void shouldFailValidationWhenNumeroIsNull() {
        // Arrange - Can't test with null int in record constructor, so we'll test with 0
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                0,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("deve ser maior que 0");
    }

    @Test
    @DisplayName("Should fail validation when number is negative")
    void shouldFailValidationWhenNumeroIsNegative() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                -1,
                "SP",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("deve ser maior que 0");
    }

    @Test
    @DisplayName("Should fail validation when state is blank")
    void shouldFailValidationWhenEstadoIsBlank() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                123,
                "",
                "São Paulo",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(2); // @NotBlank and @Size violations
    }

    @Test
    @DisplayName("Should fail validation when city is blank")
    void shouldFailValidationWhenCidadeIsBlank() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                123,
                "SP",
                "",
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(2); // @NotBlank and @Size violations
    }

    @Test
    @DisplayName("Should fail validation when postCode is null")
    void shouldFailValidationWhenCepIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                null
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("nulo");
    }

    @Test
    @DisplayName("Should fail validation when postCode has invalid format")
    void shouldFailValidationWhenCepHasInvalidFormat() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "1234567" // 7 digits instead of 8
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The zip code must contain exactly 8 digits");
    }

    @Test
    @DisplayName("Should fail validation when postCode contains letters")
    void shouldFailValidationWhenCepContainsLetters() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "1234567A"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The zip code must contain exactly 8 digits");
    }

    @Test
    @DisplayName("Should pass validation with maximum field lengths")
    void shouldPassValidationWithMaximumFieldLengths() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "R".repeat(100), // Max length for street
                "B".repeat(100), // Max length for district
                "C".repeat(100), // Max length for complement
                999999,
                "E".repeat(50), // Max length for state
                "C".repeat(50), // Max length for city
                "12345678"
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        AddressDto address1 = new AddressDto("Street A", "Centro", "Apt 1", 123, "SP", "São Paulo", "12345678");
        AddressDto address2 = new AddressDto("Street A", "Centro", "Apt 1", 123, "SP", "São Paulo", "12345678");
        AddressDto address3 = new AddressDto("Street B", "Centro", "Apt 1", 123, "SP", "São Paulo", "12345678");

        // Act & Assert
        assertThat(address1.street()).isEqualTo("Street A");
        assertThat(address1.district()).isEqualTo("Centro");
        assertThat(address1.complement()).isEqualTo("Apt 1");
        assertThat(address1.number()).isEqualTo(123);
        assertThat(address1.state()).isEqualTo("SP");
        assertThat(address1.city()).isEqualTo("São Paulo");
        assertThat(address1.postCode()).isEqualTo("12345678");
        
        assertThat(address1).isEqualTo(address2);
        assertThat(address1).isNotEqualTo(address3);
        assertThat(address1.hashCode()).isEqualTo(address2.hashCode());
        assertThat(address1.toString()).contains("Street A");
    }
}
