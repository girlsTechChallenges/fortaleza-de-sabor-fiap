package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

public record MenuItemResponseDto(
        String nome,
        String itemDescription,
        String itemPrice,
        Boolean availability,
        String itemImage
) {}
