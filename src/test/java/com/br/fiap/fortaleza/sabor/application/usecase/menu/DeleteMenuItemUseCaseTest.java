package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMenuItemUseCaseTest {
    private MenuItemsRepository menuItemsRepository;
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemsRepository = mock(MenuItemsRepository.class);
        deleteMenuItemUseCase = new DeleteMenuItemUseCase(menuItemsRepository);
    }

    @Test
    @DisplayName("Should delete menu item by ID successfully.")
    void shouldDeleteMenuItemByIdSuccessfully() {
        Long menuItemId = 1L;
        MenuItem mockMenuItem = new MenuItem();
        Optional<MenuItem> expected = Optional.of(mockMenuItem);

        when(menuItemsRepository.deleteById(menuItemId)).thenReturn(expected);

        // Act
        Optional<MenuItem> result = deleteMenuItemUseCase.delete(menuItemId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result);
        verify(menuItemsRepository, times(1)).deleteById(menuItemId);
    }

    @Test
    @DisplayName("Should return empty when menu item is not found.")
    void shouldReturnEmptyWhenMenuItemNotFound() {
        // Arrange
        Long menuItemId = 2L;
        when(menuItemsRepository.deleteById(menuItemId)).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = deleteMenuItemUseCase.delete(menuItemId);

        // Assert
        assertTrue(result.isEmpty());
        verify(menuItemsRepository, times(1)).deleteById(menuItemId);
    }
}
