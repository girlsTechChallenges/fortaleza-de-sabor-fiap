package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TypeUserRequestDto(
        @NotNull
        @NotEmpty
        String type) {
}
