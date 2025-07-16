package com.br.fiap.fortaleza.sabor.application.usecase.restaurant;

import com.br.fiap.fortaleza.sabor.application.gateways.RestaurantsRepository;
import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetRestaurantUseCase {

    private final RestaurantsRepository restaurantRepository;

    public GetRestaurantUseCase(RestaurantsRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Optional<Restaurant> getById(Long idRestaurant) {
        return restaurantRepository.getById(idRestaurant);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }
}
