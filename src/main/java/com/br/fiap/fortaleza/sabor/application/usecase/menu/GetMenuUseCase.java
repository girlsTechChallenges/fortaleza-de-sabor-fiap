package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.Menu;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetMenuUseCase {
    private final MenuRepository menuRepository;

    public GetMenuUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuItem> getAll() {
        return menuRepository.getAll();
    }

    public Optional<MenuItem> getById(Long idMenu) {
        return menuRepository.getById(idMenu);
    }
}
