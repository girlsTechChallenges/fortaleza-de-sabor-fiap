package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TypeUserDto (

        @NotNull
        @NotEmpty
        String nome_tipo

){

}
