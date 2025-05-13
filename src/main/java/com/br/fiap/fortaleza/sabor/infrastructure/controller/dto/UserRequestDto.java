package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;

import java.time.LocalDate;
import java.util.List;

public record UserRequestDto(
        String nome,
        String email,
        String login,
        String senha,
        LocalDate dataAlteracao,
        TypeEnum tipo,
        List<AddressRequestDto> address) {
}
