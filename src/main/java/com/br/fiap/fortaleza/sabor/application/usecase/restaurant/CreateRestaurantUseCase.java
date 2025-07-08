package com.br.fiap.fortaleza.sabor.application.usecase.restaurant;

import com.br.fiap.fortaleza.sabor.application.gateways.RestaurantsRepository;
import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class CreateRestaurantUseCase {

    private final RestaurantsRepository restaurantRepository;

    public CreateRestaurantUseCase(RestaurantsRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.create(restaurant);
    }

}
