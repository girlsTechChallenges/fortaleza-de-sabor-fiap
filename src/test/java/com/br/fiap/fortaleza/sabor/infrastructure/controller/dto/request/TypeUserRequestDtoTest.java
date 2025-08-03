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

@DisplayName("TypeUserRequestDto Validation Tests")
class TypeUserRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should pass validation with valid TypeUserRequestDto")
    void shouldPassValidationWithValidTypeUserRequestDto() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("ADMIN");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when type is null")
    void shouldFailValidationWhenTypeIsNull() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto(null);

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertThat(violations).hasSize(2); // @NotNull and @NotEmpty violations
        assertThat(violations.stream().anyMatch(v -> v.getMessage().contains("nulo"))).isTrue();
    }

    @Test
    @DisplayName("Should fail validation when type is empty")
    void shouldFailValidationWhenTypeIsEmpty() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("vazio");
    }

    @Test
    @DisplayName("Should pass validation with different valid user types")
    void shouldPassValidationWithDifferentValidUserTypes() {
        // Arrange & Act & Assert
        String[] validTypes = {"ADMIN", "USER", "CUSTOMER", "EMPLOYEE", "MODERATOR", "MANAGER"};
        
        for (String type : validTypes) {
            TypeUserRequestDto typeUserDto = new TypeUserRequestDto(type);
            Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);
            assertThat(violations).isEmpty();
        }
    }

    @Test
    @DisplayName("Should pass validation with lowercase user type")
    void shouldPassValidationWithLowercaseUserType() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("admin");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should pass validation with special characters in type")
    void shouldPassValidationWithSpecialCharactersInType() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("SUPER_ADMIN");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should pass validation with numeric characters in type")
    void shouldPassValidationWithNumericCharactersInType() {
        // Arrange
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto("LEVEL_1_USER");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should pass validation with whitespace trimmed type")
    void shouldPassValidationWithWhitespaceTrimmiedType() {
        // Arrange - Note: Validation doesn't trim, so whitespace will be preserved
        TypeUserRequestDto typeUserDto = new TypeUserRequestDto(" ADMIN ");

        // Act
        Set<ConstraintViolation<TypeUserRequestDto>> violations = validator.validate(typeUserDto);

        // Assert
        assertThat(violations).isEmpty();
        assertThat(typeUserDto.type()).isEqualTo(" ADMIN ");
    }

    @Test
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        TypeUserRequestDto typeUserDto1 = new TypeUserRequestDto("ADMIN");
        TypeUserRequestDto typeUserDto2 = new TypeUserRequestDto("ADMIN");
        TypeUserRequestDto typeUserDto3 = new TypeUserRequestDto("USER");

        // Act & Assert
        assertThat(typeUserDto1.type()).isEqualTo("ADMIN");
        assertThat(typeUserDto1).isEqualTo(typeUserDto2);
        assertThat(typeUserDto1).isNotEqualTo(typeUserDto3);
        assertThat(typeUserDto1.hashCode()).isEqualTo(typeUserDto2.hashCode());
        assertThat(typeUserDto1.toString()).contains("ADMIN");
    }
}
