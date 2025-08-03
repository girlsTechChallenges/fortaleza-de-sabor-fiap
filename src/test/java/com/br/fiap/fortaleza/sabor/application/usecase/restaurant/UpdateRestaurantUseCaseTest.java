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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseTest {

   @InjectMocks
   private RestaurantUseCase RestaurantUseCase;

   @Mock
   private RestaurantsRepositoryPort restaurantRepository;

   private Restaurant restaurant;
   private Long restaurantId;

   @BeforeEach
   void setUp() {
       restaurantId = 1L;
       restaurant = new Restaurant(
               restaurantId,
               "Restaurante Atualizado",
               "Italiana",
               "updated@teste.com",
               "Novo Dono",
               new ArrayList<>(),
               new ArrayList<>()
       );
   }

   @Test
   @DisplayName("Should update restaurant successfully")
   void shouldUpdateRestaurantSuccessfully() {
       // Arrange
       when(restaurantRepository.update(eq(restaurantId), any(Restaurant.class)))
               .thenReturn(Optional.of(restaurant));

       // Act
       Optional<Restaurant> result = RestaurantUseCase.update(restaurantId, restaurant);

       // Assert
       assertTrue(result.isPresent());
       Restaurant updatedRestaurant = result.get();
       assertNotNull(updatedRestaurant);
       assertEquals(restaurantId, updatedRestaurant.getId());
       assertEquals("Restaurante Atualizado", updatedRestaurant.getName());
       assertEquals("Italiana", updatedRestaurant.getKitchenType());
       assertEquals("updated@teste.com", updatedRestaurant.getEmail());

       verify(restaurantRepository, times(1)).update(restaurantId, restaurant);
   }

   @Test
   @DisplayName("Should throw RestaurantNotFoundException when restaurant not found")
   void shouldThrowRestaurantNotFoundExceptionWhenRestaurantNotFound() {
       // Arrange
       when(restaurantRepository.update(eq(restaurantId), any(Restaurant.class)))
               .thenReturn(Optional.empty());

       // Act & Assert
       RestaurantNotFoundException exception = assertThrows(
               RestaurantNotFoundException.class,
               () -> RestaurantUseCase.update(restaurantId, restaurant)
       );

       assertEquals("Restaurant not found with id: " + restaurantId, exception.getMessage());
       verify(restaurantRepository, times(1)).update(restaurantId, restaurant);
   }

   @Test
   @DisplayName("Should handle null restaurant id")
   void shouldHandleNullRestaurantId() {
       // Arrange
       when(restaurantRepository.update(isNull(), any(Restaurant.class)))
               .thenReturn(Optional.empty());

       // Act & Assert
       assertThrows(RestaurantNotFoundException.class, () -> {
           RestaurantUseCase.update(null, restaurant);
       });

       verify(restaurantRepository, times(1)).update(null, restaurant);
   }

   @Test
   @DisplayName("Should handle null restaurant")
   void shouldHandleNullRestaurant() {
       // Arrange
       when(restaurantRepository.update(eq(restaurantId), isNull()))
               .thenReturn(Optional.empty());

       // Act & Assert
       assertThrows(RestaurantNotFoundException.class, () -> {
           RestaurantUseCase.update(restaurantId, null);
       });

       verify(restaurantRepository, times(1)).update(restaurantId, null);
   }
}
