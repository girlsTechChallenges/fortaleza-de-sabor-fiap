package com.br.fiap.fortaleza.sabor.application.gateways;

import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;

import java.util.List;
import java.util.Optional;

// usar camel case idItemCardapio
// mudar para ingles
public interface MenuItemsRepository {
    List<MenuItem> getAll();

    MenuItem save(MenuItem user);

    Optional<MenuItem> update(Long idItemMenu, MenuItem user);

    Optional<MenuItem> getById(Long idItemMenu);

    Optional<MenuItem> deleteById(Long idItemMenu);
}
