package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("BusinessHoursDto Validation Tests")
class BusinessHoursDtoTest {

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
        BusinessHoursDto businessHoursDto = createValidBusinessHoursDto();

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when dayOfWeek is null")
    void shouldFailValidationWhenDayOfWeekIsNull() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            null,
            LocalTime.of(8, 0),
            LocalTime.of(18, 0),
            "Standard opening hours"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null dayOfWeek");
    }

    @Test
    @DisplayName("Should fail validation when openingTime is null")
    void shouldFailValidationWhenOpeningTimeIsNull() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.MONDAY,
            null,
            LocalTime.of(18, 0),
            "Standard opening hours"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null openingTime");
    }

    @Test
    @DisplayName("Should fail validation when closingTime is null")
    void shouldFailValidationWhenClosingTimeIsNull() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.MONDAY,
            LocalTime.of(8, 0),
            null,
            "Standard opening hours"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for null closingTime");
    }

    @Test
    @DisplayName("Should allow null observations")
    void shouldAllowNullObservations() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.TUESDAY,
            LocalTime.of(9, 0),
            LocalTime.of(17, 0),
            null
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations when observations is null");
    }

    @Test
    @DisplayName("Should allow empty observations")
    void shouldAllowEmptyObservations() {
        // Arrange
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.WEDNESDAY,
            LocalTime.of(10, 0),
            LocalTime.of(20, 0),
            ""
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations when observations is empty");
    }

    @Test
    @DisplayName("Should allow observations at maximum length")
    void shouldAllowObservationsAtMaximumLength() {
        // Arrange
        String maxObservations = "A".repeat(255);
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.THURSDAY,
            LocalTime.of(7, 30),
            LocalTime.of(19, 30),
            maxObservations
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for maximum length observations");
    }

    @Test
    @DisplayName("Should fail validation when observations exceed maximum length")
    void shouldFailValidationWhenObservationsExceedMaximumLength() {
        // Arrange
        String tooLongObservations = "A".repeat(256);
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
            DayOfWeek.FRIDAY,
            LocalTime.of(6, 0),
            LocalTime.of(22, 0),
            tooLongObservations
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

        // Assert
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertTrue(violations.size() >= 1, "Should have at least one violation for observations too long");
    }

    @Test
    @DisplayName("Should accept all valid days of week")
    void shouldAcceptAllValidDaysOfWeek() {
        // Arrange & Act & Assert
        for (DayOfWeek day : DayOfWeek.values()) {
            BusinessHoursDto businessHoursDto = new BusinessHoursDto(
                day,
                LocalTime.of(8, 0),
                LocalTime.of(18, 0),
                "Open on " + day.name()
            );

            Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

            assertTrue(violations.isEmpty(), 
                "Should have no validation violations for day: " + day.name());
        }
    }

    @Test
    @DisplayName("Should accept various valid time combinations")
    void shouldAcceptVariousValidTimeCombinations() {
        // Arrange & Act & Assert
        LocalTime[][] timeCombs = {
            {LocalTime.of(0, 0), LocalTime.of(23, 59)},    // Full day
            {LocalTime.of(6, 0), LocalTime.of(14, 0)},     // Morning shift
            {LocalTime.of(14, 0), LocalTime.of(22, 0)},    // Evening shift
            {LocalTime.of(9, 30), LocalTime.of(17, 45)},   // Standard business hours with minutes
            {LocalTime.MIN, LocalTime.MAX}                  // Extreme values
        };

        for (LocalTime[] times : timeCombs) {
            BusinessHoursDto businessHoursDto = new BusinessHoursDto(
                DayOfWeek.SATURDAY,
                times[0],
                times[1],
                "Test hours"
            );

            Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(businessHoursDto);

            assertTrue(violations.isEmpty(), 
                "Should have no validation violations for times: " + times[0] + " to " + times[1]);
        }
    }

    @Test
    @DisplayName("Should accept weekend operations")
    void shouldAcceptWeekendOperations() {
        // Arrange
        BusinessHoursDto weekendDto = new BusinessHoursDto(
            DayOfWeek.SUNDAY,
            LocalTime.of(11, 0),
            LocalTime.of(15, 0),
            "Weekend special hours"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(weekendDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for weekend operations");
    }

    @Test
    @DisplayName("Should handle midnight operations")
    void shouldHandleMidnightOperations() {
        // Arrange
        BusinessHoursDto midnightDto = new BusinessHoursDto(
            DayOfWeek.FRIDAY,
            LocalTime.of(18, 0),
            LocalTime.of(2, 0), // Note: This represents 2 AM next day
            "Late night operations"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(midnightDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for midnight operations");
    }

    @Test
    @DisplayName("Should handle 24-hour operations")
    void shouldHandle24HourOperations() {
        // Arrange
        BusinessHoursDto twentyFourHourDto = new BusinessHoursDto(
            DayOfWeek.MONDAY,
            LocalTime.of(0, 0),
            LocalTime.of(23, 59),
            "24-hour service"
        );

        // Act
        Set<ConstraintViolation<BusinessHoursDto>> violations = validator.validate(twentyFourHourDto);

        // Assert
        assertTrue(violations.isEmpty(), "Should have no validation violations for 24-hour operations");
    }

    private BusinessHoursDto createValidBusinessHoursDto() {
        return new BusinessHoursDto(
            DayOfWeek.MONDAY,
            LocalTime.of(8, 0),
            LocalTime.of(18, 0),
            "Standard business hours"
        );
    }
}
