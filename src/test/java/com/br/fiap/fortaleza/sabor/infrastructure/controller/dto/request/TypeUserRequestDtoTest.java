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
@DisplayName("TypeUserRequestDto Validation Tests")
class TypeUserRequestDtoTest {

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
        TypeUserRequestDto typeUserDto = createValidTypeUserRequestDto();

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when type is null")
    void shouldFailValidationWhenTypeIsNull() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto(null);

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null type");
    }

    @Test
    @DisplayName("Should fail validation when type is empty")
    void shouldFailValidationWhenTypeIsEmpty() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for empty type");
    }

    @Test
    @DisplayName("Should accept valid type values")
    void shouldAcceptValidTypeValues() {
        // Arrange - Test various valid type values
        String[] validTypes = {
            TestConstants.OWNER_TYPE_NAME,
            TestConstants.CLIENT_TYPE_NAME,
            "ADMIN",
            "MANAGER",
            "USER"
        };

        for (String validType : validTypes) {
            TypeUserRequestDto typeUserDto = new TypeUserRequestDto(validType);

            // Act
            Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

            // Assert
            assertTrue(violations.isEmpty(), 
                "Should have no validation violations for valid type: " + validType);
        }
    }

    @Test
    @DisplayName("Should accept type with whitespace")
    void shouldAcceptTypeWithWhitespace() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("  OWNER  ");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for type with whitespace");
    }

    @Test
    @DisplayName("Should accept lowercase type values")
    void shouldAcceptLowercaseTypeValues() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("owner");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for lowercase type");
    }

    @Test
    @DisplayName("Should accept mixed case type values")
    void shouldAcceptMixedCaseTypeValues() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("OwNeR");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for mixed case type");
    }

    @Test
    @DisplayName("Should accept type with numbers")
    void shouldAcceptTypeWithNumbers() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("OWNER1");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for type with numbers");
    }

    @Test
    @DisplayName("Should accept type with special characters")
    void shouldAcceptTypeWithSpecialCharacters() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("OWNER-TYPE");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for type with special characters");
    }

    @Test
    @DisplayName("Should accept long type values")
    void shouldAcceptLongTypeValues() {
        // Arrange
        String longType = "VERY_LONG_TYPE_NAME_THAT_MIGHT_BE_USED_IN_SOME_SYSTEMS";
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto(longType);

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for long type value");
    }

    @Test
    @DisplayName("Should accept single character type")
    void shouldAcceptSingleCharacterType() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("A");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for single character type");
    }

    @Test
    @DisplayName("Should accept type that is only whitespace")
    void shouldAcceptTypeOnlyWhitespace() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("   ");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for whitespace-only type");
    }

    @Test
    @DisplayName("Should accept type with underscores")
    void shouldAcceptTypeWithUnderscores() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("SUPER_ADMIN");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for type with underscores");
    }

    @Test
    @DisplayName("Should accept unicode characters in type")
    void shouldAcceptUnicodeCharactersInType() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("PROPRIETÁRIO");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for unicode characters");
    }

    private TypeUserRequestDto createValidTypeUserRequestDto() {
        return new TypeUserRequestDto(TestConstants.OWNER_TYPE_NAME);
    }
}
