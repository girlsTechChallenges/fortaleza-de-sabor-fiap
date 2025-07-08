package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto;

import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record MenuDto(
        @NotBlank List<MenuItem> lista_items_cardapio
) {}
