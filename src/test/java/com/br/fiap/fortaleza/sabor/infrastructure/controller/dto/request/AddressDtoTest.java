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
@DisplayName("AddressDto Validation Tests")
class AddressDtoTest {

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
        AddressDto addressDto = createValidAddressDto();

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when rua is null")
    void shouldFailValidationWhenRuaIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            null,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null rua");
    }

    @Test
    @DisplayName("Should fail validation when rua is blank")
    void shouldFailValidationWhenRuaIsBlank() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            "",
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank rua");
    }

    @Test
    @DisplayName("Should fail validation when rua is too short")
    void shouldFailValidationWhenRuaIsTooShort() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            "A",
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short rua");
    }

    @Test
    @DisplayName("Should fail validation when bairro is null")
    void shouldFailValidationWhenBairroIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            TestConstants.VALID_STREET,
            null,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null bairro");
    }

    @Test
    @DisplayName("Should fail validation when numero is negative")
    void shouldFailValidationWhenNumeroIsNegativeAsSecondTest() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            -5,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for negative numero");
    }

    @Test
    @DisplayName("Should fail validation when numero is negative")
    void shouldFailValidationWhenNumeroIsNegative() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            -1,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for negative numero");
    }

    @Test
    @DisplayName("Should fail validation when numero is zero")
    void shouldFailValidationWhenNumeroIsZero() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            0,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for zero numero");
    }

    @Test
    @DisplayName("Should fail validation when estado is null")
    void shouldFailValidationWhenEstadoIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            null,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null estado");
    }

    @Test
    @DisplayName("Should fail validation when cidade is null")
    void shouldFailValidationWhenCidadeIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            null,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null cidade");
    }

    @Test
    @DisplayName("Should fail validation when cep is null")
    void shouldFailValidationWhenCepIsNull() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            null
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null cep");
    }

    @Test
    @DisplayName("Should fail validation when cep format is invalid")
    void shouldFailValidationWhenCepFormatIsInvalid() {
        // Arrange - Test various invalid CEP formats
        String[] invalidCeps = {"1234567", "123456789", "12345-678", "abcdefgh", "12345abc"};

        for (String invalidCep : invalidCeps) {
            AddressDto addressDto = new AddressDto(
                TestConstants.VALID_STREET,
                TestConstants.VALID_NEIGHBORHOOD,
                TestConstants.VALID_COMPLEMENT,
                TestConstants.VALID_NUMBER,
                TestConstants.VALID_STATE,
                TestConstants.VALID_CITY,
                invalidCep
            );

            // Act
            Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

            // Assert
            assertFalse(violations.isEmpty(), 
                "Should have validation violations for invalid CEP format: " + invalidCep);
        }
    }

    @Test
    @DisplayName("Should allow null complemento")
    void shouldAllowNullComplemento() {
        // Arrange
        AddressDto addressDto = new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            null,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations when complemento is null");
    }

    @Test
    @DisplayName("Should allow maximum valid lengths")
    void shouldAllowMaximumValidLengths() {
        // Arrange
        String maxString100 = "A".repeat(100);
        String maxString50 = "B".repeat(50);

        AddressDto addressDto = new AddressDto(
            maxString100, // rua max 100
            maxString100, // bairro max 100
            maxString100, // complemento max 100
            TestConstants.VALID_NUMBER,
            maxString50,  // estado max 50
            maxString50,  // cidade max 50
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for maximum valid lengths");
    }

    @Test
    @DisplayName("Should reject fields exceeding maximum length")
    void shouldRejectFieldsExceedingMaximumLength() {
        // Arrange - Rua too long
        String tooLong101 = "A".repeat(101);
        AddressDto addressDto1 = new AddressDto(
            tooLong101,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations1 = validator.validate(addressDto1);

        // Assert
        assertFalse(violations1.isEmpty(), "Should have validation violations for rua too long");

        // Arrange - Estado too long
        String tooLong51 = "B".repeat(51);
        AddressDto addressDto2 = new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            tooLong51,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );

        // Act
        Set<ConstraintViolation<AddressDto>> violations2 = validator.validate(addressDto2);

        // Assert
        assertFalse(violations2.isEmpty(), "Should have validation violations for estado too long");
    }

    @Test
    @DisplayName("Should accept valid CEP formats")
    void shouldAcceptValidCepFormats() {
        // Arrange - Test valid 8-digit CEPs
        String[] validCeps = {"01234567", "12345678", "99999999", "00000001"};

        for (String validCep : validCeps) {
            AddressDto addressDto = new AddressDto(
                TestConstants.VALID_STREET,
                TestConstants.VALID_NEIGHBORHOOD,
                TestConstants.VALID_COMPLEMENT,
                TestConstants.VALID_NUMBER,
                TestConstants.VALID_STATE,
                TestConstants.VALID_CITY,
                validCep
            );

            // Act
            Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

            // Assert
            assertTrue(violations.isEmpty(), 
                "Should have no validation violations for valid CEP: " + validCep);
        }
    }

    private AddressDto createValidAddressDto() {
        return new AddressDto(
            TestConstants.VALID_STREET,
            TestConstants.VALID_NEIGHBORHOOD,
            TestConstants.VALID_COMPLEMENT,
            TestConstants.VALID_NUMBER,
            TestConstants.VALID_STATE,
            TestConstants.VALID_CITY,
            TestConstants.VALID_CEP
        );
    }
}
