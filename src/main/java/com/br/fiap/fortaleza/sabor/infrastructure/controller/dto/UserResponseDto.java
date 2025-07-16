package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;

import java.util.List;

public record UserResponseDto(
        String nome,
        String login,
        String email,
        TypeUser tipo,
        List<AddressDto> address) {
}
