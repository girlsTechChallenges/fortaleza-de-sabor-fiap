package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

public record MenuItemResponseDto(
        String name,
        String itemDescription,
        String itemPrice,
        Boolean availability,
        String itemImage
) {}
