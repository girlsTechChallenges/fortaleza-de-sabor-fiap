package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RestaurantRequestDto (
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "Kitchen type is required")
        @Size(min = 3, max = 50, message = "Kitchen type must be between 3 and 50 characters")
        String kitchenType,


        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotEmpty(message = "At least one address must be provided")
        @Valid
        List<AddressDto> address,

        @NotEmpty(message = "Business hours must be provided")
        @Valid
        List<BusinessHoursDto> businessHours) {
}
