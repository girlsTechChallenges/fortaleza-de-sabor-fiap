package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteMenuItemUseCase {
    private final MenuItemsRepository menuItemsRepository;

    public DeleteMenuItemUseCase(MenuItemsRepository menuItemsRepository) {
        this.menuItemsRepository = menuItemsRepository;
    }

    public Optional<MenuItem> delete(Long idMenu) {
        return menuItemsRepository.deleteById(idMenu);
    }
}
