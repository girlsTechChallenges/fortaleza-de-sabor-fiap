package com.br.fiap.fortaleza.sabor.application.ports.out;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.BusinessHours;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RestaurantsRepositoryPort Contract Tests")
class RestaurantsRepositoryPortTest {

    @Mock
    private RestaurantsRepositoryPort restaurantsRepositoryPort;

    private Restaurant restaurant1;
    private Restaurant restaurant2;

    @BeforeEach
    void setUp() {
        Address address1 = new Address("Rua das Flores", "Centro", "Loja 1", 100, "SP", "São Paulo", "01234-567");
        Address address2 = new Address("Av. Paulista", "Bela Vista", "Andar 2", 1000, "SP", "São Paulo", "01310-100");

        BusinessHours hours1 = new BusinessHours(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(18, 0), "Funcionamento normal");
        BusinessHours hours2 = new BusinessHours(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(18, 0), "Funcionamento normal");

        restaurant1 = new Restaurant(1L, "Restaurante Sabor", "Italiana", "sabor@email.com", "João Silva", 
                                   Arrays.asList(address1), Arrays.asList(hours1));
        restaurant2 = new Restaurant(2L, "Cantina da Nonna", "Italiana", "nonna@email.com", "Maria Santos", 
                                   Arrays.asList(address2), Arrays.asList(hours2));
    }

    @Test
    @DisplayName("Should create restaurant successfully")
    void shouldCreateRestaurant() {
        // Arrange
        when(restaurantsRepositoryPort.create(any(Restaurant.class))).thenReturn(restaurant1);

        // Act
        Restaurant result = restaurantsRepositoryPort.create(restaurant1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Restaurante Sabor");
        assertThat(result.getKitchenType()).isEqualTo("Italiana");
        assertThat(result.getEmail()).isEqualTo("sabor@email.com");
        verify(restaurantsRepositoryPort).create(restaurant1);
    }

    @Test
    @DisplayName("Should update restaurant successfully")
    void shouldUpdateRestaurant() {
        // Arrange
        when(restaurantsRepositoryPort.update(anyLong(), any(Restaurant.class))).thenReturn(Optional.of(restaurant1));

        // Act
        Optional<Restaurant> result = restaurantsRepositoryPort.update(1L, restaurant1);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Restaurante Sabor");
        verify(restaurantsRepositoryPort).update(1L, restaurant1);
    }

    @Test
    @DisplayName("Should return empty when updating non-existent restaurant")
    void shouldReturnEmptyWhenUpdatingNonExistentRestaurant() {
        // Arrange
        when(restaurantsRepositoryPort.update(anyLong(), any(Restaurant.class))).thenReturn(Optional.empty());

        // Act
        Optional<Restaurant> result = restaurantsRepositoryPort.update(999L, restaurant1);

        // Assert
        assertThat(result).isEmpty();
        verify(restaurantsRepositoryPort).update(999L, restaurant1);
    }

    @Test
    @DisplayName("Should get restaurant by id successfully")
    void shouldGetRestaurantById() {
        // Arrange
        when(restaurantsRepositoryPort.getById(anyLong())).thenReturn(Optional.of(restaurant1));

        // Act
        Optional<Restaurant> result = restaurantsRepositoryPort.getById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Restaurante Sabor");
        verify(restaurantsRepositoryPort).getById(1L);
    }

    @Test
    @DisplayName("Should return empty when getting non-existent restaurant by id")
    void shouldReturnEmptyWhenGettingNonExistentRestaurantById() {
        // Arrange
        when(restaurantsRepositoryPort.getById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<Restaurant> result = restaurantsRepositoryPort.getById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(restaurantsRepositoryPort).getById(999L);
    }

    @Test
    @DisplayName("Should delete restaurant by id successfully")
    void shouldDeleteRestaurantById() {
        // Arrange
        when(restaurantsRepositoryPort.deleteById(anyLong())).thenReturn(Optional.of(restaurant1));

        // Act
        Optional<Restaurant> result = restaurantsRepositoryPort.deleteById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Restaurante Sabor");
        verify(restaurantsRepositoryPort).deleteById(1L);
    }

    @Test
    @DisplayName("Should return empty when deleting non-existent restaurant")
    void shouldReturnEmptyWhenDeletingNonExistentRestaurant() {
        // Arrange
        when(restaurantsRepositoryPort.deleteById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<Restaurant> result = restaurantsRepositoryPort.deleteById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(restaurantsRepositoryPort).deleteById(999L);
    }

    @Test
    @DisplayName("Should return all restaurants when getAll is called")
    void shouldReturnAllRestaurants() {
        // Arrange
        List<Restaurant> expectedRestaurants = Arrays.asList(restaurant1, restaurant2);
        when(restaurantsRepositoryPort.getAll()).thenReturn(expectedRestaurants);

        // Act
        List<Restaurant> result = restaurantsRepositoryPort.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(restaurant1, restaurant2);
        verify(restaurantsRepositoryPort).getAll();
    }

    @Test
    @DisplayName("Should update owner successfully")
    void shouldUpdateOwner() {
        // Arrange
        when(restaurantsRepositoryPort.updateOwner(anyLong(), anyString(), anyString())).thenReturn(Optional.of(restaurant1));

        // Act
        Optional<Restaurant> result = restaurantsRepositoryPort.updateOwner(1L, "Novo Proprietário", "novo@email.com");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Restaurante Sabor");
        verify(restaurantsRepositoryPort).updateOwner(1L, "Novo Proprietário", "novo@email.com");
    }

    @Test
    @DisplayName("Should return empty when updating owner of non-existent restaurant")
    void shouldReturnEmptyWhenUpdatingOwnerOfNonExistentRestaurant() {
        // Arrange
        when(restaurantsRepositoryPort.updateOwner(anyLong(), anyString(), anyString())).thenReturn(Optional.empty());

        // Act
        Optional<Restaurant> result = restaurantsRepositoryPort.updateOwner(999L, "Novo Proprietário", "novo@email.com");

        // Assert
        assertThat(result).isEmpty();
        verify(restaurantsRepositoryPort).updateOwner(999L, "Novo Proprietário", "novo@email.com");
    }
}
