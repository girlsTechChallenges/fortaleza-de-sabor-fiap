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

@DisplayName("UserCredentialsDto Validation Tests")
class UserCredentialsDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should pass validation with valid credentials")
    void shouldPassValidationWithValidCredentials() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto("user@email.com", "password123");

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when email is null")
    void shouldFailValidationWhenEmailIsNull() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(null, "password123");

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("nulo");
    }

    @Test
    @DisplayName("Should fail validation when password is null")
    void shouldFailValidationWhenPasswordIsNull() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto("user@email.com", null);

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("nulo");
    }

    @Test
    @DisplayName("Should fail validation when both email and password are null")
    void shouldFailValidationWhenBothEmailAndPasswordAreNull() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto(null, null);

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("Should pass validation with empty strings")
    void shouldPassValidationWithEmptyStrings() {
        // Arrange
        UserCredentialsDto credentialsDto = new UserCredentialsDto("", "");

        // Act
        Set<ConstraintViolation<UserCredentialsDto>> violations = validator.validate(credentialsDto);

        // Assert
        assertThat(violations).isEmpty(); // @NotNull allows empty strings
    }

    @Test
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        UserCredentialsDto credentials1 = new UserCredentialsDto("user@email.com", "password123");
        UserCredentialsDto credentials2 = new UserCredentialsDto("user@email.com", "password123");
        UserCredentialsDto credentials3 = new UserCredentialsDto("other@email.com", "password123");

        // Act & Assert
        assertThat(credentials1.email()).isEqualTo("user@email.com");
        assertThat(credentials1.password()).isEqualTo("password123");
        
        assertThat(credentials1).isEqualTo(credentials2);
        assertThat(credentials1).isNotEqualTo(credentials3);
        assertThat(credentials1.hashCode()).isEqualTo(credentials2.hashCode());
        assertThat(credentials1.toString()).contains("user@email.com");
    }
}
