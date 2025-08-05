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

@DisplayName("RestaurantUpdateDto Validation Tests")
class RestaurantUpdateDtoTest {

    private Validator validator;
    private List<AddressDto> validAddress;
    private List<BusinessHoursDto> validBusinessHours;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        validAddress = List.of(
            new AddressDto("Street das Flores", "Centro", "Apt 123", 123, "SP", "São Paulo", "01234567")
        );
        
        validBusinessHours = List.of(
            new BusinessHoursDto(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), null)
        );
    }

    @Test
    @DisplayName("Should pass validation with valid restaurant update")
    void shouldPassValidationWithValidRestaurantUpdate() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "Restaurant Atualizado",
            "Italiana",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when name is null")
    void shouldFailValidationWhenNameIsNull() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            null,
            "Italiana",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when name is blank")
    void shouldFailValidationWhenNameIsBlank() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "   ",
            "Italiana",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when name is too short")
    void shouldFailValidationWhenNameIsTooShort() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "A",
            "Italiana",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("between 2 and 100");
    }

    @Test
    @DisplayName("Should fail validation when kitchen type is null")
    void shouldFailValidationWhenKitchenTypeIsNull() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "Restaurant Teste",
            null,
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when kitchen type is too short")
    void shouldFailValidationWhenKitchenTypeIsTooShort() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "Restaurant Teste",
            "It",
            validAddress,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("between 3 and 50");
    }

    @Test
    @DisplayName("Should fail validation when address list is null")
    void shouldFailValidationWhenAddressListIsNull() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "Restaurant Teste",
            "Italiana",
            null,
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must be provided");
    }

    @Test
    @DisplayName("Should fail validation when address list is empty")
    void shouldFailValidationWhenAddressListIsEmpty() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "Restaurant Teste",
            "Italiana",
            List.of(),
            validBusinessHours
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must be provided");
    }

    @Test
    @DisplayName("Should fail validation when business hours list is null")
    void shouldFailValidationWhenBusinessHoursListIsNull() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "Restaurant Teste",
            "Italiana",
            validAddress,
            null
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must be provided");
    }

    @Test
    @DisplayName("Should fail validation when business hours list is empty")
    void shouldFailValidationWhenBusinessHoursListIsEmpty() {
        // Arrange
        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "Restaurant Teste",
            "Italiana",
            validAddress,
            List.of()
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must be provided");
    }

    @Test
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        RestaurantUpdateDto update1 = new RestaurantUpdateDto(
            "Restaurant Exemplo",
            "Mexicana",
            validAddress,
            validBusinessHours
        );
        
        RestaurantUpdateDto update2 = new RestaurantUpdateDto(
            "Restaurant Exemplo",
            "Mexicana",
            validAddress,
            validBusinessHours
        );

        // Act & Assert
        assertThat(update1.name()).isEqualTo("Restaurant Exemplo");
        assertThat(update1.kitchenType()).isEqualTo("Mexicana");
        assertThat(update1.address()).isEqualTo(validAddress);
        assertThat(update1.businessHours()).isEqualTo(validBusinessHours);
        
        assertThat(update1).isEqualTo(update2);
        assertThat(update1.hashCode()).isEqualTo(update2.hashCode());
        assertThat(update1.toString()).contains("Restaurant Exemplo");
    }

    @Test
    @DisplayName("Should pass validation with multiple addresses and business hours")
    void shouldPassValidationWithMultipleAddressesAndBusinessHours() {
        // Arrange
        List<AddressDto> multipleAddresses = List.of(
            new AddressDto("Street A", "Centro", "Apt 1", 100, "SP", "São Paulo", "01234567"),
            new AddressDto("Street B", "Vila", "Casa 2", 200, "RJ", "Rio de Janeiro", "20000000")
        );
        
        List<BusinessHoursDto> multipleHours = List.of(
            new BusinessHoursDto(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), null),
            new BusinessHoursDto(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(19, 0), "Horário especial")
        );

        RestaurantUpdateDto updateDto = new RestaurantUpdateDto(
            "Multi Restaurant",
            "Fusion",
            multipleAddresses,
            multipleHours
        );

        // Act
        Set<ConstraintViolation<RestaurantUpdateDto>> violations = validator.validate(updateDto);

        // Assert
        assertThat(violations).isEmpty();
        assertThat(updateDto.address()).hasSize(2);
        assertThat(updateDto.businessHours()).hasSize(2);
    }
}
