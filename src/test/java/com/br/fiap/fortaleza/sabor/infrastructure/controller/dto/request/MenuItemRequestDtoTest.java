package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import com.br.fiap.fortaleza.sabor.utils.TestConstants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("MenuItemRequestDto Validation Tests")
class MenuItemRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should pass validation with valid data")
    void shouldPassValidationWithValidData() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = createValidMenuItemRequestDto();

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when name is null")
    void shouldFailValidationWhenNameIsNull() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            null,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            TestConstants.VALID_MENU_AVAILABILITY,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null name");
    }

    @Test
    @DisplayName("Should fail validation when name is blank")
    void shouldFailValidationWhenNameIsBlank() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            "",
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            TestConstants.VALID_MENU_AVAILABILITY,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank name");
    }

    @Test
    @DisplayName("Should fail validation when name is too short")
    void shouldFailValidationWhenNameIsTooShort() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            "A",
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            TestConstants.VALID_MENU_AVAILABILITY,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short name");
    }

    @Test
    @DisplayName("Should fail validation when name contains numbers")
    void shouldFailValidationWhenNameContainsNumbers() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            "Pizza123",
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            TestConstants.VALID_MENU_AVAILABILITY,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for invalid name pattern");
    }

    @Test
    @DisplayName("Should fail validation when description is null")
    void shouldFailValidationWhenDescriptionIsNull() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            null,
            TestConstants.VALID_MENU_PRICE,
            TestConstants.VALID_MENU_AVAILABILITY,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null description");
    }

    @Test
    @DisplayName("Should fail validation when description is too short")
    void shouldFailValidationWhenDescriptionIsTooShort() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            "A",
            TestConstants.VALID_MENU_PRICE,
            TestConstants.VALID_MENU_AVAILABILITY,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short description");
    }

    @Test
    @DisplayName("Should fail validation when price is null")
    void shouldFailValidationWhenPriceIsNull() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            null,
            TestConstants.VALID_MENU_AVAILABILITY,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null price");
    }

    @Test
    @DisplayName("Should fail validation when price format is invalid")
    void shouldFailValidationWhenPriceFormatIsInvalid() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            "invalid-price",
            TestConstants.VALID_MENU_AVAILABILITY,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for invalid price format");
    }

    @Test
    @DisplayName("Should fail validation when availability is null")
    void shouldFailValidationWhenAvailabilityIsNull() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            null,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null availability");
    }

    @Test
    @DisplayName("Should allow valid price formats")
    void shouldAllowValidPriceFormats() {
        // Test cases for valid prices
        String[] validPrices = {"10.50", "100", "1.99", "999.99", "12345678.99"};
        
        for (String validPrice : validPrices) {
            // Arrange
            MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
                TestConstants.VALID_MENU_NAME,
                TestConstants.VALID_MENU_DESCRIPTION,
                validPrice,
                TestConstants.VALID_MENU_AVAILABILITY,
                TestConstants.VALID_MENU_IMAGE
            );

            // Act
            Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

            // Assert
            assertTrue(violations.isEmpty(), 
                "Should have no validation violations for valid price: " + validPrice);
        }
    }

    @Test
    @DisplayName("Should reject invalid price formats")
    void shouldRejectInvalidPriceFormats() {
        // Test cases for invalid prices
        String[] invalidPrices = {"10.5", "10.123", "abc", "10.99.99", "123456789.99", "-10.50"};
        
        for (String invalidPrice : invalidPrices) {
            // Arrange
            MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
                TestConstants.VALID_MENU_NAME,
                TestConstants.VALID_MENU_DESCRIPTION,
                invalidPrice,
                TestConstants.VALID_MENU_AVAILABILITY,
                TestConstants.VALID_MENU_IMAGE
            );

            // Act
            Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

            // Assert
            assertFalse(violations.isEmpty(), 
                "Should have validation violations for invalid price: " + invalidPrice);
        }
    }

    @Test
    @DisplayName("Should fail validation when image is null")
    void shouldFailValidationWhenImageIsNull() {
        // Arrange
        MenuItemRequestDto menuItemRequestDto = new MenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            TestConstants.VALID_MENU_AVAILABILITY,
            null
        );

        // Act
        Set<ConstraintViolation<MenuItemRequestDto>> violations = validator.validate(menuItemRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations when image is null");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null image");
    }

    private MenuItemRequestDto createValidMenuItemRequestDto() {
        return new MenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            TestConstants.VALID_MENU_AVAILABILITY,
            TestConstants.VALID_MENU_IMAGE
        );
    }
}
