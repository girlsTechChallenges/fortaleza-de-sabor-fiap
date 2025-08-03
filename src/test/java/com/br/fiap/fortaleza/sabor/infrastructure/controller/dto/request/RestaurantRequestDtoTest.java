package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RestaurantRequestDto Validation Tests")
class RestaurantRequestDtoTest {

    private Validator validator;
    private List<AddressDto> validAddress;
    private List<BusinessHoursDto> validBusinessHours;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        validAddress = List.of(
            new AddressDto("Rua das Flores", "Centro", "Apt 123", 123, "SP", "São Paulo", "01234567")
        );
        
        validBusinessHours = List.of(
            new BusinessHoursDto(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), null)
        );
    }

    @Test
    @DisplayName("Should pass validation with valid restaurant request")
    void shouldPassValidationWithValidRestaurantRequest() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "Restaurante Exemplo",
            "Italiana",
            "restaurante@email.com",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when name is null")
    void shouldFailValidationWhenNameIsNull() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            null,
            "Italiana",
            "restaurante@email.com",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when name is blank")
    void shouldFailValidationWhenNameIsBlank() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "   ",
            "Italiana",
            "restaurante@email.com",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when name is too short")
    void shouldFailValidationWhenNameIsTooShort() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "A",
            "Italiana",
            "restaurante@email.com",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("between 2 and 100");
    }

    @Test
    @DisplayName("Should fail validation when kitchen type is null")
    void shouldFailValidationWhenKitchenTypeIsNull() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "Restaurante Exemplo",
            null,
            "restaurante@email.com",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when kitchen type is too short")
    void shouldFailValidationWhenKitchenTypeIsTooShort() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "Restaurante Exemplo",
            "It",
            "restaurante@email.com",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("between 3 and 50");
    }

    @Test
    @DisplayName("Should fail validation when email is null")
    void shouldFailValidationWhenEmailIsNull() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "Restaurante Exemplo",
            "Italiana",
            null,
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when email format is invalid")
    void shouldFailValidationWhenEmailFormatIsInvalid() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "Restaurante Exemplo",
            "Italiana",
            "email-invalido",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("email");
    }

    @Test
    @DisplayName("Should fail validation when address list is empty")
    void shouldFailValidationWhenAddressListIsEmpty() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "Restaurante Exemplo",
            "Italiana",
            "restaurante@email.com",
            List.of(),
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must be provided");
    }

    @Test
    @DisplayName("Should fail validation when business hours list is empty")
    void shouldFailValidationWhenBusinessHoursListIsEmpty() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "Restaurante Exemplo",
            "Italiana",
            "restaurante@email.com",
            validAddress,
            List.of()
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must be provided");
    }

    @Test
    @DisplayName("Should fail validation when address list is null")
    void shouldFailValidationWhenAddressListIsNull() {
        // Arrange
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(
            "Restaurante Exemplo",
            "Italiana",
            "restaurante@email.com",
            null,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must be provided");
    }

    @Test  
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        RestaurantRequestDto restaurant1 = new RestaurantRequestDto(
            "Restaurante Exemplo",
            "Italiana",
            "restaurante@email.com",
            validAddress,
            validBusinessHours
        );
        
        RestaurantRequestDto restaurant2 = new RestaurantRequestDto(
            "Restaurante Exemplo",
            "Italiana",
            "restaurante@email.com",
            validAddress,
            validBusinessHours
        );

        // Act & Assert
        assertThat(restaurant1.name()).isEqualTo("Restaurante Exemplo");
        assertThat(restaurant1.kitchenType()).isEqualTo("Italiana");
        assertThat(restaurant1.email()).isEqualTo("restaurante@email.com");
        assertThat(restaurant1.address()).isEqualTo(validAddress);
        assertThat(restaurant1.businessHours()).isEqualTo(validBusinessHours);
        
        assertThat(restaurant1).isEqualTo(restaurant2);
        assertThat(restaurant1.hashCode()).isEqualTo(restaurant2.hashCode());
        assertThat(restaurant1.toString()).contains("Restaurante Exemplo");
    }
}
