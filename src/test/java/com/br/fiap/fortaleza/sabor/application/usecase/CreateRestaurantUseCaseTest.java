package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.RestaurantsRepository;
import com.br.fiap.fortaleza.sabor.application.usecase.restaurant.CreateRestaurantUseCase;
import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.restaurant.BusinessHours;
import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.RestaurantAlreadyExistsException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Mock
    private RestaurantsRepository restaurantRepository;

    private Restaurant restaurant;
    private List<Address> addresses;
    private List<BusinessHours> businessHours;

    @BeforeEach
    void setUp() {
        addresses = Arrays.asList(
                new Address("Rua das Flores", "Centro", "Apto 101", 123, "São Paulo", "SP", "01234-567")
        );
        
        businessHours = Arrays.asList(
                new BusinessHours(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(22, 0), "Funcionamento normal"),
                new BusinessHours(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(22, 0), "Funcionamento normal")
        );
        
        restaurant = new Restaurant(
                null,
                "Restaurante Teste",
                "Brasileira",
                "dono@teste.com",
                "Dono Teste",
                addresses,
                businessHours
        );
    }

    @Test
    @DisplayName("Should save restaurant successfully")
    void shouldSaveRestaurantSuccessfully() {
        // Arrange
        Restaurant savedRestaurant = new Restaurant(
                1L,
                restaurant.getName(),
                restaurant.getKitchenType(),
                restaurant.getEmail(),
                restaurant.getOwner(),
                restaurant.getAddress(),
                restaurant.getBusinessHours()
        );

        when(restaurantRepository.create(any(Restaurant.class))).thenReturn(savedRestaurant);

        // Act
        Restaurant result = createRestaurantUseCase.save(restaurant);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Restaurante Teste", result.getName());
        assertEquals("Brasileira", result.getKitchenType());
        assertEquals("dono@teste.com", result.getEmail());
        assertEquals("Dono Teste", result.getOwner());
        assertNotNull(result.getAddress());
        assertEquals(1, result.getAddress().size());
        assertNotNull(result.getBusinessHours());
        assertEquals(2, result.getBusinessHours().size());

        verify(restaurantRepository, times(1)).create(restaurant);
    }

    @Test
    @DisplayName("Should handle null restaurant gracefully")
    void shouldHandleNullRestaurant() {
        // Arrange
        when(restaurantRepository.create(null)).thenReturn(null);

        // Act
        Restaurant result = createRestaurantUseCase.save(null);

        // Assert
        assertNull(result);
        verify(restaurantRepository, times(1)).create(null);
    }

    @Test
    @DisplayName("Should save restaurant with valid owner email")
    void shouldSaveRestaurantWithValidOwnerEmail() {
        // Arrange
        Restaurant restaurantWithValidEmail = new Restaurant(
                null, "Pizzaria do João", "Italiana", "joao.dono@email.com", "João Silva",
                addresses, businessHours
        );
        
        Restaurant savedRestaurant = new Restaurant(
                2L, "Pizzaria do João", "Italiana", "joao.dono@email.com", "João Silva",
                addresses, businessHours
        );

        when(restaurantRepository.create(restaurantWithValidEmail)).thenReturn(savedRestaurant);

        // Act
        Restaurant result = createRestaurantUseCase.save(restaurantWithValidEmail);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Pizzaria do João", result.getName());
        assertEquals("joao.dono@email.com", result.getEmail());
        verify(restaurantRepository, times(1)).create(restaurantWithValidEmail);
    }

    @Test
    @DisplayName("Should handle restaurant with empty address list")
    void shouldHandleRestaurantWithEmptyAddressList() {
        // Arrange
        Restaurant restaurantWithEmptyAddress = new Restaurant(
                null, "Lanchonete", "Fast Food", "lanchonete@email.com", "Proprietário",
                new ArrayList<>(), businessHours
        );

        Restaurant savedRestaurant = new Restaurant(
                3L, "Lanchonete", "Fast Food", "lanchonete@email.com", "Proprietário",
                new ArrayList<>(), businessHours
        );

        when(restaurantRepository.create(restaurantWithEmptyAddress)).thenReturn(savedRestaurant);

        // Act
        Restaurant result = createRestaurantUseCase.save(restaurantWithEmptyAddress);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertTrue(result.getAddress().isEmpty());
        verify(restaurantRepository, times(1)).create(restaurantWithEmptyAddress);
    }

    @Test
    @DisplayName("Should handle restaurant with empty business hours")
    void shouldHandleRestaurantWithEmptyBusinessHours() {
        // Arrange
        Restaurant restaurantWithEmptyHours = new Restaurant(
                null, "Café Express", "Café", "cafe@email.com", "Dono Café",
                addresses, new ArrayList<>()
        );

        Restaurant savedRestaurant = new Restaurant(
                4L, "Café Express", "Café", "cafe@email.com", "Dono Café",
                addresses, new ArrayList<>()
        );

        when(restaurantRepository.create(restaurantWithEmptyHours)).thenReturn(savedRestaurant);

        // Act
        Restaurant result = createRestaurantUseCase.save(restaurantWithEmptyHours);

        // Assert
        assertNotNull(result);
        assertEquals(4L, result.getId());
        assertTrue(result.getBusinessHours().isEmpty());
        verify(restaurantRepository, times(1)).create(restaurantWithEmptyHours);
    }

    @Test
    @DisplayName("Should handle restaurant with special characters in name")
    void shouldHandleRestaurantWithSpecialCharactersInName() {
        // Arrange
        Restaurant restaurantWithSpecialChars = new Restaurant(
                null, "Restaurante São José & Cia.", "Regional", "saojose@email.com", "José Santos",
                addresses, businessHours
        );

        Restaurant savedRestaurant = new Restaurant(
                5L, "Restaurante São José & Cia.", "Regional", "saojose@email.com", "José Santos",
                addresses, businessHours
        );

        when(restaurantRepository.create(restaurantWithSpecialChars)).thenReturn(savedRestaurant);

        // Act
        Restaurant result = createRestaurantUseCase.save(restaurantWithSpecialChars);

        // Assert
        assertNotNull(result);
        assertEquals("Restaurante São José & Cia.", result.getName());
        verify(restaurantRepository, times(1)).create(restaurantWithSpecialChars);
    }

    @Test
    @DisplayName("Should handle restaurant with long kitchen type")
    void shouldHandleRestaurantWithLongKitchenType() {
        // Arrange
        Restaurant restaurantWithLongKitchenType = new Restaurant(
                null, "Fusão Internacional", "Culinária Asiática Contemporânea com Influências Mediterrâneas",
                "fusao@email.com", "Chef Internacional", addresses, businessHours
        );

        Restaurant savedRestaurant = new Restaurant(
                6L, "Fusão Internacional", "Culinária Asiática Contemporânea com Influências Mediterrâneas",
                "fusao@email.com", "Chef Internacional", addresses, businessHours
        );

        when(restaurantRepository.create(restaurantWithLongKitchenType)).thenReturn(savedRestaurant);

        // Act
        Restaurant result = createRestaurantUseCase.save(restaurantWithLongKitchenType);

        // Assert
        assertNotNull(result);
        assertEquals("Culinária Asiática Contemporânea com Influências Mediterrâneas", result.getKitchenType());
        verify(restaurantRepository, times(1)).create(restaurantWithLongKitchenType);
    }

    @Test
    @DisplayName("Should handle restaurant with multiple addresses")
    void shouldHandleRestaurantWithMultipleAddresses() {
        // Arrange
        List<Address> multipleAddresses = Arrays.asList(
                new Address("Rua Principal", "Centro", "Loja 1", 100, "São Paulo", "SP", "01000-000"),
                new Address("Av. Secundária", "Bairro Novo", "Loja 2", 200, "São Paulo", "SP", "02000-000")
        );

        Restaurant restaurantWithMultipleAddresses = new Restaurant(
                null, "Rede de Restaurantes", "Variada", "rede@email.com", "Grupo Empresarial",
                multipleAddresses, businessHours
        );

        Restaurant savedRestaurant = new Restaurant(
                7L, "Rede de Restaurantes", "Variada", "rede@email.com", "Grupo Empresarial",
                multipleAddresses, businessHours
        );

        when(restaurantRepository.create(restaurantWithMultipleAddresses)).thenReturn(savedRestaurant);

        // Act
        Restaurant result = createRestaurantUseCase.save(restaurantWithMultipleAddresses);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getAddress().size());
        assertEquals("Rua Principal", result.getAddress().get(0).getRua());
        assertEquals("Av. Secundária", result.getAddress().get(1).getRua());
        verify(restaurantRepository, times(1)).create(restaurantWithMultipleAddresses);
    }

    @Test
    @DisplayName("Should handle restaurant with comprehensive business hours")
    void shouldHandleRestaurantWithComprehensiveBusinessHours() {
        // Arrange
        List<BusinessHours> fullWeekHours = Arrays.asList(
                new BusinessHours(DayOfWeek.MONDAY, LocalTime.of(6, 0), LocalTime.of(23, 0), "Segunda-feira"),
                new BusinessHours(DayOfWeek.TUESDAY, LocalTime.of(6, 0), LocalTime.of(23, 0), "Terça-feira"),
                new BusinessHours(DayOfWeek.WEDNESDAY, LocalTime.of(6, 0), LocalTime.of(23, 0), "Quarta-feira"),
                new BusinessHours(DayOfWeek.THURSDAY, LocalTime.of(6, 0), LocalTime.of(23, 0), "Quinta-feira"),
                new BusinessHours(DayOfWeek.FRIDAY, LocalTime.of(6, 0), LocalTime.of(23, 59), "Sexta até tarde"),
                new BusinessHours(DayOfWeek.SATURDAY, LocalTime.of(8, 0), LocalTime.of(23, 59), "Sábado especial"),
                new BusinessHours(DayOfWeek.SUNDAY, LocalTime.of(10, 0), LocalTime.of(22, 0), "Domingo reduzido")
        );

        Restaurant restaurantWithFullWeek = new Restaurant(
                null, "Restaurante 24/7", "Diversos", "sempre@aberto.com", "Dono Dedicado",
                addresses, fullWeekHours
        );

        Restaurant savedRestaurant = new Restaurant(
                8L, "Restaurante 24/7", "Diversos", "sempre@aberto.com", "Dono Dedicado",
                addresses, fullWeekHours
        );

        when(restaurantRepository.create(restaurantWithFullWeek)).thenReturn(savedRestaurant);

        // Act
        Restaurant result = createRestaurantUseCase.save(restaurantWithFullWeek);

        // Assert
        assertNotNull(result);
        assertEquals(7, result.getBusinessHours().size());
        assertTrue(result.getBusinessHours().stream().anyMatch(bh -> bh.getDayOfWeek() == DayOfWeek.SUNDAY));
        verify(restaurantRepository, times(1)).create(restaurantWithFullWeek);
    }

    @Test
    @DisplayName("Should propagate UserNotFoundException when repository throws it")
    void shouldPropagateUserNotFoundException() {
        // Arrange
        when(restaurantRepository.create(any(Restaurant.class)))
                .thenThrow(new UserNotFoundException("test@email.com"));

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, 
                () -> createRestaurantUseCase.save(restaurant));
        
        assertEquals("User test@email.com not found", exception.getMessage());
        verify(restaurantRepository, times(1)).create(restaurant);
    }

    @Test
    @DisplayName("Should propagate RestaurantAlreadyExistsException when repository throws it")
    void shouldPropagateRestaurantAlreadyExistsException() {
        // Arrange
        when(restaurantRepository.create(any(Restaurant.class)))
                .thenThrow(new RestaurantAlreadyExistsException("Restaurant with name Restaurante Teste already exists"));

        // Act & Assert
        RestaurantAlreadyExistsException exception = assertThrows(RestaurantAlreadyExistsException.class,
                () -> createRestaurantUseCase.save(restaurant));
        
        assertEquals("Restaurant with name Restaurante Teste already exists", exception.getMessage());
        verify(restaurantRepository, times(1)).create(restaurant);
    }

    @Test
    @DisplayName("Should handle runtime exception from repository")
    void shouldHandleRuntimeExceptionFromRepository() {
        // Arrange
        when(restaurantRepository.create(any(Restaurant.class)))
                .thenThrow(new RuntimeException("Database connection error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> createRestaurantUseCase.save(restaurant));
        
        assertEquals("Database connection error", exception.getMessage());
        verify(restaurantRepository, times(1)).create(restaurant);
    }

    @Test
    @DisplayName("Should handle restaurant with null fields")
    void shouldHandleRestaurantWithNullFields() {
        // Arrange
        Restaurant restaurantWithNulls = new Restaurant(
                null, null, null, null, null, null, null
        );

        when(restaurantRepository.create(restaurantWithNulls)).thenReturn(null);

        // Act
        Restaurant result = createRestaurantUseCase.save(restaurantWithNulls);

        // Assert
        assertNull(result);
        verify(restaurantRepository, times(1)).create(restaurantWithNulls);
    }

    @Test
    @DisplayName("Should handle restaurant with minimal valid data")
    void shouldHandleRestaurantWithMinimalValidData() {
        // Arrange
        Restaurant minimalRestaurant = new Restaurant(
                null, "R", "A", "a@b.co", "X", Collections.emptyList(), Collections.emptyList()
        );

        Restaurant savedMinimal = new Restaurant(
                9L, "R", "A", "a@b.co", "X", Collections.emptyList(), Collections.emptyList()
        );

        when(restaurantRepository.create(minimalRestaurant)).thenReturn(savedMinimal);

        // Act
        Restaurant result = createRestaurantUseCase.save(minimalRestaurant);

        // Assert
        assertNotNull(result);
        assertEquals("R", result.getName());
        assertEquals("A", result.getKitchenType());
        assertEquals("a@b.co", result.getEmail());
        assertTrue(result.getAddress().isEmpty());
        assertTrue(result.getBusinessHours().isEmpty());
        verify(restaurantRepository, times(1)).create(minimalRestaurant);
    }
}
