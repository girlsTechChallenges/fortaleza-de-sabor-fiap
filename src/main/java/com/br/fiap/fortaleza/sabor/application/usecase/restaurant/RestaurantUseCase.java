package com.br.fiap.fortaleza.sabor.application.usecase.restaurant;

import com.br.fiap.fortaleza.sabor.application.ports.in.RestaurantUseCasePort;
import com.br.fiap.fortaleza.sabor.application.ports.out.RestaurantsRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.RestaurantNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.br.fiap.fortaleza.sabor.infrastructure.common.MessageConstants.RESTAURANT_NOT_FOUND;

@Component
public class RestaurantUseCase implements RestaurantUseCasePort {

    private final RestaurantsRepositoryPort restaurantRepository;

    public RestaurantUseCase(RestaurantsRepositoryPort restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.create(restaurant);
    }

    @Override
    public Optional<Restaurant> update(Long idRestaurant, Restaurant restaurant) {
        return Optional.ofNullable(restaurantRepository.update(idRestaurant, restaurant)
                .orElseThrow(() -> new RestaurantNotFoundException(RESTAURANT_NOT_FOUND + idRestaurant)));
    }

    @Override
    public Optional<Restaurant> getById(Long idRestaurant) {
        return restaurantRepository.getById(idRestaurant);
    }

    @Override
    public Optional<Restaurant> deleteById(Long idRestaurant) {
        return Optional.ofNullable(restaurantRepository.deleteById(idRestaurant)
                .orElseThrow(() -> new RuntimeException(RESTAURANT_NOT_FOUND + idRestaurant)));
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Override
    public Optional<Restaurant> updateOwner(Long idRestaurant, String ownerName, String email) {
        return Optional.ofNullable(restaurantRepository.updateOwner(idRestaurant, ownerName, email)
                .orElseThrow(() -> new RestaurantNotFoundException(RESTAURANT_NOT_FOUND + idRestaurant)));
    }
}
