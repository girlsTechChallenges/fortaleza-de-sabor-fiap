package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

public record AddressDto(
        String rua,
        String bairro,
        String complemento,
        int numero,
        String estado,
        String cidade,
        int cep
) {
}
