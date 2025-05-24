package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AddressDto(
        @NotBlank @Size(min = 2, max = 100) String rua,
        @NotBlank @Size(min = 2, max = 100) String bairro,
        @Size(max = 100) String complemento,
        @NotNull @Positive int numero,
        @NotBlank @Size(min = 2, max = 50) String estado,
        @NotBlank @Size(min = 2, max = 50) String cidade,
        @NotNull @Positive @Size(min = 8, max = 8) int cep
) {}

