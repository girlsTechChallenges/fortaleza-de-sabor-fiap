package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateMenuUseCase {
    private final MenuRepository menuRepository;

    public UpdateMenuUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Optional<MenuItem> update(Long idMenu, MenuItem menu) {
        return menuRepository.update(idMenu, menu);
    }
}
