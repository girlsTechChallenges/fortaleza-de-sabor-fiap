package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserRequestDto Validation Tests")
class UserRequestDtoTest {

    private Validator validator;
    private AddressDto validAddress;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        validAddress = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "12345678"
        );
    }

    @Test
    @DisplayName("Should pass validation with valid UserRequestDto")
    void shouldPassValidationWithValidUserRequestDto() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "João Silva",
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when name is null")
    void shouldFailValidationWhenNomeIsNull() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                null,
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("Should fail validation when name is blank")
    void shouldFailValidationWhenNomeIsBlank() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "   ",
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("Should fail validation when name is too short")
    void shouldFailValidationWhenNomeIsTooShort() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "A",
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("Should fail validation when name is too long")
    void shouldFailValidationWhenNomeIsTooLong() {
        // Arrange
        String longName = "A".repeat(51);
        UserRequestDto userDto = new UserRequestDto(
                longName,
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("Should fail validation when name contains numbers")
    void shouldFailValidationWhenNomeContainsNumbers() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "João Silva123",
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
        assertThat(violations).anyMatch(v -> v.getMessage().contains("The name must contain only letters"));
    }

    @Test
    @DisplayName("Should fail validation when email is invalid")
    void shouldFailValidationWhenEmailIsInvalid() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "João Silva",
                "invalid-email",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    @DisplayName("Should fail validation when login is too short")
    void shouldFailValidationWhenLoginIsTooShort() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "João Silva",
                "joao.silva@test.com",
                "abc",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("login"));
    }

    @Test
    @DisplayName("Should fail validation when login is too long")
    void shouldFailValidationWhenLoginIsTooLong() {
        // Arrange
        String longLogin = "a".repeat(21);
        UserRequestDto userDto = new UserRequestDto(
                "João Silva",
                "joao.silva@test.com",
                longLogin,
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("login"));
    }

    @Test
    @DisplayName("Should fail validation when password is too short")
    void shouldFailValidationWhenSenhaIsTooShort() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "João Silva",
                "joao.silva@test.com",
                "joaosilva",
                "123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }

    @Test
    @DisplayName("Should fail validation when type is null")
    void shouldFailValidationWhenTipoIsNull() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "João Silva",
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                null,
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("type"));
    }

    @Test
    @DisplayName("Should fail validation when address list is empty")
    void shouldFailValidationWhenAddressListIsEmpty() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "João Silva",
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList()
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("address"));
    }

    @Test
    @DisplayName("Should accept valid names with accents and spaces")
    void shouldAcceptValidNamesWithAccentsAndSpaces() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "José María dos Santos",
                "jose.maria@test.com",
                "josemaria",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should accept null changeDate as it is optional")
    void shouldAcceptNullDataAlteracaoAsItIsOptional() {
        // Arrange
        UserRequestDto userDto = new UserRequestDto(
                "João Silva",
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                null,
                "CLIENTE",
                Arrays.asList(validAddress)
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        // Assert
        assertThat(violations).isEmpty();
    }
}
