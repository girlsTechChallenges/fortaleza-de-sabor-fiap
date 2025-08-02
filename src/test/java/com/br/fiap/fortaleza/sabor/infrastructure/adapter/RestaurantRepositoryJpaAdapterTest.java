//package com.br.fiap.fortaleza.sabor.infrastructure.gateways;
//
//import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
//import com.br.fiap.fortaleza.sabor.infrastructure.exception.RestaurantNotFoundException;
//import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserNotFoundException;
//import com.br.fiap.fortaleza.sabor.infrastructure.mapper.RestaurantMapper;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantRepositoryAdapter;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserRepositoryAdapter;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class RestaurantRepositoryJpaAdapterTest {
//
//    @InjectMocks
//    private RestaurantRepositoryPortJpa restaurantRepositoryJpa;
//
//    @Mock
//    private RestaurantRepositoryAdapter restaurantRepositoryAdapter;
//
//    @Mock
//    private UserRepositoryAdapter userRepositoryAdapter;
//
//    @Mock
//    private RestaurantMapper mapper;
//
//    private Restaurant restaurant;
//    private RestaurantEntity restaurantEntity;
//    private UserEntity userEntity;
//
//    @BeforeEach
//    void setUp() {
//        userEntity = new UserEntity();
//        userEntity.setId(1L);
//        userEntity.setEmail("teste@teste.com");
//        userEntity.setNome("Dono Teste");
//        userEntity.setTipo(new TypeEntity(1L, "DONO"));
//
//        restaurant = new Restaurant(
//                null,
//                "Restaurante Teste",
//                "Brasileira",
//                "teste@teste.com",
//                "Dono Teste",
//                new ArrayList<>(),
//                new ArrayList<>()
//        );
//
//        restaurantEntity = new RestaurantEntity();
//        restaurantEntity.setId(1L);
//        restaurantEntity.setName("Restaurante Teste");
//        restaurantEntity.setTypeKitchen("Brasileira");
//        restaurantEntity.setOwner(userEntity);
//        restaurantEntity.setAddress(new ArrayList<>());
//        restaurantEntity.setBusinessHours(new ArrayList<>());
//    }
//
//    @Test
//    @DisplayName("Should create restaurant successfully")
//    void shouldCreateRestaurantSuccessfully() {
//        // Arrange
//        Restaurant savedRestaurant = new Restaurant(
//                1L,
//                restaurant.getName(),
//                restaurant.getKitchenType(),
//                restaurant.getEmail(),
//                restaurant.getOwner(),
//                restaurant.getAddress(),
//                restaurant.getBusinessHours()
//        );
//
//        when(userRepositoryAdapter.findByEmail(restaurant.getEmail())).thenReturn(Optional.of(userEntity));
//        when(restaurantRepositoryAdapter.findByName(restaurant.getName())).thenReturn(Optional.empty());
//        when(mapper.toRestaurantEntity(any(Restaurant.class))).thenReturn(restaurantEntity);
//        when(restaurantRepositoryAdapter.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);
//        when(mapper.toRestaurantDomain(any(RestaurantEntity.class))).thenReturn(savedRestaurant);
//
//        // Act
//        Restaurant result = restaurantRepositoryAdapter.create(restaurant);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("Restaurante Teste", result.getName());
//        assertEquals("Brasileira", result.getKitchenType());
//
//        verify(userRepositoryAdapter, times(1)).findByEmail(restaurant.getEmail());
//        verify(mapper, times(1)).toRestaurantEntity(restaurant);
//        verify(restaurantRepositoryAdapter, times(1)).save(any(RestaurantEntity.class));
//        verify(mapper, times(1)).toRestaurantDomain(restaurantEntity);
//    }
//
//    @Test
//    @DisplayName("Should update restaurant successfully")
//    void shouldUpdateRestaurantSuccessfully() {
//        // Arrange
//        Long restaurantId = 1L;
//        Restaurant updatedRestaurant = new Restaurant(
//                restaurantId,
//                "Restaurante Atualizado",
//                "Italiana",
//                restaurant.getEmail(),
//                restaurant.getOwner(),
//                restaurant.getAddress(),
//                restaurant.getBusinessHours()
//        );
//
//        when(userRepositoryAdapter.findByEmail(restaurant.getEmail())).thenReturn(Optional.of(userEntity));
//        when(restaurantRepositoryAdapter.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));
//        when(restaurantRepositoryAdapter.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);
//        when(mapper.toRestaurantDomain(any(RestaurantEntity.class))).thenReturn(updatedRestaurant);
//        when(mapper.toBusinessHoursEntities(any(Restaurant.class), any(RestaurantEntity.class)))
//                .thenReturn(new ArrayList<>());
//
//        // Act
//        Optional<Restaurant> result = restaurantRepositoryAdapter.update(restaurantId, restaurant);
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals(restaurantId, result.get().getId());
//        assertEquals("Restaurante Atualizado", result.get().getName());
//        assertEquals("Italiana", result.get().getKitchenType());
//
//        verify(restaurantRepositoryAdapter, times(1)).findById(restaurantId);
//        verify(restaurantRepositoryAdapter, times(1)).save(any(RestaurantEntity.class));
//        verify(mapper, times(1)).toRestaurantDomain(restaurantEntity);
//    }
//
//    @Test
//    @DisplayName("Should throw exception when restaurant not found for update")
//    void shouldThrowExceptionWhenRestaurantNotFoundForUpdate() {
//        // Arrange
//        Long restaurantId = 1L;
//        when(userRepositoryAdapter.findByEmail(restaurant.getEmail())).thenReturn(Optional.empty());
//
//        // Act & Assert
//        RestaurantNotFoundException exception = assertThrows(
//                RestaurantNotFoundException.class,
//                () -> restaurantRepositoryAdapter.update(restaurantId, restaurant)
//        );
//
//        assertEquals("User not found with email: " + restaurant.getEmail(), exception.getMessage());
//        verify(userRepositoryAdapter, times(1)).findByEmail(restaurant.getEmail());
//        verify(restaurantRepositoryAdapter, never()).save(any());
//    }
//
//    @Test
//    @DisplayName("Should throw exception when user not found for creation")
//    void shouldThrowExceptionWhenUserNotFoundForCreation() {
//        // Arrange
//        when(userRepositoryAdapter.findByEmail(restaurant.getEmail())).thenReturn(Optional.empty());
//
//        // Act & Assert
//        UserNotFoundException exception = assertThrows(
//                UserNotFoundException.class,
//                () -> restaurantRepositoryAdapter.create(restaurant)
//        );
//
//        assertTrue(exception.getMessage().contains("User not found with email"));
//        verify(userRepositoryAdapter, times(1)).findByEmail(restaurant.getEmail());
//        verify(restaurantRepositoryAdapter, never()).save(any());
//    }
//
//    @Test
//    @DisplayName("Should handle null restaurant gracefully in create")
//    void shouldHandleNullRestaurantGracefullyInCreate() {
//        // Act & Assert
//        assertThrows(NullPointerException.class, () -> {
//            restaurantRepositoryAdapter.create(null);
//        });
//
//        verify(userRepositoryAdapter, never()).findByEmail(any());
//        verify(restaurantRepositoryAdapter, never()).save(any());
//    }
//
//    @Test
//    @DisplayName("Should handle null restaurant id in update")
//    void shouldHandleNullRestaurantIdInUpdate() {
//        // Arrange
//        when(userRepositoryAdapter.findByEmail(restaurant.getEmail())).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(RestaurantNotFoundException.class, () -> {
//            restaurantRepositoryAdapter.update(null, restaurant);
//        });
//
//        verify(userRepositoryAdapter, times(1)).findByEmail(restaurant.getEmail());
//        verify(restaurantRepositoryAdapter, never()).save(any());
//    }
//}
