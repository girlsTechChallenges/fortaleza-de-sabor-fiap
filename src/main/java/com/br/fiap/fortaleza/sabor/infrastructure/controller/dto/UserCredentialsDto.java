package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import jakarta.validation.constraints.NotNull;

public record UserCredentialsDto(
        @NotNull String email,
        @NotNull String password) {
}
