package com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepositoryAdapter extends JpaRepository<RestaurantEntity, Long> {

    Optional<RestaurantEntity> findByName(String name);

}
