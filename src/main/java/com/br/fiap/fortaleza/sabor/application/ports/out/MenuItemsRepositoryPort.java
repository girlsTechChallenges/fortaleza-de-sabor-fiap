package com.br.fiap.fortaleza.sabor.application.ports.out;

import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemsRepositoryPort {
    List<MenuItem> getAll();

    MenuItem save(MenuItem user);

    Optional<MenuItem> update(Long idItemMenu, MenuItem user);

    Optional<MenuItem> getById(Long idItemMenu);

    Optional<MenuItem> deleteById(Long idItemMenu);
}
