package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import com.br.fiap.fortaleza.sabor.utils.TestConstants;
import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateRequestDto Validation Tests")
class UpdateRequestDtoTest {

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
        UpdateRequestDto dto = createValidUpdateRequestDto();

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when nome is null")
    void shouldFailValidationWhenNomeIsNull() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            null,
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null nome");
    }

    @Test
    @DisplayName("Should fail validation when nome is blank")
    void shouldFailValidationWhenNomeIsBlank() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            "",
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank nome");
    }

    @Test
    @DisplayName("Should fail validation when nome is too short")
    void shouldFailValidationWhenNomeIsTooShort() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            "A",
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short nome");
    }

    @Test
    @DisplayName("Should fail validation when nome is too long")
    void shouldFailValidationWhenNomeIsTooLong() {
        // Arrange
        String longName = "A".repeat(51);
        UpdateRequestDto dto = new UpdateRequestDto(
            longName,
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for long nome");
    }

    @Test
    @DisplayName("Should fail validation when email is null")
    void shouldFailValidationWhenEmailIsNull() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            null,
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null email");
    }

    @Test
    @DisplayName("Should fail validation when email is blank")
    void shouldFailValidationWhenEmailIsBlank() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            "",
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank email");
    }

    @Test
    @DisplayName("Should fail validation when email format is invalid")
    void shouldFailValidationWhenEmailFormatIsInvalid() {
        // Arrange - Test various invalid email formats
        String[] invalidEmails = {"invalid", "invalid@", "@invalid.com", "invalid@.com", "invalid..email@domain.com"};

        for (String invalidEmail : invalidEmails) {
            UpdateRequestDto dto = new UpdateRequestDto(
                TestConstants.VALID_USER_NAME,
                invalidEmail,
                TestConstants.VALID_PASSWORD,
                TestDataBuilder.createValidTypeUserRequestDto(),
                Arrays.asList(TestDataBuilder.createValidAddressDto())
            );

            // Act
            Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

            // Assert
            assertFalse(violations.isEmpty(), 
                "Should have validation violations for invalid email format: " + invalidEmail);
        }
    }

    @Test
    @DisplayName("Should fail validation when senha is null")
    void shouldFailValidationWhenSenhaIsNull() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_EMAIL,
            null,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null senha");
    }

    @Test
    @DisplayName("Should fail validation when senha is blank")
    void shouldFailValidationWhenSenhaIsBlank() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_EMAIL,
            "",
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank senha");
    }

    @Test
    @DisplayName("Should fail validation when senha is too short")
    void shouldFailValidationWhenSenhaIsTooShort() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_EMAIL,
            "1234567", // 7 characters, minimum is 8
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short senha");
    }

    @Test
    @DisplayName("Should fail validation when senha is too long")
    void shouldFailValidationWhenSenhaIsTooLong() {
        // Arrange
        String longPassword = "A".repeat(101);
        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_EMAIL,
            longPassword,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for long senha");
    }

    @Test
    @DisplayName("Should fail validation when tipo is null")
    void shouldFailValidationWhenTipoIsNull() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD,
            null,
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null tipo");
    }

    @Test
    @DisplayName("Should fail validation when address is null")
    void shouldFailValidationWhenAddressIsNull() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            null
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null address");
    }

    @Test
    @DisplayName("Should fail validation when address list is empty")
    void shouldFailValidationWhenAddressListIsEmpty() {
        // Arrange
        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            new ArrayList<>()
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for empty address list");
    }

    @Test
    @DisplayName("Should allow multiple addresses")
    void shouldAllowMultipleAddresses() {
        // Arrange
        List<AddressDto> addresses = Arrays.asList(
            TestDataBuilder.createValidAddressDto(),
            new AddressDto(
                "Rua Secundária",
                "Bairro Novo",
                "Casa 2",
                456,
                "Rio de Janeiro", 
                "Rio de Janeiro",
                "12345678"
            )
        );

        UpdateRequestDto dto = new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            addresses
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for multiple addresses");
    }

    @Test
    @DisplayName("Should accept maximum valid field lengths")
    void shouldAcceptMaximumValidFieldLengths() {
        // Arrange
        String maxName = "A".repeat(50);
        String maxPassword = "B".repeat(100);

        UpdateRequestDto dto = new UpdateRequestDto(
            maxName,
            TestConstants.VALID_EMAIL,
            maxPassword,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for maximum valid lengths");
    }

    @Test
    @DisplayName("Should accept minimum valid field lengths")
    void shouldAcceptMinimumValidFieldLengths() {
        // Arrange
        String minName = "AB"; // 2 characters
        String minPassword = "12345678"; // 8 characters

        UpdateRequestDto dto = new UpdateRequestDto(
            minName,
            TestConstants.VALID_EMAIL,
            minPassword,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );

        // Act
        Set<ConstraintViolation<UpdateRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for minimum valid lengths");
    }

    private UpdateRequestDto createValidUpdateRequestDto() {
        return new UpdateRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD,
            TestDataBuilder.createValidTypeUserRequestDto(),
            Arrays.asList(TestDataBuilder.createValidAddressDto())
        );
    }
}
