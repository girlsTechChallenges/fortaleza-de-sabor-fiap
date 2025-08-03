package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.ports.in.RestaurantUseCasePort;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.BusinessHoursDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.RestaurantMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

   @Mock
   private RestaurantUseCasePort restaurantUseCase;

   @Mock
   private RestaurantMapper restaurantMapper;

   @InjectMocks
   private RestaurantController restaurantController;

   private MockMvc mockMvc;
   private ObjectMapper objectMapper;
   private RestaurantRequestDto restaurantRequestDto;
   private Restaurant restaurant;
   private RestaurantResponseDto restaurantResponseDto;

   @BeforeEach
   void setUp() {
       mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
       objectMapper = new ObjectMapper();
       objectMapper.registerModule(new JavaTimeModule());

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
               LocalTime.of(8, 0, 0),
               LocalTime.of(18, 0, 0),
               "Observação teste"
       );

       // Setup test data
       restaurantRequestDto = new RestaurantRequestDto(
               "Restaurante Teste",
               "Brasileira",
               "teste@teste.com",
               List.of(addressDto),
               List.of(businessHoursDto)
       );

       restaurant = new Restaurant(
               1L,
               "Restaurante Teste",
               "Brasileira",
               "teste@teste.com",
               "Dono Teste",
               List.of(),
               List.of()
       );

       restaurantResponseDto = new RestaurantResponseDto(
               1L,
               "Restaurante Teste",
               "Dono Teste"
       );
   }

   @Test
   @DisplayName("Should create restaurant successfully")
   void shouldCreateRestaurantSuccessfully() throws Exception {
       // Arrange
       when(restaurantMapper.toRestaurantDomain(any(RestaurantRequestDto.class)))
               .thenReturn(restaurant);
       when(restaurantUseCase.create(any(Restaurant.class)))
               .thenReturn(restaurant);
       when(restaurantMapper.toRestaurantResponseDto(any(Restaurant.class)))
               .thenReturn(restaurantResponseDto);

       // Act & Assert
       mockMvc.perform(post("/restaurants")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(restaurantRequestDto)))
               .andExpect(status().isCreated())
               .andExpect(header().exists("Location"))
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.name").value("Restaurante Teste"))
               .andExpect(jsonPath("$.owner").value("Dono Teste"));

       verify(restaurantMapper, times(1)).toRestaurantDomain(any(RestaurantRequestDto.class));
       verify(restaurantUseCase, times(1)).create(any(Restaurant.class));
       verify(restaurantMapper, times(1)).toRestaurantResponseDto(any(Restaurant.class));
   }

   @Test
   @DisplayName("Should update restaurant successfully")
   void shouldUpdateRestaurantSuccessfully() throws Exception {
       // Arrange
       Long restaurantId = 1L;

       when(restaurantMapper.toRestaurantDomain(any(RestaurantRequestDto.class)))
               .thenReturn(restaurant);
       when(restaurantUseCase.update(eq(restaurantId), any(Restaurant.class)))
               .thenReturn(Optional.of(restaurant));
       when(restaurantMapper.toRestaurantResponseDto(eq(Optional.of(restaurant))))
               .thenReturn(restaurantResponseDto);

       // Act & Assert
       mockMvc.perform(put("/restaurants/{idRestaurant}", restaurantId)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(restaurantRequestDto)))
               .andExpect(status().isAccepted())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.name").value("Restaurante Teste"));

       verify(restaurantMapper, times(1)).toRestaurantDomain(any(RestaurantRequestDto.class));
       verify(restaurantUseCase, times(1)).update(eq(restaurantId), any(Restaurant.class));
       verify(restaurantMapper, times(1)).toRestaurantResponseDto(eq(Optional.of(restaurant)));
   }

   @Test
   @DisplayName("Should return bad request for invalid input")
   void shouldReturnBadRequestForInvalidInput() throws Exception {
       // Arrange
       String invalidJson = "{ \"name\": \"\" }"; // Nome vazio

       // Act & Assert
       mockMvc.perform(post("/restaurants")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(invalidJson))
               .andExpect(status().isBadRequest());

       verify(restaurantUseCase, never()).create(any());
   }

   @Test
   @DisplayName("Should handle update with invalid id")
   void shouldHandleUpdateWithInvalidId() throws Exception {
       // Arrange
       String invalidId = "abc";

       // Act & Assert
       mockMvc.perform(put("/restaurants/{idRestaurant}", invalidId)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(restaurantRequestDto)))
               .andExpect(status().isBadRequest());

       verify(restaurantUseCase, never()).update(any(), any());
   }
}
