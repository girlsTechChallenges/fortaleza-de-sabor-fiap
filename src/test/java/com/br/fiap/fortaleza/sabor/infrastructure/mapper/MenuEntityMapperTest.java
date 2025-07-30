package com.br.fiap.fortaleza.sabor.infrastructure.mapper;


import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateMenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.MenuItemResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MenuEntityMapperTest {
    private MenuEntityMapper menuEntityMapper;

    @BeforeEach
    void setUp() {
        menuEntityMapper = new MenuEntityMapper();
    }

    @Test
    @DisplayName("Should map UpdateMenuItemRequestDto to MenuItem correctly")
    void shouldMapUpdateMenuItemRequestDtoToMenuItem() {
        MenuItemRequestDto dto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        MenuItem menuItem = menuEntityMapper.toMenuDomain(dto);

        assertNotNull(menuItem);
        assertEquals("Pizza Margherita", menuItem.getName());
        assertEquals("Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco", menuItem.getItemDescription());
        assertEquals("29.90", menuItem.getItemPrice());
        assertTrue(menuItem.getAvailability());
        assertEquals("https://exemplo.com/images/pizza-margherita.png", menuItem.getItemImage());
    }

    @Test
    @DisplayName("Should map UpdateRequestDto to MenuItem.")
    void shouldMapUpdateRequestDtoToMenuItem() {
        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        MenuItem menuItem = menuEntityMapper.updateToMenuDomain(dto);

        assertNotNull(menuItem);
        assertEquals("Pizza Margherita", menuItem.getName());
        assertEquals("Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco", menuItem.getItemDescription());
        assertEquals("29.90", menuItem.getItemPrice());
        assertEquals(true, menuItem.getAvailability());
        assertEquals("https://exemplo.com/images/pizza-margherita.png", menuItem.getItemImage());
    }

    @Test
    @DisplayName("Should map MenuItem to MenuItemEntity")
    void shouldMapMenuItemToMenuItemEntity() {
        MenuItem menuItem = new MenuItem(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        MenuItemsEntity entity = menuEntityMapper.toMenuItemsEntity(menuItem);

        assertEquals(menuItem.getName(), entity.getName());
    }

    @Test
    @DisplayName("Should map MenuItemEntity to MenuItem.")
    void shouldMapMenuItemEntityToMenuItem() {
        MenuItemsEntity entity = new MenuItemsEntity(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        MenuItem user = menuEntityMapper.toMenuItemDomain(entity);

        assertEquals("Pizza Margherita", user.getName());
    }

    @Test
    @DisplayName("Should map MenuItem to MenuItemResponseDto")
    void shouldMapMenuItemToMenuItemResponseDto() {
        MenuItem entity = new MenuItem(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        MenuItemResponseDto dto = menuEntityMapper.toMenuItemResponseDto(entity);

        assertEquals("Pizza Margherita", dto.name());
    }

    @Test
    @DisplayName("Should map optional MenuItem to MenuItemResponseDto.")
    void shouldMapOptionalMenuItemToMenuItemResponseDto() {
        MenuItem menuItem = new MenuItem(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        MenuItemResponseDto dto = menuEntityMapper.getMenuByIdToMenuResponseDto(Optional.of(menuItem));

        assertEquals("Pizza Margherita", dto.nome());
    }

    @Test
    @DisplayName("Should throw when optional MenuItem is empty.")
    void shouldThrowWhenOptionalMenuItemIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> menuEntityMapper.getMenuByIdToMenuResponseDto(Optional.empty()));
    }
}
