package com.br.fiap.fortaleza.sabor.application.gateways;

import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository {
    List<MenuItem> getAll();

    MenuItem save(MenuItem user);

    Optional<MenuItem> update(Long id_item_cardapio, MenuItem user);

    Optional<MenuItem> getById(Long id_item_cardapio);

    Optional<MenuItem> deleteById(Long id_item_cardapio);

}
