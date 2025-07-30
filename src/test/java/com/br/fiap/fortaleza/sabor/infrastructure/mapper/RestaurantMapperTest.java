package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.restaurant.BusinessHours;
import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.BusinessHoursDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.AddressRestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.BusinessHoursEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantMapperTest {

    @InjectMocks
    private RestaurantMapper restaurantMapper;

    private RestaurantRequestDto restaurantRequestDto;
    private Restaurant restaurant;
    private RestaurantEntity restaurantEntity;

    @BeforeEach
    void setUp() {
        // Setup Address DTO
        AddressDto addressDto = new AddressDto(
                "Rua Teste",
                "Bairro Teste",
                "Apto 1",
                123,
                "Estado Teste",
                "Cidade Teste",
                "12345678"
        );

        // Setup Business Hours DTO
        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(18, 0),
                "Observação teste"
        );

        // Setup Request DTO
        restaurantRequestDto = new RestaurantRequestDto(
                "Restaurante Teste",
                "Brasileira",
                "teste@teste.com",
                List.of(addressDto),
                List.of(businessHoursDto)
        );

        // Setup Domain Object
        Address address = new Address(
                "Rua Teste",
                "Bairro Teste",
                "Apto 1",
                123,
                "Estado Teste",
                "Cidade Teste",
                "12345678"
        );

        BusinessHours businessHours = new BusinessHours(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(18, 0),
                "Observação teste"
        );

        restaurant = new Restaurant(
                1L,
                "Restaurante Teste",
                "Brasileira",
                "teste@teste.com",
                "Dono Teste",
                List.of(address),
                List.of(businessHours)
        );

        // Setup User Entity
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("teste@teste.com");
        userEntity.setName("Dono Teste");

        // Setup Address Entity
        AddressRestaurantEntity addressEntity = new AddressRestaurantEntity();
        addressEntity.setStreet("Rua Teste");
        addressEntity.setDistrict("Bairro Teste");
        addressEntity.setComplement("Apto 1");
        addressEntity.setNumber(123);
        addressEntity.setState("Estado Teste");
        addressEntity.setCity("Cidade Teste");
        addressEntity.setPostCode("12345678");

        // Setup Business Hours Entity
        BusinessHoursEntity businessHoursEntity = new BusinessHoursEntity();
        businessHoursEntity.setDay(DayOfWeek.MONDAY);
        businessHoursEntity.setOpeningTime(LocalTime.of(8, 0));
        businessHoursEntity.setClosingTime(LocalTime.of(18, 0));
        businessHoursEntity.setObservations("Observação teste");

        // Setup Restaurant Entity
        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Restaurante Teste");
        restaurantEntity.setTypeKitchen("Brasileira");
        restaurantEntity.setOwner(userEntity);
        restaurantEntity.setAddress(List.of(addressEntity));
        restaurantEntity.setBusinessHours(List.of(businessHoursEntity));
    }

    @Test
    @DisplayName("Should convert RestaurantRequestDto to Restaurant domain")
    void shouldConvertRestaurantRequestDtoToRestaurantDomain() {
        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals("Restaurante Teste", result.getName());
        assertEquals("Brasileira", result.getKitchenType());
        assertEquals("teste@teste.com", result.getEmail());
        
        assertNotNull(result.getAddress());
        assertEquals(1, result.getAddress().size());
        assertEquals("Rua Teste", result.getAddress().getFirst().getStreet());
        
        assertNotNull(result.getBusinessHours());
        assertEquals(1, result.getBusinessHours().size());
        assertEquals(DayOfWeek.MONDAY, result.getBusinessHours().getFirst().getDayOfWeek());
    }

    @Test
    @DisplayName("Should convert Restaurant domain to RestaurantEntity")
    void shouldConvertRestaurantDomainToRestaurantEntity() {
        // Act
        RestaurantEntity result = restaurantMapper.toRestaurantEntity(restaurant);

        // Assert
        assertNotNull(result);
        assertEquals("Restaurante Teste", result.getName());
        assertEquals("Brasileira", result.getTypeKitchen());
        
        assertNotNull(result.getAddress());
        assertEquals(1, result.getAddress().size());
        assertEquals("Rua Teste", result.getAddress().getFirst().getStreet());
        
        assertNotNull(result.getBusinessHours());
        assertEquals(1, result.getBusinessHours().size());
        assertEquals(DayOfWeek.MONDAY, result.getBusinessHours().getFirst().getDay());
    }

    @Test
    @DisplayName("Should convert RestaurantEntity to Restaurant domain")
    void shouldConvertRestaurantEntityToRestaurantDomain() {
        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantEntity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Restaurante Teste", result.getName());
        assertEquals("Brasileira", result.getKitchenType());
        assertEquals("teste@teste.com", result.getEmail());
        assertEquals("Dono Teste", result.getOwner());
        
        assertNotNull(result.getAddress());
        assertEquals(1, result.getAddress().size());
        assertEquals("Rua Teste", result.getAddress().getFirst().getStreet());
        
        assertNotNull(result.getBusinessHours());
        assertEquals(1, result.getBusinessHours().size());
        assertEquals(DayOfWeek.MONDAY, result.getBusinessHours().getFirst().getDayOfWeek());
    }

    @Test
    @DisplayName("Should convert Restaurant domain to RestaurantResponseDto")
    void shouldConvertRestaurantDomainToRestaurantResponseDto() {
        // Act
        RestaurantResponseDto result = restaurantMapper.toRestaurantResponseDto(restaurant);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Restaurante Teste", result.name());
        assertEquals("Dono Teste", result.owner());
    }

    @Test
    @DisplayName("Should convert business hours to entities")
    void shouldConvertBusinessHoursToEntities() {
        // Act
        List<BusinessHoursEntity> result = restaurantMapper.toBusinessHoursEntities(restaurant, restaurantEntity);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        
        BusinessHoursEntity businessHoursEntity = result.getFirst();
        assertEquals(DayOfWeek.MONDAY, businessHoursEntity.getDay());
        assertEquals(LocalTime.of(8, 0), businessHoursEntity.getOpeningTime());
        assertEquals(LocalTime.of(18, 0), businessHoursEntity.getClosingTime());
        assertEquals("Observação teste", businessHoursEntity.getObservations());
        assertEquals(restaurantEntity, businessHoursEntity.getRestaurant());
    }

    @Test
    @DisplayName("Should handle empty lists gracefully")
    void shouldHandleEmptyListsGracefully() {
        // Arrange
        RestaurantRequestDto emptyDto = new RestaurantRequestDto(
                "Restaurante Vazio",
                "Italiana",
                "vazio@teste.com",
                new ArrayList<>(),
                new ArrayList<>()
        );

        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(emptyDto);

        // Assert
        assertNotNull(result);
        assertEquals("Restaurante Vazio", result.getName());
        assertNotNull(result.getAddress());
        assertTrue(result.getAddress().isEmpty());
        assertNotNull(result.getBusinessHours());
        assertTrue(result.getBusinessHours().isEmpty());
    }

    @Test
    @DisplayName("Should filter out null business hours when converting")
    void shouldFilterOutNullBusinessHoursWhenConverting() {
        // Arrange
        Restaurant restaurantWithNullHours = new Restaurant(
                1L,
                "Restaurante Teste",
                "Brasileira",
                "teste@teste.com",
                "Dono Teste",
                new ArrayList<>(),
                List.of(
                        new BusinessHours(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(18, 0), "Valid"),
                        new BusinessHours(null, null, null, null) // This should be filtered out
                )
        );

        // Act
        List<BusinessHoursEntity> result = restaurantMapper.toBusinessHoursEntities(restaurantWithNullHours, restaurantEntity);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size()); // Only the valid one should remain
        assertEquals(DayOfWeek.MONDAY, result.getFirst().getDay());
    }
}
