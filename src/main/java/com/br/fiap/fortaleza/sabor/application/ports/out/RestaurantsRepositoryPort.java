package com.br.fiap.fortaleza.sabor.application.ports.out;

import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantsRepositoryPort {

    Restaurant create(Restaurant restaurant);

    Optional<Restaurant> update(Long idRestaurant, Restaurant restaurant);

    Optional<Restaurant> getById(Long idRestaurant);

    Optional<Restaurant> deleteById(Long idRestaurant);

    List<Restaurant> getAll();

    Optional<Restaurant> updateOwner(Long idRestaurant, String ownerName, String email);
}
