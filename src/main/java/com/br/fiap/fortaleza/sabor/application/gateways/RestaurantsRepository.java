package com.br.fiap.fortaleza.sabor.application.gateways;

import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;

public interface RestaurantsRepository {

    Restaurant create(Restaurant restaurant);

}
