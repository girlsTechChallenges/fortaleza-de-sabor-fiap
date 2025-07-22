package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateMenuItemUseCase {
    private final MenuItemsRepository menuItemsRepository;

    public UpdateMenuItemUseCase(MenuItemsRepository menuItemsRepository) {
        this.menuItemsRepository = menuItemsRepository;
    }

    public Optional<MenuItem> update(Long idMenu, MenuItem menu) {
        return menuItemsRepository.update(idMenu, menu);
    }
}
