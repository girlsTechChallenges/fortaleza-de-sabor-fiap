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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BusinessHoursDto Validation Tests")
class BusinessHoursDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should pass validation with valid business hours")
    void shouldPassValidationWithValidBusinessHours() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.MONDAY,
            LocalTime.of(9, 0),
            LocalTime.of(18, 0),
            "Funcionamento normal"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should pass validation with null observations")
    void shouldPassValidationWithNullObservations() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.TUESDAY,
            LocalTime.of(8, 30),
            LocalTime.of(17, 30),
            null
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when dayOfWeek is null")
    void shouldFailValidationWhenDayOfWeekIsNull() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            null,
            LocalTime.of(9, 0),
            LocalTime.of(18, 0),
            "Teste"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when openingTime is null")
    void shouldFailValidationWhenOpeningTimeIsNull() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.WEDNESDAY,
            null,
            LocalTime.of(18, 0),
            "Teste"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when closingTime is null")
    void shouldFailValidationWhenClosingTimeIsNull() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.THURSDAY,
            LocalTime.of(9, 0),
            null,
            "Teste"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("required");
    }

    @Test
    @DisplayName("Should fail validation when observations exceed max length")
    void shouldFailValidationWhenObservationsExceedMaxLength() {
        // Arrange
        String longObservations = "a".repeat(256); // 256 characters, exceeds max of 255
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.FRIDAY,
            LocalTime.of(9, 0),
            LocalTime.of(18, 0),
            longObservations
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("255");
    }

    @Test
    @DisplayName("Should pass validation with observations at max length")
    void shouldPassValidationWithObservationsAtMaxLength() {
        // Arrange
        String maxLengthObservations = "a".repeat(255); // Exactly 255 characters
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.SATURDAY,
            LocalTime.of(10, 0),
            LocalTime.of(16, 0),
            maxLengthObservations
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when multiple fields are null")
    void shouldFailValidationWhenMultipleFieldsAreNull() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(null, null, null, "Teste");

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertThat(violations).hasSize(3); // dayOfWeek, openingTime, closingTime
    }

    @Test
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        BusinessHoursDto hours1 = new BusinessHoursDto(
            DayOfWeek.SUNDAY,
            LocalTime.of(11, 0),
            LocalTime.of(15, 0),
            "Horário especial"
        );
        
        BusinessHoursDto hours2 = new BusinessHoursDto(
            DayOfWeek.SUNDAY,
            LocalTime.of(11, 0),
            LocalTime.of(15, 0),
            "Horário especial"
        );
        
        BusinessHoursDto hours3 = new BusinessHoursDto(
            DayOfWeek.MONDAY,
            LocalTime.of(9, 0),
            LocalTime.of(18, 0),
            "Normal"
        );

        // Act & Assert
        assertThat(hours1.dayOfWeek()).isEqualTo(DayOfWeek.SUNDAY);
        assertThat(hours1.openingTime()).isEqualTo(LocalTime.of(11, 0));
        assertThat(hours1.closingTime()).isEqualTo(LocalTime.of(15, 0));
        assertThat(hours1.observations()).isEqualTo("Horário especial");
        
        assertThat(hours1).isEqualTo(hours2);
        assertThat(hours1).isNotEqualTo(hours3);
        assertThat(hours1.hashCode()).isEqualTo(hours2.hashCode());
        assertThat(hours1.toString()).contains("SUNDAY");
        assertThat(hours1.toString()).contains("Horário especial");
    }

    @Test
    @DisplayName("Should handle all days of week")
    void shouldHandleAllDaysOfWeek() {
        // Test all days of the week
        for (DayOfWeek day : DayOfWeek.values()) {
            // Arrange
            BusinessHoursDto businessHoursDto = new BusinessHoursDto(
                day,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                "Teste para " + day
            );

            // Act
            Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

            // Assert
            assertThat(violations).isEmpty();
            assertThat(businessHoursDto.dayOfWeek()).isEqualTo(day);
        }
    }
}
