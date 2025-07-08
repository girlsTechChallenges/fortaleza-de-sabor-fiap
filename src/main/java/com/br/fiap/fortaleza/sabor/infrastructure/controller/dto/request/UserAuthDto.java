package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

public record UserAuthDto(String accessToken, Long expiresIn) {
}
