package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuRepositoryJpa implements MenuRepository {
    private static final Logger log = LoggerFactory.getLogger(UserRepositoryJpa.class);
    private final MenuRepository menuRepository;
    private final MenuEntityMapper mapper;

    public MenuRepositoryJpa(MenuRepository menuRepository, MenuEntityMapper mapper) {
        this.menuRepository = menuRepository;
        this.mapper = mapper;
    }

    @Override
    public List<MenuItem> getAll() {
        return menuRepository.findAll().stream().map(mapper::toUserDomain).toList();;
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
