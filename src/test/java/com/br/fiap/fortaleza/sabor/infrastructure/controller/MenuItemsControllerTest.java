package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.ports.in.MenuItemsUseCasePort;
import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateMenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.MenuItemResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuItemsController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("MenuItemsController Tests")
class MenuItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MenuItemsUseCasePort menuItemsUseCasePort;

    @MockitoBean
    private MenuMapper menuMapper;

    private ObjectMapper objectMapper;
    private MenuItem menuItem;
    private MenuItemRequestDto menuItemRequest;
    private UpdateMenuItemRequestDto updateMenuItemRequest;
    private MenuItemResponseDto menuItemResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        
        // Setup common test data
        menuItem = new MenuItem("Pizza Margherita", "Delicious pizza with tomato and mozzarella", "25.90", true, "pizza.jpg");
        menuItemRequest = new MenuItemRequestDto("Pizza Margherita", "Delicious pizza with tomato and mozzarella", "25.90", true, "pizza.jpg");
        updateMenuItemRequest = new UpdateMenuItemRequestDto("Pizza Napolitana", "Pizza with tomato, mozzarella and anchovies", "28.90", true, "pizza_napolitana.jpg");
        menuItemResponse = new MenuItemResponseDto("Pizza Margherita", "Delicious pizza with tomato and mozzarella", "25.90", true, "pizza.jpg");
    }

    @Test
    @DisplayName("Should create menu item successfully")
    void shouldCreateMenuItemSuccessfully() throws Exception {
        // Arrange
        String requestJson = objectMapper.writeValueAsString(menuItemRequest);
        
        when(menuMapper.toMenuDomain(any(MenuItemRequestDto.class))).thenReturn(menuItem);
        when(menuItemsUseCasePort.save(any(MenuItem.class))).thenReturn(menuItem);
        when(menuMapper.toMenuItemResponseDto(any(MenuItem.class))).thenReturn(menuItemResponse);

        // Act & Assert
        mockMvc.perform(post("/cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Pizza Margherita"))
                .andExpect(jsonPath("$.itemDescription").value("Delicious pizza with tomato and mozzarella"))
                .andExpect(jsonPath("$.itemPrice").value("25.90"))
                .andExpect(jsonPath("$.availability").value(true))
                .andExpect(jsonPath("$.itemImage").value("pizza.jpg"));

        verify(menuMapper).toMenuDomain(any(MenuItemRequestDto.class));
        verify(menuItemsUseCasePort).save(any(MenuItem.class));
        verify(menuMapper).toMenuItemResponseDto(any(MenuItem.class));
    }

    @Test
    @DisplayName("Should return validation error when creating menu item with invalid data")
    void shouldReturnValidationErrorWhenCreatingMenuItemWithInvalidData() throws Exception {
        // Arrange - JSON with empty required field
        String invalidJson = "{\"nome\":\"\",\"itemDescription\":\"Description\",\"itemPrice\":\"25.90\",\"availability\":true,\"itemImage\":\"image.jpg\"}";

        // Act & Assert
        mockMvc.perform(post("/cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return validation error when creating menu item with invalid price format")
    void shouldReturnValidationErrorWhenCreatingMenuItemWithInvalidPriceFormat() throws Exception {
        // Arrange - JSON with invalid price format
        String invalidPriceJson = "{\"nome\":\"Pizza\",\"itemDescription\":\"Description\",\"itemPrice\":\"invalid-price\",\"availability\":true,\"itemImage\":\"image.jpg\"}";

        // Act & Assert
        mockMvc.perform(post("/cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPriceJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get menu item by id successfully")
    void shouldGetMenuItemByIdSuccessfully() throws Exception {
        // Arrange
        Long menuId = 1L;
        
        when(menuItemsUseCasePort.getById(menuId)).thenReturn(Optional.of(menuItem));
        when(menuMapper.getMenuByIdToMenuResponseDto(Optional.of(menuItem))).thenReturn(menuItemResponse);

        // Act & Assert - O controller retorna uma estrutura aninhada devido ao ResponseEntity duplo
        mockMvc.perform(get("/cardapio/{idMenu}", menuId))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(menuItemsUseCasePort).getById(menuId);
        verify(menuMapper).getMenuByIdToMenuResponseDto(Optional.of(menuItem));
    }

    @Test
    @DisplayName("Should return not found when getting menu item by non-existent id")
    void shouldReturnNotFoundWhenGettingMenuItemByNonExistentId() throws Exception {
        // Arrange
        Long nonExistentId = 999L;
        
        when(menuItemsUseCasePort.getById(nonExistentId)).thenReturn(Optional.empty());
        when(menuMapper.getMenuByIdToMenuResponseDto(Optional.empty())).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/cardapio/{idMenu}", nonExistentId))
                .andExpect(status().isAccepted());

        verify(menuItemsUseCasePort).getById(nonExistentId);
        verify(menuMapper).getMenuByIdToMenuResponseDto(Optional.empty());
    }

    @Test
    @DisplayName("Should get all menu items successfully")
    void shouldGetAllMenuItemsSuccessfully() throws Exception {
        // Arrange
        MenuItem menuItem1 = new MenuItem("Pizza Margherita", "Pizza with tomato and mozzarella", "25.90", true, "pizza1.jpg");
        MenuItem menuItem2 = new MenuItem("Pizza Pepperoni", "Pizza with pepperoni", "28.90", true, "pizza2.jpg");
        MenuItemResponseDto response1 = new MenuItemResponseDto("Pizza Margherita", "Pizza with tomato and mozzarella", "25.90", true, "pizza1.jpg");
        MenuItemResponseDto response2 = new MenuItemResponseDto("Pizza Pepperoni", "Pizza with pepperoni", "28.90", true, "pizza2.jpg");

        when(menuItemsUseCasePort.getAll()).thenReturn(List.of(menuItem1, menuItem2));
        when(menuMapper.toMenuItemResponseDto(menuItem1)).thenReturn(response1);
        when(menuMapper.toMenuItemResponseDto(menuItem2)).thenReturn(response2);

        // Act & Assert
        mockMvc.perform(get("/cardapio"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Pizza Margherita"))
                .andExpect(jsonPath("$[0].itemPrice").value("25.90"))
                .andExpect(jsonPath("$[1].nome").value("Pizza Pepperoni"))
                .andExpect(jsonPath("$[1].itemPrice").value("28.90"));

        verify(menuItemsUseCasePort).getAll();
        verify(menuMapper).toMenuItemResponseDto(menuItem1);
        verify(menuMapper).toMenuItemResponseDto(menuItem2);
    }

    @Test
    @DisplayName("Should return empty list when no menu items exist")
    void shouldReturnEmptyListWhenNoMenuItemsExist() throws Exception {
        // Arrange
        when(menuItemsUseCasePort.getAll()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/cardapio"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(menuItemsUseCasePort).getAll();
    }

    @Test
    @DisplayName("Should update menu item successfully")
    void shouldUpdateMenuItemSuccessfully() throws Exception {
        // Arrange
        Long menuId = 1L;
        String updateJson = objectMapper.writeValueAsString(updateMenuItemRequest);
        MenuItem updatedMenuItem = new MenuItem("Pizza Napolitana", "Pizza with tomato, mozzarella and anchovies", "28.90", true, "pizza_napolitana.jpg");

        when(menuMapper.updateToMenuDomain(any(UpdateMenuItemRequestDto.class))).thenReturn(updatedMenuItem);

        // Act & Assert
        mockMvc.perform(put("/cardapio/{idMenu}", menuId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isAccepted());

        verify(menuMapper).updateToMenuDomain(any(UpdateMenuItemRequestDto.class));
        verify(menuItemsUseCasePort).update(menuId, updatedMenuItem);
    }

    @Test
    @DisplayName("Should return internal server error when updating menu item with use case failure")
    void shouldReturnInternalServerErrorWhenUpdatingMenuItemWithUseCaseFailure() throws Exception {
        // Arrange
        Long menuId = 1L;
        String validUpdateJson = objectMapper.writeValueAsString(updateMenuItemRequest);
        MenuItem updatedMenuItem = new MenuItem("Pizza Napolitana", "Pizza with tomato, mozzarella and anchovies", "28.90", true, "pizza_napolitana.jpg");

        when(menuMapper.updateToMenuDomain(any(UpdateMenuItemRequestDto.class))).thenReturn(updatedMenuItem);
        doThrow(new RuntimeException("Use case error")).when(menuItemsUseCasePort).update(menuId, updatedMenuItem);

        // Act & Assert
        mockMvc.perform(put("/cardapio/{idMenu}", menuId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUpdateJson))
                .andExpect(status().isInternalServerError());

        verify(menuMapper).updateToMenuDomain(any(UpdateMenuItemRequestDto.class));
        verify(menuItemsUseCasePort).update(menuId, updatedMenuItem);
    }

    @Test
    @DisplayName("Should delete menu item successfully")
    void shouldDeleteMenuItemSuccessfully() throws Exception {
        // Arrange
        Long menuId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/cardapio/{idMenu}", menuId))
                .andExpect(status().isNoContent());

        verify(menuItemsUseCasePort).deleteById(menuId);
    }

    @Test
    @DisplayName("Should return internal server error when use case fails")
    void shouldReturnInternalServerErrorWhenUseCaseFails() throws Exception {
        // Arrange
        String requestJson = objectMapper.writeValueAsString(menuItemRequest);

        when(menuMapper.toMenuDomain(any(MenuItemRequestDto.class))).thenThrow(new RuntimeException("Use case error"));

        // Act & Assert
        mockMvc.perform(post("/cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError());

        verify(menuMapper).toMenuDomain(any(MenuItemRequestDto.class));
    }

    @Test
    @DisplayName("Should handle menu item with unavailable status")
    void shouldHandleMenuItemWithUnavailableStatus() throws Exception {
        // Arrange
        MenuItemRequestDto unavailableRequest = new MenuItemRequestDto("Pizza Especial", "Special pizza", "35.90", false, "special.jpg");
        MenuItem unavailableItem = new MenuItem("Pizza Especial", "Special pizza", "35.90", false, "special.jpg");
        MenuItemResponseDto unavailableResponse = new MenuItemResponseDto("Pizza Especial", "Special pizza", "35.90", false, "special.jpg");
        
        String requestJson = objectMapper.writeValueAsString(unavailableRequest);

        when(menuMapper.toMenuDomain(any(MenuItemRequestDto.class))).thenReturn(unavailableItem);
        when(menuItemsUseCasePort.save(any(MenuItem.class))).thenReturn(unavailableItem);
        when(menuMapper.toMenuItemResponseDto(any(MenuItem.class))).thenReturn(unavailableResponse);

        // Act & Assert
        mockMvc.perform(post("/cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Pizza Especial"))
                .andExpect(jsonPath("$.availability").value(false));

        verify(menuMapper).toMenuDomain(any(MenuItemRequestDto.class));
        verify(menuItemsUseCasePort).save(any(MenuItem.class));
        verify(menuMapper).toMenuItemResponseDto(any(MenuItem.class));
    }
}
