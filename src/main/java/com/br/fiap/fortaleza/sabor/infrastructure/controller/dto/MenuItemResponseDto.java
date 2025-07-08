package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

public record MenuItemResponseDto(
        String nome,
        String descricao_item,
        String preco_item,
        Boolean disponibilidade,
        String imagem_item
) {}
