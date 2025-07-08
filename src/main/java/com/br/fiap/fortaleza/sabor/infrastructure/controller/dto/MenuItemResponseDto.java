package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

public record MenuItemResponseDto(
        String nome,
        String itemDescription,
        String itemPrice,
        Boolean availability,
        String itemImage
) {}
