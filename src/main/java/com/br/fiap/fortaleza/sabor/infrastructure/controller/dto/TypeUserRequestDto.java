package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TypeUserRequestDto(

        @NotNull
        @NotEmpty
        String nameType

){

}
