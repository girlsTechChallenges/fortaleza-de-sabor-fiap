package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.MenuItemsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemRepositoryJpa {
    private static final Logger log = LoggerFactory.getLogger(MenuItemRepositoryJpa.class);
    private final MenuItemsRepository menuItemsRepository;
    private final MenuEntityMapper mapper;

    public MenuItemRepositoryJpa(MenuItemsRepository menuItemsRepository, MenuEntityMapper mapper) {
        this.menuItemsRepository = menuItemsRepository;
        this.mapper = mapper;
    }

    @Override
    public List<MenuItem> getAll() {
        return menuItemsRepository.findAll().stream().map(mapper::toUserDomain).toList();
    }

    @Override
    public MenuItem save(MenuItem user) {
        return null;
    }

    @Override
    public Optional<MenuItem> update(Long id_item_cardapio, MenuItem user) {
        return Optional.empty();
    }

    @Override
    public Optional<MenuItem> getById(Long id_item_cardapio) {
        return Optional.empty();
    }

    @Override
    public Optional<MenuItem> deleteById(Long id_item_cardapio) {
        return Optional.empty();
    }
}
