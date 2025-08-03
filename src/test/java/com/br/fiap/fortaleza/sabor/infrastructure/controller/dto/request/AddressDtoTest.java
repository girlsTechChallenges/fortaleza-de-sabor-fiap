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
                "Rua das Flores",
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
    @DisplayName("Should fail validation when rua is null")
    void shouldFailValidationWhenRuaIsNull() {
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
    @DisplayName("Should fail validation when rua is empty")
    void shouldFailValidationWhenRuaIsEmpty() {
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
    @DisplayName("Should fail validation when rua is too short")
    void shouldFailValidationWhenRuaIsTooShort() {
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
    @DisplayName("Should fail validation when rua is too long")
    void shouldFailValidationWhenRuaIsTooLong() {
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
    @DisplayName("Should fail validation when bairro is blank")
    void shouldFailValidationWhenBairroIsBlank() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should pass validation when complemento is null")
    void shouldPassValidationWhenComplementoIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should pass validation when complemento is empty")
    void shouldPassValidationWhenComplementoIsEmpty() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should fail validation when complemento is too long")
    void shouldFailValidationWhenComplementoIsTooLong() {
        // Arrange
        String longComplemento = "C".repeat(101);
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should fail validation when numero is null")
    void shouldFailValidationWhenNumeroIsNull() {
        // Arrange - Can't test with null int in record constructor, so we'll test with 0
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should fail validation when numero is negative")
    void shouldFailValidationWhenNumeroIsNegative() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should fail validation when estado is blank")
    void shouldFailValidationWhenEstadoIsBlank() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should fail validation when cidade is blank")
    void shouldFailValidationWhenCidadeIsBlank() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should fail validation when cep is null")
    void shouldFailValidationWhenCepIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should fail validation when cep has invalid format")
    void shouldFailValidationWhenCepHasInvalidFormat() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
    @DisplayName("Should fail validation when cep contains letters")
    void shouldFailValidationWhenCepContainsLetters() {
        // Arrange
        AddressDto addressDto = new AddressDto(
                "Rua das Flores",
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
                "R".repeat(100), // Max length for rua
                "B".repeat(100), // Max length for bairro
                "C".repeat(100), // Max length for complemento
                999999,
                "E".repeat(50), // Max length for estado
                "C".repeat(50), // Max length for cidade
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
        AddressDto address1 = new AddressDto("Rua A", "Centro", "Apt 1", 123, "SP", "São Paulo", "12345678");
        AddressDto address2 = new AddressDto("Rua A", "Centro", "Apt 1", 123, "SP", "São Paulo", "12345678");
        AddressDto address3 = new AddressDto("Rua B", "Centro", "Apt 1", 123, "SP", "São Paulo", "12345678");

        // Act & Assert
        assertThat(address1.rua()).isEqualTo("Rua A");
        assertThat(address1.bairro()).isEqualTo("Centro");
        assertThat(address1.complemento()).isEqualTo("Apt 1");
        assertThat(address1.numero()).isEqualTo(123);
        assertThat(address1.estado()).isEqualTo("SP");
        assertThat(address1.cidade()).isEqualTo("São Paulo");
        assertThat(address1.cep()).isEqualTo("12345678");
        
        assertThat(address1).isEqualTo(address2);
        assertThat(address1).isNotEqualTo(address3);
        assertThat(address1.hashCode()).isEqualTo(address2.hashCode());
        assertThat(address1.toString()).contains("Rua A");
    }
}
