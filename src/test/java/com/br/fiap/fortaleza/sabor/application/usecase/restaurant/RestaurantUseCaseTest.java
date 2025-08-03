package com.br.fiap.fortaleza.sabor.application.usecase.restaurant;

import com.br.fiap.fortaleza.sabor.application.ports.out.RestaurantsRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RestaurantUseCase Tests")
class RestaurantUseCaseTest {

    @Mock
    private RestaurantsRepositoryPort restaurantRepository;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private Restaurant restaurant;
    private Restaurant anotherRestaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(1L, "Restaurante do João", "Brasileira", "joao@test.com", "João Silva", null, null);
        anotherRestaurant = new Restaurant(2L, "Pizzaria da Maria", "Italiana", "maria@test.com", "Maria Santos", null, null);
    }

    @Test
    @DisplayName("Should create restaurant when create is called")
    void shouldCreateRestaurantWhenCreateIsCalled() {
        // Arrange
        when(restaurantRepository.create(any(Restaurant.class))).thenReturn(restaurant);

        // Act
        Restaurant createdRestaurant = restaurantUseCase.create(restaurant);

        // Assert
        assertThat(createdRestaurant).isNotNull();
        assertThat(createdRestaurant.getName()).isEqualTo("Restaurante do João");
        assertThat(createdRestaurant.getKitchenType()).isEqualTo("Brasileira");
        assertThat(createdRestaurant.getEmail()).isEqualTo("joao@test.com");
        assertThat(createdRestaurant.getOwner()).isEqualTo("João Silva");
        verify(restaurantRepository, times(1)).create(restaurant);
    }

    @Test
    @DisplayName("Should return all restaurants when getAll is called")
    void shouldReturnAllRestaurantsWhenGetAllIsCalled() {
        // Arrange
        List<Restaurant> expectedRestaurants = Arrays.asList(restaurant, anotherRestaurant);
        when(restaurantRepository.getAll()).thenReturn(expectedRestaurants);

        // Act
        List<Restaurant> actualRestaurants = restaurantUseCase.getAll();

        // Assert
        assertThat(actualRestaurants).isNotNull();
        assertThat(actualRestaurants).hasSize(2);
        assertThat(actualRestaurants).containsExactly(restaurant, anotherRestaurant);
        verify(restaurantRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return empty list when no restaurants exist")
    void shouldReturnEmptyListWhenNoRestaurantsExist() {
        // Arrange
        when(restaurantRepository.getAll()).thenReturn(Arrays.asList());

        // Act
        List<Restaurant> actualRestaurants = restaurantUseCase.getAll();

        // Assert
        assertThat(actualRestaurants).isNotNull();
        assertThat(actualRestaurants).isEmpty();
        verify(restaurantRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return restaurant when getById is called with valid id")
    void shouldReturnRestaurantWhenGetByIdIsCalledWithValidId() {
        // Arrange
        Long restaurantId = 1L;
        when(restaurantRepository.getById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Act
        Optional<Restaurant> result = restaurantUseCase.getById(restaurantId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(restaurant);
        verify(restaurantRepository, times(1)).getById(restaurantId);
    }

    @Test
    @DisplayName("Should return empty optional when getById is called with invalid id")
    void shouldReturnEmptyOptionalWhenGetByIdIsCalledWithInvalidId() {
        // Arrange
        Long invalidRestaurantId = 999L;
        when(restaurantRepository.getById(invalidRestaurantId)).thenReturn(Optional.empty());

        // Act
        Optional<Restaurant> result = restaurantUseCase.getById(invalidRestaurantId);

        // Assert
        assertThat(result).isEmpty();
        verify(restaurantRepository, times(1)).getById(invalidRestaurantId);
    }

    @Test
    @DisplayName("Should update restaurant when update is called with valid id")
    void shouldUpdateRestaurantWhenUpdateIsCalledWithValidId() {
        // Arrange
        Long restaurantId = 1L;
        Restaurant updatedRestaurant = new Restaurant(1L, "Updated Restaurant", "Fusion", "updated@test.com", "Updated Owner", null, null);

        when(restaurantRepository.update(restaurantId, restaurant)).thenReturn(Optional.of(updatedRestaurant));

        // Act
        Optional<Restaurant> result = restaurantUseCase.update(restaurantId, restaurant);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Updated Restaurant");
        assertThat(result.get().getKitchenType()).isEqualTo("Fusion");
        assertThat(result.get().getEmail()).isEqualTo("updated@test.com");
        assertThat(result.get().getOwner()).isEqualTo("Updated Owner");
        verify(restaurantRepository, times(1)).update(restaurantId, restaurant);
    }

    @Test
    @DisplayName("Should throw RestaurantNotFoundException when update is called with invalid id")
    void shouldThrowRestaurantNotFoundExceptionWhenUpdateIsCalledWithInvalidId() {
        // Arrange
        Long invalidRestaurantId = 999L;
        when(restaurantRepository.update(invalidRestaurantId, restaurant)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> restaurantUseCase.update(invalidRestaurantId, restaurant))
                .isInstanceOf(RestaurantNotFoundException.class)
                .hasMessage("Restaurant not found with id: 999");
        verify(restaurantRepository, times(1)).update(invalidRestaurantId, restaurant);
    }

    @Test
    @DisplayName("Should delete restaurant when deleteById is called with valid id")
    void shouldDeleteRestaurantWhenDeleteByIdIsCalledWithValidId() {
        // Arrange
        Long restaurantId = 1L;
        when(restaurantRepository.deleteById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Act
        Optional<Restaurant> result = restaurantUseCase.deleteById(restaurantId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(restaurant);
        verify(restaurantRepository, times(1)).deleteById(restaurantId);
    }

    @Test
    @DisplayName("Should throw RuntimeException when deleteById is called with invalid id")
    void shouldThrowRuntimeExceptionWhenDeleteByIdIsCalledWithInvalidId() {
        // Arrange
        Long invalidRestaurantId = 999L;
        when(restaurantRepository.deleteById(invalidRestaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> restaurantUseCase.deleteById(invalidRestaurantId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Restaurant not found with id: 999");
        verify(restaurantRepository, times(1)).deleteById(invalidRestaurantId);
    }

    @Test
    @DisplayName("Should update owner when updateOwner is called with valid id")
    void shouldUpdateOwnerWhenUpdateOwnerIsCalledWithValidId() {
        // Arrange
        Long restaurantId = 1L;
        String ownerName = "New Owner";
        String email = "newowner@test.com";
        Restaurant updatedRestaurant = new Restaurant(1L, "Restaurante do João", "Brasileira", "joao@test.com", "New Owner", null, null);

        when(restaurantRepository.updateOwner(restaurantId, ownerName, email))
                .thenReturn(Optional.of(updatedRestaurant));

        // Act
        Optional<Restaurant> result = restaurantUseCase.updateOwner(restaurantId, ownerName, email);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(updatedRestaurant);
        verify(restaurantRepository, times(1)).updateOwner(restaurantId, ownerName, email);
    }

    @Test
    @DisplayName("Should throw RestaurantNotFoundException when updateOwner is called with invalid id")
    void shouldThrowRestaurantNotFoundExceptionWhenUpdateOwnerIsCalledWithInvalidId() {
        // Arrange
        Long invalidRestaurantId = 999L;
        String ownerName = "New Owner";
        String email = "newowner@test.com";

        when(restaurantRepository.updateOwner(invalidRestaurantId, ownerName, email))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> restaurantUseCase.updateOwner(invalidRestaurantId, ownerName, email))
                .isInstanceOf(RestaurantNotFoundException.class)
                .hasMessage("Restaurant not found with id: 999");
        verify(restaurantRepository, times(1)).updateOwner(invalidRestaurantId, ownerName, email);
    }

    @Test
    @DisplayName("Should handle null restaurant gracefully when create is called")
    void shouldHandleNullRestaurantGracefullyWhenCreateIsCalled() {
        // Arrange
        Restaurant nullRestaurant = null;
        when(restaurantRepository.create(nullRestaurant)).thenReturn(null);

        // Act
        Restaurant result = restaurantUseCase.create(nullRestaurant);

        // Assert
        assertThat(result).isNull();
        verify(restaurantRepository, times(1)).create(nullRestaurant);
    }
}
