package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;

import java.util.List;

public record UserResponseDto(
        String nome,
        String login,
        String email,
        String tipo,
        List<AddressDto> address) {
}
