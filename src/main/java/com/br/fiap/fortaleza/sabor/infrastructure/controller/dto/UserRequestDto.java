package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record UserRequestDto(
        @NotNull(message = "field cannot be blank") @NotBlank @Size(min = 2, max = 50) String nome,
        @NotNull @NotBlank @Email String email,
        @NotNull @NotBlank @Size(min = 5, max = 20) String login,
        @NotNull @NotBlank @Size(min = 8, max = 100) String senha,
        @NotNull LocalDate dataAlteracao,
        @NotNull TypeEnum tipo,
        @NotNull @Size(min = 1) List<AddressDto> address
) {}

