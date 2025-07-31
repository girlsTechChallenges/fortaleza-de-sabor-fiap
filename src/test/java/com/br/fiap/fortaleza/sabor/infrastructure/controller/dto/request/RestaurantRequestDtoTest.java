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

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("RestaurantRequestDto Validation Tests")
class RestaurantRequestDtoTest {

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
        RestaurantRequestDto restaurantRequestDto = createValidRestaurantRequestDto();

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when name is null")
    void shouldFailValidationWhenNameIsNull() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            null,
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null name");
    }

    @Test
    @DisplayName("Should fail validation when name is blank")
    void shouldFailValidationWhenNameIsBlank() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            "",
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for blank name");
    }

    @Test
    @DisplayName("Should fail validation when name is too short")
    void shouldFailValidationWhenNameIsTooShort() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            "A",
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short name");
    }

    @Test
    @DisplayName("Should fail validation when kitchen type is null")
    void shouldFailValidationWhenKitchenTypeIsNull() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            null,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null kitchen type");
    }

    @Test
    @DisplayName("Should fail validation when kitchen type is too short")
    void shouldFailValidationWhenKitchenTypeIsTooShort() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            "AB",
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for short kitchen type");
    }

    @Test
    @DisplayName("Should fail validation when email is null")
    void shouldFailValidationWhenEmailIsNull() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            null,
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null email");
    }

    @Test
    @DisplayName("Should fail validation when email format is invalid")
    void shouldFailValidationWhenEmailFormatIsInvalid() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            "email-invalido",
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for invalid email format");
    }

    @Test
    @DisplayName("Should fail validation when address list is empty")
    void shouldFailValidationWhenAddressListIsEmpty() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            TestConstants.VALID_RESTAURANT_EMAIL,
            Collections.emptyList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for empty address list");
    }

    @Test
    @DisplayName("Should fail validation when business hours list is empty")
    void shouldFailValidationWhenBusinessHoursListIsEmpty() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            Collections.emptyList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for empty business hours list");
    }

    @Test
    @DisplayName("Should fail validation when address list is null")
    void shouldFailValidationWhenAddressListIsNull() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            TestConstants.VALID_RESTAURANT_EMAIL,
            null,
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null address list");
    }

    @Test
    @DisplayName("Should fail validation when business hours list is null")
    void shouldFailValidationWhenBusinessHoursListIsNull() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            null
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null business hours list");
    }

    @Test
    @DisplayName("Should allow maximum valid lengths")
    void shouldAllowMaximumValidLengths() {
        // Arrange
        String maxName = "A".repeat(100); // Max length for name
        String maxKitchenType = "B".repeat(50); // Max length for kitchen type
        
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(
            maxName,
            maxKitchenType,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for maximum valid lengths");
    }

    @Test
    @DisplayName("Should reject names and kitchen types exceeding maximum length")
    void shouldRejectNamesAndKitchenTypesExceedingMaximumLength() {
        // Arrange - Name too long
        String tooLongName = "A".repeat(101);
        RestaurantRequestDto restaurantRequestDto1 = new RestaurantRequestDto(
            tooLongName,
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations1 = validator.validate(restaurantRequestDto1);

        // Assert
        assertFalse(violations1.isEmpty(), "Should have validation violations for name too long");

        // Arrange - Kitchen type too long
        String tooLongKitchenType = "B".repeat(51);
        RestaurantRequestDto restaurantRequestDto2 = new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            tooLongKitchenType,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            createValidBusinessHoursList()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations2 = validator.validate(restaurantRequestDto2);

        // Assert
        assertFalse(violations2.isEmpty(), "Should have validation violations for kitchen type too long");
    }

    private RestaurantRequestDto createValidRestaurantRequestDto() {
        return new RestaurantRequestDto(
            TestConstants.VALID_RESTAURANT_NAME,
            TestConstants.VALID_RESTAURANT_KITCHEN_TYPE,
            TestConstants.VALID_RESTAURANT_EMAIL,
            createValidAddressList(),
            createValidBusinessHoursList()
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

    private List<BusinessHoursDto> createValidBusinessHoursList() {
        return List.of(new BusinessHoursDto(
            TestConstants.VALID_DAY_OF_WEEK,
            LocalTime.of(9, 0),
            LocalTime.of(18, 0),
            "Funcionamento normal"
        ));
    }
}
