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
@DisplayName("UserCredentialsDto Validation Tests")
class UserCredentialsDtoTest {

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
        UserCredentialsDto credentialsDto = createValidUserCredentialsDto();

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when email is null")
    void shouldFailValidationWhenEmailIsNull() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(
            null,
            TestConstants.VALID_PASSWORD
        );

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null email");
    }

    @Test
    @DisplayName("Should fail validation when password is null")
    void shouldFailValidationWhenPasswordIsNull() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(
            TestConstants.VALID_EMAIL,
            null
        );

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null password");
    }

    @Test
    @DisplayName("Should fail validation when both email and password are null")
    void shouldFailValidationWhenBothEmailAndPasswordAreNull() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(null, null);

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 2, "Should have at least two violations for null email and password");
    }

    @Test
    @DisplayName("Should accept empty email")
    void shouldAcceptEmptyEmail() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(
            "",
            TestConstants.VALID_PASSWORD
        );

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for empty email");
    }

    @Test
    @DisplayName("Should accept empty password")
    void shouldAcceptEmptyPassword() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(
            TestConstants.VALID_EMAIL,
            ""
        );

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for empty password");
    }

    @Test
    @DisplayName("Should accept valid email formats")
    void shouldAcceptValidEmailFormats() {
        // Arrange - Test various valid email formats
        String[] validEmails = {
            "user@example.com",
            "test.email@domain.co.uk",
            "user+tag@example.org",
            "123@number-domain.com",
            "a@b.co"
        };

        for (String validEmail : validEmails) {
            UserCredentialsDto credentialsDto = new UserCredentialsDto(
                validEmail,
                TestConstants.VALID_PASSWORD
            );

            // Act
            Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

            // Assert
            assertTrue(violations.isEmpty(), 
                "Should have no validation violations for valid email: " + validEmail);
        }
    }

    @Test
    @DisplayName("Should accept various password formats")
    void shouldAcceptVariousPasswordFormats() {
        // Arrange - Test various password formats
        String[] passwords = {
            "simple",
            "Password123!",
            "very-long-password-with-many-characters-and-symbols@#$%",
            "12345678",
            "P@ssw0rd",
            "   spaces   ",
            "unicode-ção-password"
        };

        for (String password : passwords) {
            UserCredentialsDto credentialsDto = new UserCredentialsDto(
                TestConstants.VALID_EMAIL,
                password
            );

            // Act
            Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

            // Assert
            assertTrue(violations.isEmpty(), 
                "Should have no validation violations for password format");
        }
    }

    @Test
    @DisplayName("Should accept long credential values")
    void shouldAcceptLongCredentialValues() {
        // Arrange
        String longEmail = "very-long-email-address-that-might-be-used-in-some-systems@very-long-domain-name-example.com";
        String longPassword = "A".repeat(255);

        UserCredentialsDto credentialsDto = new UserCredentialsDto(
            longEmail,
            longPassword
        );

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for long values");
    }

    @Test
    @DisplayName("Should accept whitespace-only values")
    void shouldAcceptWhitespaceOnlyValues() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(
            "   ",
            "   "
        );

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for whitespace-only values");
    }

    @Test
    @DisplayName("Should accept special characters in credentials")
    void shouldAcceptSpecialCharactersInCredentials() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(
            "user+special@domain-with-dash.com",
            "P@ssw0rd!#$%&*()_+-=[]{}|;:,.<>?"
        );

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for special characters");
    }

    @Test
    @DisplayName("Should handle minimum valid inputs")
    void shouldHandleMinimumValidInputs() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(
            "a@b.c",
            "1"
        );

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for minimum valid inputs");
    }

    private UserCredentialsDto createValidUserCredentialsDto() {
        return new UserCredentialsDto(
            TestConstants.VALID_EMAIL,
            TestConstants.VALID_PASSWORD
        );
    }
}
