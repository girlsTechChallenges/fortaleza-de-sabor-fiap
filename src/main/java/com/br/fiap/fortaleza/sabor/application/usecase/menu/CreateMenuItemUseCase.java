package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class CreateMenuItemUseCase {
    private final MenuItemsRepository menuItemsRepository;

    public CreateMenuItemUseCase(MenuItemsRepository menuItemsRepository) {
        this.menuItemsRepository = menuItemsRepository;
    }

    public MenuItem save(MenuItem menu) {
        return menuItemsRepository.save(menu);
    }
}
