package com.br.fiap.fortaleza.sabor.infrastructure.adapter;

import com.br.fiap.fortaleza.sabor.application.ports.out.MenuItemsRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.MenuAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.MenuNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemRepositoryPortJpa implements MenuItemsRepositoryPort {

    Logger log = LoggerFactory.getLogger(MenuItemRepositoryPortJpa.class);

    private final MenuItemRepositoryAdapter menuItemRepositoryAdapter;
    private final MenuMapper mapper;

    public MenuItemRepositoryPortJpa(MenuItemRepositoryAdapter menuItemRepositoryAdapter, MenuMapper mapper) {
        this.menuItemRepositoryAdapter = menuItemRepositoryAdapter;
        this.mapper = mapper;
    }

    @Override
    public List<MenuItem> getAll() {
        return menuItemRepositoryAdapter.findAll().stream().map(mapper::toMenuItemDomain).toList();
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        menuItemRepositoryAdapter.findByNome(menuItem.getNome())
                .ifPresent(existingMenuItem -> {
                    throw new MenuAlreadyRegisteredException(
                            "This item already exists."
                    );
                });

        MenuItemsEntity menuEntity = mapper.toMenuItemsEntity(menuItem);
        return mapper.toMenuItemDomain(menuItemRepositoryAdapter.save(menuEntity));
    }

    @Override
    public Optional<MenuItem> update(Long idItemCardapio, MenuItem menu) {
        MenuItemsEntity findMenu = menuItemRepositoryAdapter.findById(idItemCardapio)
                .orElseThrow(() -> new MenuNotFoundException("Menu " + idItemCardapio + " was not found"));

        if (menu != null) {
            findMenu.setNome(menu.getNome());
            findMenu.setItemDescription(menu.getItemDescription());
            findMenu.setAvailability(menu.getAvailability());
            findMenu.setItemPrice(menu.getItemPrice());
            findMenu.setItemImage(menu.getItemImage());
        }

        MenuItemsEntity actualization = menuItemRepositoryAdapter.save(findMenu);
        return Optional.ofNullable(mapper.toMenuItemDomain(actualization));
    }

    @Override
    public Optional<MenuItem> getById(Long idItemCardapio) {
        var findMenu = menuItemRepositoryAdapter.findById(idItemCardapio)
                .orElseThrow(() -> new MenuNotFoundException("Menu" + idItemCardapio + "was not found"));

        return Optional.ofNullable(mapper.toMenuItemDomain(findMenu));
    }

    @Override
    public Optional<MenuItem> deleteById(Long idItemCardapio) {
        menuItemRepositoryAdapter.findById(idItemCardapio)
                .orElseThrow(() -> new MenuNotFoundException("Menu" + idItemCardapio + "was not found"));

        Optional<MenuItemsEntity> menu = menuItemRepositoryAdapter.findById(idItemCardapio);
        menuItemRepositoryAdapter.deleteById(idItemCardapio);
        return menu.map(mapper::toMenuItemDomain);
    }
}