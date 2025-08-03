package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.ports.in.MenuItemsUseCasePort;
import com.br.fiap.fortaleza.sabor.application.ports.out.MenuItemsRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MenuItemUseCase implements MenuItemsUseCasePort {

    private final MenuItemsRepositoryPort menuItemsRepositoryPort;

    public MenuItemUseCase(MenuItemsRepositoryPort menuItemsRepositoryPort) {
        this.menuItemsRepositoryPort = menuItemsRepositoryPort;
    }

    @Override
    public List<MenuItem> getAll() {
        return menuItemsRepositoryPort.getAll();
    }

    @Override
    public MenuItem save(MenuItem menu) {
        return menuItemsRepositoryPort.save(menu);
    }

    @Override
    public Optional<MenuItem> update(Long idItemMenu, MenuItem menu) {
        return menuItemsRepositoryPort.update(idItemMenu, menu);
    }

    @Override
    public Optional<MenuItem> getById(Long idItemMenu) {
        return menuItemsRepositoryPort.getById(idItemMenu);
    }

    @Override
    public Optional<MenuItem> deleteById(Long idItemMenu) {
        return menuItemsRepositoryPort.deleteById(idItemMenu);
    }
}
