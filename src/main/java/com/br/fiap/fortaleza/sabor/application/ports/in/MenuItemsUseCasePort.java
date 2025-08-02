package com.br.fiap.fortaleza.sabor.application.ports.in;

import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemsUseCasePort {

    List<MenuItem> getAll();

    MenuItem save(MenuItem menu);

    Optional<MenuItem> update(Long idItemMenu, MenuItem menu);

    Optional<MenuItem> getById(Long idItemMenu);

    Optional<MenuItem> deleteById(Long idItemMenu);

}
