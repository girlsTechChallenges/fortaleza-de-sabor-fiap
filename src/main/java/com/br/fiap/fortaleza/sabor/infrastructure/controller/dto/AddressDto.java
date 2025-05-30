package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import jakarta.validation.constraints.*;

public record AddressDto(
        @NotBlank @Size(min = 2, max = 100) String rua,
        @NotBlank @Size(min = 2, max = 100) String bairro,
        @Size(max = 100) String complemento,
        @NotNull @Positive int numero,
        @NotBlank @Size(min = 2, max = 50) String estado,
        @NotBlank @Size(min = 2, max = 50) String cidade,
        @NotNull
        @Pattern(regexp = "^\\d{8}$", message = "The zip code must contain exactly 8 digits")
        String cep
) {}

