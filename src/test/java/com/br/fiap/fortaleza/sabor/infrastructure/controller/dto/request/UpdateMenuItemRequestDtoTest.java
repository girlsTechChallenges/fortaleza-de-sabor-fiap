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

@DisplayName("UpdateMenuItemRequestDto Validation Tests")
class UpdateMenuItemRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should pass validation with valid UpdateMenuItemRequestDto")
    void shouldPassValidationWithValidUpdateMenuItemRequestDto() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza Margherita",
                "Delicious pizza with tomato and mozzarella",
                "25.90",
                true,
                "pizza.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when nome is null")
    void shouldFailValidationWhenNomeIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                null,
                "Description",
                "25.90",
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(2); // @NotNull and @NotBlank violations
        assertThat(violations.stream().anyMatch(v -> v.getMessage().contains("nulo"))).isTrue();
    }

    @Test
    @DisplayName("Should fail validation when nome is empty")
    void shouldFailValidationWhenNomeIsEmpty() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "",
                "Description",
                "25.90",
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(3); // @NotBlank, @Size and @Pattern violations
    }

    @Test
    @DisplayName("Should fail validation when nome is too short")
    void shouldFailValidationWhenNomeIsTooShort() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "P",
                "Description",
                "25.90",
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("tamanho deve ser entre 2 e 50");
    }

    @Test
    @DisplayName("Should fail validation when nome is too long")
    void shouldFailValidationWhenNomeIsTooLong() {
        // Arrange
        String longName = "P".repeat(51);
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                longName,
                "Description",
                "25.90",
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("tamanho deve ser entre 2 e 50");
    }

    @Test
    @DisplayName("Should fail validation when nome contains invalid characters")
    void shouldFailValidationWhenNomeContainsInvalidCharacters() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza123",
                "Description",
                "25.90",
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The name must contain only letters");
    }

    @Test
    @DisplayName("Should pass validation with accented characters in nome")
    void shouldPassValidationWithAccentedCharactersInNome() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Açaí com Granola",
                "Delicious açaí bowl",
                "15.50",
                true,
                "acai.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is null")
    void shouldFailValidationWhenItemDescriptionIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza",
                null,
                "25.90",
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(2); // @NotNull and @NotBlank violations
        assertThat(violations.stream().anyMatch(v -> v.getMessage().contains("nulo"))).isTrue();
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is empty")
    void shouldFailValidationWhenItemDescriptionIsEmpty() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza",
                "",
                "25.90",
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(2); // @NotBlank and @Size violations
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is too long")
    void shouldFailValidationWhenItemDescriptionIsTooLong() {
        // Arrange
        String longDescription = "D".repeat(251);
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza",
                longDescription,
                "25.90",
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("tamanho deve ser entre 2 e 250");
    }

    @Test
    @DisplayName("Should fail validation when itemPrice is null")
    void shouldFailValidationWhenItemPriceIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza",
                "Description",
                null,
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(2); // @NotNull and @NotBlank violations
        assertThat(violations.stream().anyMatch(v -> v.getMessage().contains("nulo"))).isTrue();
    }

    @Test
    @DisplayName("Should fail validation when itemPrice has invalid format")
    void shouldFailValidationWhenItemPriceHasInvalidFormat() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza",
                "Description",
                "invalid-price",
                true,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("The price must be a valid number");
    }

    @Test
    @DisplayName("Should pass validation with different valid price formats")
    void shouldPassValidationWithDifferentValidPriceFormats() {
        // Arrange & Act & Assert
        String[] validPrices = {"1", "12", "123", "1234", "12345", "123456", "1234567", "12345678", "12.34", "1.00", "999.99"};
        
        for (String price : validPrices) {
            UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                    "Pizza",
                    "Description",
                    price,
                    true,
                    "image.jpg"
            );
            Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);
            assertThat(violations).isEmpty();
        }
    }

    @Test
    @DisplayName("Should fail validation when availability is null")
    void shouldFailValidationWhenAvailabilityIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza",
                "Description",
                "25.90",
                null,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("nulo");
    }

    @Test
    @DisplayName("Should pass validation with availability false")
    void shouldPassValidationWithAvailabilityFalse() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza",
                "Description",
                "25.90",
                false,
                "image.jpg"
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when itemImage is null")
    void shouldFailValidationWhenItemImageIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza",
                "Description",
                "25.90",
                true,
                null
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("nulo");
    }

    @Test
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        UpdateMenuItemRequestDto dto1 = new UpdateMenuItemRequestDto("Pizza", "Description", "25.90", true, "image.jpg");
        UpdateMenuItemRequestDto dto2 = new UpdateMenuItemRequestDto("Pizza", "Description", "25.90", true, "image.jpg");
        UpdateMenuItemRequestDto dto3 = new UpdateMenuItemRequestDto("Pasta", "Description", "25.90", true, "image.jpg");

        // Act & Assert
        assertThat(dto1.nome()).isEqualTo("Pizza");
        assertThat(dto1.itemDescription()).isEqualTo("Description");
        assertThat(dto1.itemPrice()).isEqualTo("25.90");
        assertThat(dto1.availability()).isTrue();
        assertThat(dto1.itemImage()).isEqualTo("image.jpg");
        
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
        assertThat(dto1.toString()).contains("Pizza");
    }
}
