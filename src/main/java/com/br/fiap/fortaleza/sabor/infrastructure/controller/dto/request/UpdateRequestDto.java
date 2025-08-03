package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateRequestDto(
        @NotNull(message = "This field cannot be empty") @NotBlank @Size(min = 2, max = 50) String nome,
        @NotNull @NotBlank @Email String email,
        @NotNull @NotBlank @Size(min = 8, max = 100) String senha,
        @NotNull String tipo,
        @NotNull @Size(min = 1) @Valid List<AddressDto> address
) {}
