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
class UpdateMenuItemUseCaseTest {
    private MenuItemsRepository menuItemsRepository;
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemsRepository = mock(MenuItemsRepository.class);
        updateMenuItemUseCase = new UpdateMenuItemUseCase(menuItemsRepository);
    }

    @Test
    @DisplayName("Should update menu item successfully.")
    void shouldUpdateMenuItemSuccessfully() {
        // Arrange
        Long menuItemId = 1L;
        MenuItem menuItemToUpdate = new MenuItem();
        MenuItem updatedMenuItem = new MenuItem();
        Optional<MenuItem> expected = Optional.of(updatedMenuItem);

        when(menuItemsRepository.update(menuItemId, menuItemToUpdate)).thenReturn(expected);

        // Act
        Optional<MenuItem> result = updateMenuItemUseCase.update(menuItemId, menuItemToUpdate);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result);
        verify(menuItemsRepository, times(1)).update(menuItemId, menuItemToUpdate);
    }

    @Test
    @DisplayName("Should return empty when menu item is not found.")
    void shouldReturnEmptyWhenMenuItemNotFound() {
        // Arrange
        Long menuItemId = 2L;
        MenuItem menuItemToUpdate = new MenuItem();
        when(menuItemsRepository.update(menuItemId, menuItemToUpdate)).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = updateMenuItemUseCase.update(menuItemId, menuItemToUpdate);

        // Assert
        assertTrue(result.isEmpty());
        verify(menuItemsRepository, times(1)).update(menuItemId, menuItemToUpdate);
    }
}
