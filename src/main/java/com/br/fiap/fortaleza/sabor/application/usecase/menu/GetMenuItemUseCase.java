package com.br.fiap.fortaleza.sabor.application.usecase.menu;


import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetMenuItemUseCase {
    private final MenuItemsRepository menuItemsRepository;

    public GetMenuItemUseCase(MenuItemsRepository menuItemsRepository) {
        this.menuItemsRepository = menuItemsRepository;
    }

    public List<MenuItem> getAll() {
        return menuItemsRepository.getAll();
    }

    public Optional<MenuItem> getById(Long idMenu) {
        return menuItemsRepository.getById(idMenu);
    }
}
