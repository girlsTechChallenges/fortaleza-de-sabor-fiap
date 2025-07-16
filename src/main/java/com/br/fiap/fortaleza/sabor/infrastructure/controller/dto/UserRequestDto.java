package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record UserRequestDto(
        @NotNull
        @NotBlank
        @Size(min = 2, max = 50)
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "The name must contain only letters")
        String nome,

        @NotNull
        @NotBlank
        @Email
        String email,

        @NotNull
        @NotBlank
        @Size(min = 5, max = 20)
        String login,

        @NotNull
        @NotBlank
        @Size(min = 8, max = 100) String senha,
        LocalDate dataAlteracao,

        @NotNull
        TypeUser tipo,

        @NotNull
        @Size(min = 1) List<AddressDto> address
) {}

