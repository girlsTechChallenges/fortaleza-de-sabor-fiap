package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

public record MenuResponseDto(
        String nome,
        String descricao_item,
        String preco_item,
        boolean disponibilidade,
        String imagem_item
) {}
