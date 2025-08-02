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

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UserRequestDto Validation Tests")
class UserRequestDtoTest {

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
        UserRequestDto userRequestDto = createValidUserRequestDto();

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when name is null")
    void shouldFailValidationWhenNameIsNull() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
            null,
            TestConstants.VALID_USER_EMAIL,
            TestConstants.VALID_USER_LOGIN,
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            createValidAddressList()
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null name");
    }

    @Test
    @DisplayName("Should fail validation when name is blank")
    void shouldFailValidationWhenNameIsBlank() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
            "",
            TestConstants.VALID_USER_EMAIL,
            TestConstants.VALID_USER_LOGIN,
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            createValidAddressList()
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank name");
    }

    @Test
    @DisplayName("Should fail validation when name is too short")
    void shouldFailValidationWhenNameIsTooShort() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
            "A",
            TestConstants.VALID_USER_EMAIL,
            TestConstants.VALID_USER_LOGIN,
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            createValidAddressList()
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short name");
    }

    @Test
    @DisplayName("Should fail validation when name contains numbers")
    void shouldFailValidationWhenNameContainsNumbers() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
            "João123",
            TestConstants.VALID_USER_EMAIL,
            TestConstants.VALID_USER_LOGIN,
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            createValidAddressList()
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for invalid name pattern");
    }

    @Test
    @DisplayName("Should fail validation when email is invalid")
    void shouldFailValidationWhenEmailIsInvalid() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
            TestConstants.VALID_USER_NAME,
            "email-invalido",
            TestConstants.VALID_USER_LOGIN,
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            createValidAddressList()
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for invalid email");
    }

    @Test
    @DisplayName("Should fail validation when login is too short")
    void shouldFailValidationWhenLoginIsTooShort() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_USER_EMAIL,
            "abc",
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            createValidAddressList()
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short login");
    }

    @Test
    @DisplayName("Should fail validation when password is too short")
    void shouldFailValidationWhenPasswordIsTooShort() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_USER_EMAIL,
            TestConstants.VALID_USER_LOGIN,
            "123",
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            createValidAddressList()
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short password");
    }

    @Test
    @DisplayName("Should allow valid names with accents and spaces")
    void shouldAllowValidNamesWithAccentsAndSpaces() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
            "João da Silva Ção",
            TestConstants.VALID_USER_EMAIL,
            TestConstants.VALID_USER_LOGIN,
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            createValidAddressList()
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for valid name with accents");
    }

    @Test
    @DisplayName("Should fail validation when required nested objects are null")
    void shouldFailValidationWhenRequiredNestedObjectsAreNull() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_USER_EMAIL,
            TestConstants.VALID_USER_LOGIN,
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            null,
            null
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null nested objects");
    }

    private UserRequestDto createValidUserRequestDto() {
        return new UserRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_USER_EMAIL,
            TestConstants.VALID_USER_LOGIN,
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            createValidAddressList()
        );
    }


    private List<AddressDto> createValidAddressList() {
        return List.of(new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        ));
    }
}

