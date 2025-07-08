package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetMenuItemUseCase {
    private final MenuItemRepository menuItemRepository;

    public GetMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAll() {
        return menuItemRepository.getAll();
    }

    public Optional<MenuItem> getById(Long idMenu) {
        return menuItemRepository.getById(idMenu);
    }
}
