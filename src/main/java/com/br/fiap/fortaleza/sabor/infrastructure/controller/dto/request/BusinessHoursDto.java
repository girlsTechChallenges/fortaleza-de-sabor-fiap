package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record BusinessHoursDto (
        @Schema(description = "Dia da semana", example = "MONDAY",
                allowableValues = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" })
        DayOfWeek dayOfWeek,
        @Schema(type = "string", pattern = "HH:mm:ss", example = "08:00:00")
        LocalTime openingTime,
        @Schema(type = "string", pattern = "HH:mm:ss", example = "08:00:00")
        LocalTime closingTime, String observations) {
}