package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.Menu;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteMenuUseCase {
    private final MenuRepository menuRepository;

    public DeleteMenuUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Optional<MenuItem> delete(Long idMenu) {
        return menuRepository.deleteById(idMenu);
    }
}
