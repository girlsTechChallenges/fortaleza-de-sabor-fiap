package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;

import java.util.List;

public record UserResponseDto(
        String nome,
        String login,
        String email,
        TypeUserResponse tipo,
        List<AddressDto> address) {
}
