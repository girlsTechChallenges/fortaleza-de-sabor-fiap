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

@DisplayName("MenuItemRequestDto Validation Tests")
class MenuItemRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should pass validation with valid MenuItemRequestDto")
    void shouldPassValidationWithValidMenuItemRequestDto() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when name is null")
    void shouldFailValidationWhenNomeIsNull() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                null,
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("Should fail validation when name is blank")
    void shouldFailValidationWhenNomeIsBlank() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "   ",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("Should fail validation when name is too short")
    void shouldFailValidationWhenNomeIsTooShort() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "P",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("Should fail validation when name is too long")
    void shouldFailValidationWhenNomeIsTooLong() {
        // Arrange
        String longName = "A".repeat(51);
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                longName,
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("Should fail validation when name contains numbers")
    void shouldFailValidationWhenNomeContainsNumbers() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza123",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
        assertThat(violations).anyMatch(v -> v.getMessage().contains("The name must contain only letters"));
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is null")
    void shouldFailValidationWhenItemDescriptionIsNull() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                null,
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("itemDescription"));
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is too short")
    void shouldFailValidationWhenItemDescriptionIsTooShort() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "A",
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("itemDescription"));
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is too long")
    void shouldFailValidationWhenItemDescriptionIsTooLong() {
        // Arrange
        String longDescription = "A".repeat(251);
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                longDescription,
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("itemDescription"));
    }

    @Test
    @DisplayName("Should fail validation when itemPrice is null")
    void shouldFailValidationWhenItemPriceIsNull() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                null,
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("itemPrice"));
    }

    @Test
    @DisplayName("Should fail validation when itemPrice has invalid format")
    void shouldFailValidationWhenItemPriceHasInvalidFormat() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.999", // 3 decimal places - invalid
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("itemPrice"));
        assertThat(violations).anyMatch(v -> v.getMessage().contains("The price must be a valid number"));
    }

    @Test
    @DisplayName("Should pass validation with valid integer price")
    void shouldPassValidationWithValidIntegerPrice() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should pass validation with valid decimal price")
    void shouldPassValidationWithValidDecimalPrice() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.50",
                true,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when availability is null")
    void shouldFailValidationWhenAvailabilityIsNull() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                null,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("availability"));
    }

    @Test
    @DisplayName("Should pass validation when availability is false")
    void shouldPassValidationWhenAvailabilityIsFalse() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                false,
                "pizza-margherita.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when itemImage is null")
    void shouldFailValidationWhenItemImageIsNull() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                true,
                null
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("itemImage"));
    }

    @Test
    @DisplayName("Should accept valid names with accents and spaces")
    void shouldAcceptValidNamesWithAccentsAndSpaces() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Açaí com Granola",
                "Delicious açaí bowl with granola and fruits",
                "15.90",
                true,
                "acai-granola.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should accept large valid price")
    void shouldAcceptLargeValidPrice() {
        // Arrange
        MenuItemRequestDto menuItemDto = new MenuItemRequestDto(
                "Premium Dish",
                "An expensive premium dish for special occasions",
                "99999999.99",
                true,
                "premium-dish.jpg"
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemDto);

        // Assert
        assertThat(violations).isEmpty();
    }
}
