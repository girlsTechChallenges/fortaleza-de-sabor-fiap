package com.br.fiap.fortaleza.sabor.application.gateways;

import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;

import java.util.Optional;

public interface RestaurantsRepository {

    Restaurant create(Restaurant restaurant);

    Optional<Restaurant> update(Long idRestaurant, Restaurant restaurant);

}
