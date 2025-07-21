package com.br.fiap.fortaleza.sabor.application.usecase.restaurant;

import com.br.fiap.fortaleza.sabor.application.gateways.RestaurantsRepository;
import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.RestaurantNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UpdateRestaurantUseCase {

    private final RestaurantsRepository restaurantRepository;

    public UpdateRestaurantUseCase(RestaurantsRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant update(Long idRestaurant, Restaurant restaurant) {
        return restaurantRepository.update(idRestaurant, restaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + idRestaurant));
    }

    public Restaurant updateOwner(Long idRestaurant, String ownerName, String email){
        return restaurantRepository.updateOwner(idRestaurant, ownerName, email)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + idRestaurant));
    }

}
