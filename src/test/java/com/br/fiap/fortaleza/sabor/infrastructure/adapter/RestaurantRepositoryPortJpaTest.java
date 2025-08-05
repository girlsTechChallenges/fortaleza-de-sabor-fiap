package com.br.fiap.fortaleza.sabor.infrastructure.adapter;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.BusinessHours;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.RestaurantNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.RestaurantMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RestaurantRepositoryPortJpa Tests")
class RestaurantRepositoryPortJpaTest {

    @Mock
    private RestaurantRepositoryAdapter restaurantRepositoryAdapter;

    @Mock
    private UserRepositoryAdapter userRepositoryAdapter;

    @Mock
    private RestaurantMapper mapper;

    @InjectMocks
    private RestaurantRepositoryPortJpa restaurantRepositoryPortJpa;

    private Restaurant restaurant;
    private RestaurantEntity restaurantEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        Address address = new Address("Street das Flores", "Centro", "Loja 1", 100, "SP", "São Paulo", "01234-567");
        BusinessHours hours = new BusinessHours(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(18, 0), "Funcionamento normal");
        
        restaurant = new Restaurant(1L, "Restaurant Sabor", "Italiana", "owner@email.com", "João Silva", 
                                  Arrays.asList(address), Arrays.asList(hours));

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("owner@email.com");
        userEntity.setNome("João Silva");

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Restaurant Sabor");
        restaurantEntity.setTypeKitchen("Italiana");
        restaurantEntity.setOwner(userEntity);
    }

    @Test
    @DisplayName("Should get all restaurants successfully")
    void shouldGetAllRestaurantsSuccessfully() {
        // Arrange
        List<RestaurantEntity> entities = Arrays.asList(restaurantEntity);
        when(restaurantRepositoryAdapter.findAll()).thenReturn(entities);
        when(mapper.toRestaurantDomain(any(RestaurantEntity.class))).thenReturn(restaurant);

        // Act
        List<Restaurant> result = restaurantRepositoryPortJpa.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Restaurant Sabor");
        verify(restaurantRepositoryAdapter).findAll();
        verify(mapper).toRestaurantDomain(restaurantEntity);
    }

    @Test
    @DisplayName("Should return empty list when no restaurants exist")
    void shouldReturnEmptyListWhenNoRestaurantsExist() {
        // Arrange
        when(restaurantRepositoryAdapter.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Restaurant> result = restaurantRepositoryPortJpa.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(restaurantRepositoryAdapter).findAll();
        verify(mapper, never()).toRestaurantDomain(any(RestaurantEntity.class));
    }

    @Test
    @DisplayName("Should get restaurant by id successfully when restaurant exists")
    void shouldGetRestaurantByIdSuccessfully() {
        // Arrange
        when(restaurantRepositoryAdapter.findById(anyLong())).thenReturn(Optional.of(restaurantEntity));
        when(mapper.toRestaurantDomain(any(RestaurantEntity.class))).thenReturn(restaurant);

        // Act
        Optional<Restaurant> result = restaurantRepositoryPortJpa.getById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Restaurant Sabor");
        verify(restaurantRepositoryAdapter).findById(1L);
        verify(mapper).toRestaurantDomain(restaurantEntity);
    }

    @Test
    @DisplayName("Should throw RestaurantNotFoundException when getting non-existent restaurant by id")
    void shouldThrowExceptionWhenGettingNonExistentRestaurantById() {
        // Arrange
        when(restaurantRepositoryAdapter.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> restaurantRepositoryPortJpa.getById(999L))
                .isInstanceOf(RestaurantNotFoundException.class)
                .hasMessage("Restaurant not found with id: 999");

        verify(restaurantRepositoryAdapter).findById(999L);
        verify(mapper, never()).toRestaurantDomain(any(RestaurantEntity.class));
    }

    @Test
    @DisplayName("Should delete restaurant by id successfully when restaurant exists")
    void shouldDeleteRestaurantByIdSuccessfully() {
        // Arrange
        when(restaurantRepositoryAdapter.findById(anyLong())).thenReturn(Optional.of(restaurantEntity));
        when(mapper.toRestaurantDomain(any(RestaurantEntity.class))).thenReturn(restaurant);

        // Act
        Optional<Restaurant> result = restaurantRepositoryPortJpa.deleteById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Restaurant Sabor");
        verify(restaurantRepositoryAdapter).findById(1L);
        verify(restaurantRepositoryAdapter).delete(restaurantEntity);
        verify(mapper).toRestaurantDomain(restaurantEntity);
    }

    @Test
    @DisplayName("Should throw RestaurantNotFoundException when trying to delete non-existent restaurant")
    void shouldThrowExceptionWhenDeletingNonExistentRestaurant() {
        // Arrange
        when(restaurantRepositoryAdapter.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> restaurantRepositoryPortJpa.deleteById(999L))
                .isInstanceOf(RestaurantNotFoundException.class)
                .hasMessage("Restaurant not found with id: 999");

        verify(restaurantRepositoryAdapter).findById(999L);
        verify(restaurantRepositoryAdapter, never()).delete(any());
    }
}
