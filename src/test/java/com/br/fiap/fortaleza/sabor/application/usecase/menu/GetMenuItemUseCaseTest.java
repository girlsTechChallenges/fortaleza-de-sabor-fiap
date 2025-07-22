package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GetMenuItemUseCaseTest {

    private MenuItemsRepository menuItemsRepository;
    private GetMenuItemUseCase getMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemsRepository = mock(MenuItemsRepository.class);
        getMenuItemUseCase = new GetMenuItemUseCase(menuItemsRepository);
    }

    @Test
    @DisplayName("Should return all menu items")
    void shouldReturnAllMenuItems() {
        // Arrange
        MenuItem menuItem1 = new MenuItem();
        MenuItem menuItem2 = new MenuItem();
        List<MenuItem> expectedMenuItems = List.of(menuItem1, menuItem2);

        when(menuItemsRepository.getAll()).thenReturn(expectedMenuItems);

        // Act
        List<MenuItem> result = getMenuItemUseCase.getAll();

        // Assert
        assertEquals(expectedMenuItems.size(), result.size());
        assertEquals(expectedMenuItems, result);
        verify(menuItemsRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return menu items by ID.")
    void shouldReturnMenuItemById() {
        // Arrange
        Long menuItemId = 1L;
        MenuItem expectedMenuItem = new MenuItem();
        when(menuItemsRepository.getById(menuItemId)).thenReturn(Optional.of(expectedMenuItem));

        // Act
        Optional<MenuItem> result = getMenuItemUseCase.getById(menuItemId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedMenuItem, result.get());
        verify(menuItemsRepository, times(1)).getById(menuItemId);
    }

    @Test
    @DisplayName("Should return empty when menu item is not found")
    void shouldReturnEmptyWhenMenuItemNotFound() {
        // Arrange
        Long menuItemId = 99L;
        when(menuItemsRepository.getById(menuItemId)).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = getMenuItemUseCase.getById(menuItemId);

        // Assert
        assertTrue(result.isEmpty());
        verify(menuItemsRepository, times(1)).getById(menuItemId);
    }
}
