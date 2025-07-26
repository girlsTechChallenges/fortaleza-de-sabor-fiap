package com.br.fiap.fortaleza.sabor.application.usecase.restaurant;

import com.br.fiap.fortaleza.sabor.application.gateways.RestaurantsRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteRestaurantUseCase {

    private final RestaurantsRepository restaurantRepository;

    public DeleteRestaurantUseCase(RestaurantsRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void delete(Long idRestaurant) {
        restaurantRepository.deleteById(idRestaurant)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + idRestaurant));
    }
}
