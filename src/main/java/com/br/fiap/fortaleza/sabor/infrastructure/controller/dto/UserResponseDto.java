package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;

import java.util.List;

public record UserResponseDto(
        String nome,
        String login,
        String email,
        TypeEnum tipo,
        List<AddressDto> address) {
}
