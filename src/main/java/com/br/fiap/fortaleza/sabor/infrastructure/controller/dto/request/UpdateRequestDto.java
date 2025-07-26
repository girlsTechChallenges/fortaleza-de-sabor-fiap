package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;

import java.util.List;

public record UpdateRequestDto(
        @NotNull(message = "This field cannot be empty") @NotBlank @Size(min = 2, max = 50) String nome,
        @NotNull @NotBlank @Email String email,
        @NotNull @NotBlank @Size(min = 8, max = 100) String senha,
        @NotNull TypeUser tipo,
        @NotNull @Size(min = 1) @Valid List<AddressDto> address
) {}
