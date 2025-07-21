package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OwnerRequestDto(
        @NotBlank(message = "Owner is required")
        String owner,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email) {
}
