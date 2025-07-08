package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.Menu;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class CreateMenuUseCase {
    private final MenuRepository menuRepository;

    public CreateMenuUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public MenuItem save(MenuItem menu) {
        return menuRepository.save(menu);
    }
}
