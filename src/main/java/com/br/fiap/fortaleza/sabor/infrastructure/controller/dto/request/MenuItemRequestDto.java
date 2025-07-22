package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MenuItemRequestDto(
        @NotNull
        @NotBlank
        @Size(min = 2, max = 50)
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "The name must contain only letters")
        String nome,

        @NotNull
        @NotBlank
        @Size(min = 2, max = 250)
        String itemDescription,

        @NotNull
        @NotBlank
        @Pattern(
                regexp = "^\\d{1,8}(\\.\\d{2})?$",
                message = "The price must be a valid number with up to 8 digits and optionally 2 decimal places (e.g., 12.34)"
        )
        String itemPrice,

        @NotNull
        Boolean availability,

        @NotNull
        String itemImage
) {}
