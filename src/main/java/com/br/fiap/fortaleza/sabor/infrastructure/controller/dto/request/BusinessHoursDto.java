package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record BusinessHoursDto (
        @NotNull(message = "Day of the week is required")
        @Schema(description = "Day of the week", example = "MONDAY",
                allowableValues = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" })
        DayOfWeek dayOfWeek,

        @NotNull(message = "Opening time is required")
        @Schema(type = "string", pattern = "HH:mm:ss", example = "08:00:00")
        LocalTime openingTime,

        @NotNull(message = "Closing time is required")
        @Schema(type = "string", pattern = "HH:mm:ss", example = "08:00:00")
        LocalTime closingTime,

        @Size(max = 255, message = "Observations must be at most 255 characters")
        String observations) {
}