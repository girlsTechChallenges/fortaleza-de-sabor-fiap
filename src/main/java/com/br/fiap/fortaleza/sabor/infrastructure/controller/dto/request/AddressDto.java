package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.constraints.*;

public record AddressDto(
        @NotBlank @Size(min = 2, max = 100) String street,
        @NotBlank @Size(min = 2, max = 100) String district,
        @Size(max = 100) String complement,
        @NotNull @Positive int number,
        @NotBlank @Size(min = 2, max = 50) String state,
        @NotBlank @Size(min = 2, max = 50) String city,
        @NotNull
        @Pattern(regexp = "^\\d{8}$", message = "The zip code must contain exactly 8 digits")
        String postCode
) {}

