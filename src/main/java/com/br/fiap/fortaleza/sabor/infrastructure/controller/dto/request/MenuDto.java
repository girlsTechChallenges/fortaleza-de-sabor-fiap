package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record MenuDto(
        @NotBlank List<MenuItem> menuItemsList
) {}
