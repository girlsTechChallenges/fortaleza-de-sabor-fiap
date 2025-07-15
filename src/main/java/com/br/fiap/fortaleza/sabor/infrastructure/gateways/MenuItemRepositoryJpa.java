package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.MenuAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.MenuNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.MenuItemRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.MenuItemsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return menuItemRepository.findAll().stream().map(mapper::toMenuDomain).toList();
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        menuItemRepository.findByName(menuItem.getNome())
                .ifPresent(existingMenuItem -> {
                    throw new MenuAlreadyRegisteredException(
                            "This item already exists."
                    );
                });

        MenuItemsEntity menuEntity = mapper.toMenuItemsEntity(menuItem);
        return mapper.toMenuItemDomain(menuItemRepository.save(menuEntity));
    }

    @Override
    public Optional<MenuItem> update(Long idItemCardapio, MenuItem menu) {
        MenuItemsEntity findMenu = menuItemRepository.findById(idItemCardapio)
                .orElseThrow(() -> new MenuNotFoundException("Menu" + idItemCardapio.toString() + "was not found"));

        if (menu != null) {
            findMenu.setNome(menu.getNome());
            findMenu.setItemDescription(menu.getItemDescription());
            findMenu.setAvailability(menu.getAvailability());
            findMenu.setItemPrice(menu.getItemPrice());
            findMenu.setItemImage(menu.getItemImage());
        }

        MenuItemsEntity actualization = menuItemRepository.save(findMenu);
        return Optional.ofNullable(mapper.toMenuDomain(actualization));
    }

    @Override
    public Optional<MenuItem> getById(Long idItemCardapio) {
        var findMenu = menuItemRepository.findById(idItemCardapio)
                .orElseThrow(() -> new MenuNotFoundException("Menu" + idItemCardapio.toString() + "was not found"));

        return Optional.ofNullable(mapper.toMenuDomain(findMenu));
    }

    @Override
    public Optional<MenuItem> deleteById(Long idItemCardapio) {
        MenuItemsEntity findMenu = menuItemRepository.findById(idItemCardapio)
                .orElseThrow(() -> new MenuNotFoundException("Menu" + idItemCardapio.toString() + "was not found"));

        Optional<MenuItemsEntity> menu = menuItemRepository.findById(idItemCardapio);
        menuItemRepository.deleteById(idItemCardapio);
        return menu.map(mapper::toMenuDomain);
    }
}
