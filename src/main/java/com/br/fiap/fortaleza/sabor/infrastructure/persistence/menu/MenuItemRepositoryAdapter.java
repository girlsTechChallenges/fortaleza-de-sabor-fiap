package com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuItemRepositoryAdapter extends JpaRepository<MenuItemsEntity, Long> {
    Optional<MenuItemsEntity> findByName(String name);
}
