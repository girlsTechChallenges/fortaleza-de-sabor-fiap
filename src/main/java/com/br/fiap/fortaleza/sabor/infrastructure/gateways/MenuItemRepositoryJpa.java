package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.MenuItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemRepositoryJpa implements MenuItemsRepository{
    private static final Logger log = LoggerFactory.getLogger(MenuItemRepositoryJpa.class);
    private final MenuItemRepository menuItemRepository;
    private final MenuEntityMapper mapper;

    public MenuItemRepositoryJpa(MenuItemRepository menuItemRepository, MenuEntityMapper mapper) {
        this.menuItemRepository = menuItemRepository;
        this.mapper = mapper;
    }

    @Override
    public List<MenuItem> getAll() {
        return List.of();
    }

    @Override
    public MenuItem save(MenuItem user) {
        return null;
    }

    @Override
    public Optional<MenuItem> update(Long idItemCardapio, MenuItem user) {
        return Optional.empty();
    }

    @Override
    public Optional<MenuItem> getById(Long idItemCardapio) {
        return Optional.empty();
    }

    @Override
    public Optional<MenuItem> deleteById(Long idItemCardapio) {
        return Optional.empty();
    }
}
