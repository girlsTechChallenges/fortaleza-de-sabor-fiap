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
@DisplayName("UpdateMenuItemRequestDto Validation Tests")
class UpdateMenuItemRequestDtoTest {

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
        UpdateMenuItemRequestDto dto = createValidUpdateMenuItemRequestDto();

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when nome is null")
    void shouldFailValidationWhenNomeIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            null,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null nome");
    }

    @Test
    @DisplayName("Should fail validation when nome is blank")
    void shouldFailValidationWhenNomeIsBlank() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            "",
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank nome");
    }

    @Test
    @DisplayName("Should fail validation when nome is too short")
    void shouldFailValidationWhenNomeIsTooShort() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            "A",
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short nome");
    }

    @Test
    @DisplayName("Should fail validation when nome is too long")
    void shouldFailValidationWhenNomeIsTooLong() {
        // Arrange
        String longName = "A".repeat(51);
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            longName,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for long nome");
    }

    @Test
    @DisplayName("Should fail validation when nome contains numbers")
    void shouldFailValidationWhenNomeContainsNumbers() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            "Pizza123",
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for nome with numbers");
    }

    @Test
    @DisplayName("Should fail validation when nome contains special characters")
    void shouldFailValidationWhenNomeContainsSpecialCharacters() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            "Pizza@Special",
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for nome with special characters");
    }

    @Test
    @DisplayName("Should accept nome with accented characters")
    void shouldAcceptNomeWithAccentedCharacters() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            "Açaí com Granola",
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for accented characters");
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is null")
    void shouldFailValidationWhenItemDescriptionIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            null,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null itemDescription");
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is blank")
    void shouldFailValidationWhenItemDescriptionIsBlank() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            "",
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank itemDescription");
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is too short")
    void shouldFailValidationWhenItemDescriptionIsTooShort() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            "A",
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short itemDescription");
    }

    @Test
    @DisplayName("Should fail validation when itemDescription is too long")
    void shouldFailValidationWhenItemDescriptionIsTooLong() {
        // Arrange
        String longDescription = "A".repeat(251);
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            longDescription,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for long itemDescription");
    }

    @Test
    @DisplayName("Should fail validation when itemPrice is null")
    void shouldFailValidationWhenItemPriceIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            null,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null itemPrice");
    }

    @Test
    @DisplayName("Should fail validation when itemPrice is blank")
    void shouldFailValidationWhenItemPriceIsBlank() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            "",
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank itemPrice");
    }

    @Test
    @DisplayName("Should fail validation when itemPrice format is invalid")
    void shouldFailValidationWhenItemPriceFormatIsInvalid() {
        // Arrange - Test various invalid price formats
        String[] invalidPrices = {"abc", "12.345", "123456789", "-12.50", "12,50", "12.5"};

        for (String invalidPrice : invalidPrices) {
            UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                TestConstants.VALID_MENU_NAME,
                TestConstants.VALID_MENU_DESCRIPTION,
                invalidPrice,
                true,
                TestConstants.VALID_MENU_IMAGE
            );

            // Act
            Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

            // Assert
            assertFalse(violations.isEmpty(), 
                "Should have validation violations for invalid price format: " + invalidPrice);
        }
    }

    @Test
    @DisplayName("Should accept valid price formats")
    void shouldAcceptValidPriceFormats() {
        // Arrange - Test various valid price formats
        String[] validPrices = {"12.50", "0.99", "100", "9999.99", "12345678"};

        for (String validPrice : validPrices) {
            UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                TestConstants.VALID_MENU_NAME,
                TestConstants.VALID_MENU_DESCRIPTION,
                validPrice,
                true,
                TestConstants.VALID_MENU_IMAGE
            );

            // Act
            Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

            // Assert
            assertTrue(violations.isEmpty(), 
                "Should have no validation violations for valid price: " + validPrice);
        }
    }

    @Test
    @DisplayName("Should fail validation when availability is null")
    void shouldFailValidationWhenAvailabilityIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            null,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null availability");
    }

    @Test
    @DisplayName("Should accept both true and false availability values")
    void shouldAcceptBothTrueAndFalseAvailabilityValues() {
        // Arrange & Act & Assert for true
        UpdateMenuItemRequestDto dtoTrue = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violationsTrue = validator.validate(dtoTrue);
        assertTrue(violationsTrue.isEmpty(), "Should have no validation violations for availability=true");

        // Arrange & Act & Assert for false
        UpdateMenuItemRequestDto dtoFalse = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            false,
            TestConstants.VALID_MENU_IMAGE
        );

        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violationsFalse = validator.validate(dtoFalse);
        assertTrue(violationsFalse.isEmpty(), "Should have no validation violations for availability=false");
    }

    @Test
    @DisplayName("Should fail validation when itemImage is null")
    void shouldFailValidationWhenItemImageIsNull() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            null
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null itemImage");
    }

    @Test
    @DisplayName("Should accept empty itemImage")
    void shouldAcceptEmptyItemImage() {
        // Arrange
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            ""
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for empty itemImage");
    }

    @Test
    @DisplayName("Should accept maximum valid field lengths")
    void shouldAcceptMaximumValidFieldLengths() {
        // Arrange
        String maxName = "A".repeat(50);
        String maxDescription = "B".repeat(250);

        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
            maxName,
            maxDescription,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );

        // Act
        Set<ConstraintViolation<UpdateMenuItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for maximum valid lengths");
    }

    private UpdateMenuItemRequestDto createValidUpdateMenuItemRequestDto() {
        return new UpdateMenuItemRequestDto(
            TestConstants.VALID_MENU_NAME,
            TestConstants.VALID_MENU_DESCRIPTION,
            TestConstants.VALID_MENU_PRICE,
            true,
            TestConstants.VALID_MENU_IMAGE
        );
    }
}
