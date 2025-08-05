package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.ports.out.MenuItemsRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MenuItemUseCase Tests")
class MenuItemUseCaseTest {

    @Mock
    private MenuItemsRepositoryPort menuItemsRepositoryPort;

    @InjectMocks
    private MenuItemUseCase menuItemUseCase;

    private MenuItem menuItem;
    private MenuItem anotherMenuItem;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem();
        menuItem.setName("Pizza Margherita");
        menuItem.setItemDescription("Traditional Italian pizza with tomato, mozzarella and basil");
        menuItem.setItemPrice("29.90");
        menuItem.setAvailability(true);

        anotherMenuItem = new MenuItem();
        anotherMenuItem.setName("Pasta Carbonara");
        anotherMenuItem.setItemDescription("Classic pasta with eggs, cheese, pancetta and pepper");
        anotherMenuItem.setItemPrice("24.50");
        anotherMenuItem.setAvailability(true);
    }

    @Test
    @DisplayName("Should return all menu items when getAll is called")
    void shouldReturnAllMenuItemsWhenGetAllIsCalled() {
        // Arrange
        List<MenuItem> expectedMenuItems = Arrays.asList(menuItem, anotherMenuItem);
        when(menuItemsRepositoryPort.getAll()).thenReturn(expectedMenuItems);

        // Act
        List<MenuItem> actualMenuItems = menuItemUseCase.getAll();

        // Assert
        assertThat(actualMenuItems).isNotNull();
        assertThat(actualMenuItems).hasSize(2);
        assertThat(actualMenuItems).containsExactly(menuItem, anotherMenuItem);
        verify(menuItemsRepositoryPort, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return empty list when no menu items exist")
    void shouldReturnEmptyListWhenNoMenuItemsExist() {
        // Arrange
        when(menuItemsRepositoryPort.getAll()).thenReturn(Arrays.asList());

        // Act
        List<MenuItem> actualMenuItems = menuItemUseCase.getAll();

        // Assert
        assertThat(actualMenuItems).isNotNull();
        assertThat(actualMenuItems).isEmpty();
        verify(menuItemsRepositoryPort, times(1)).getAll();
    }

    @Test
    @DisplayName("Should save menu item when save is called")
    void shouldSaveMenuItemWhenSaveIsCalled() {
        // Arrange
        when(menuItemsRepositoryPort.save(any(MenuItem.class))).thenReturn(menuItem);

        // Act
        MenuItem savedMenuItem = menuItemUseCase.save(menuItem);

        // Assert
        assertThat(savedMenuItem).isNotNull();
        assertThat(savedMenuItem.getName()).isEqualTo("Pizza Margherita");
        assertThat(savedMenuItem.getItemDescription()).isEqualTo("Traditional Italian pizza with tomato, mozzarella and basil");
        assertThat(savedMenuItem.getItemPrice()).isEqualTo("29.90");
        assertThat(savedMenuItem.getAvailability()).isTrue();
        verify(menuItemsRepositoryPort, times(1)).save(menuItem);
    }

    @Test
    @DisplayName("Should update menu item when update is called with valid id")
    void shouldUpdateMenuItemWhenUpdateIsCalledWithValidId() {
        // Arrange
        Long menuItemId = 1L;
        MenuItem updatedMenuItem = new MenuItem();
        updatedMenuItem.setName("Updated Pizza");
        updatedMenuItem.setItemDescription("Updated description");
        updatedMenuItem.setItemPrice("35.90");
        updatedMenuItem.setAvailability(false);

        when(menuItemsRepositoryPort.update(menuItemId, menuItem)).thenReturn(Optional.of(updatedMenuItem));

        // Act
        Optional<MenuItem> result = menuItemUseCase.update(menuItemId, menuItem);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Updated Pizza");
        assertThat(result.get().getItemDescription()).isEqualTo("Updated description");
        assertThat(result.get().getItemPrice()).isEqualTo("35.90");
        assertThat(result.get().getAvailability()).isFalse();
        verify(menuItemsRepositoryPort, times(1)).update(menuItemId, menuItem);
    }

    @Test
    @DisplayName("Should return empty optional when update is called with invalid id")
    void shouldReturnEmptyOptionalWhenUpdateIsCalledWithInvalidId() {
        // Arrange
        Long invalidMenuItemId = 999L;
        when(menuItemsRepositoryPort.update(invalidMenuItemId, menuItem)).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = menuItemUseCase.update(invalidMenuItemId, menuItem);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemsRepositoryPort, times(1)).update(invalidMenuItemId, menuItem);
    }

    @Test
    @DisplayName("Should return menu item when getById is called with valid id")
    void shouldReturnMenuItemWhenGetByIdIsCalledWithValidId() {
        // Arrange
        Long menuItemId = 1L;
        when(menuItemsRepositoryPort.getById(menuItemId)).thenReturn(Optional.of(menuItem));

        // Act
        Optional<MenuItem> result = menuItemUseCase.getById(menuItemId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(menuItem);
        assertThat(result.get().getName()).isEqualTo("Pizza Margherita");
        verify(menuItemsRepositoryPort, times(1)).getById(menuItemId);
    }

    @Test
    @DisplayName("Should return empty optional when getById is called with invalid id")
    void shouldReturnEmptyOptionalWhenGetByIdIsCalledWithInvalidId() {
        // Arrange
        Long invalidMenuItemId = 999L;
        when(menuItemsRepositoryPort.getById(invalidMenuItemId)).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = menuItemUseCase.getById(invalidMenuItemId);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemsRepositoryPort, times(1)).getById(invalidMenuItemId);
    }

    @Test
    @DisplayName("Should delete menu item when deleteById is called with valid id")
    void shouldDeleteMenuItemWhenDeleteByIdIsCalledWithValidId() {
        // Arrange
        Long menuItemId = 1L;
        when(menuItemsRepositoryPort.deleteById(menuItemId)).thenReturn(Optional.of(menuItem));

        // Act
        Optional<MenuItem> result = menuItemUseCase.deleteById(menuItemId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(menuItem);
        verify(menuItemsRepositoryPort, times(1)).deleteById(menuItemId);
    }

    @Test
    @DisplayName("Should return empty optional when deleteById is called with invalid id")
    void shouldReturnEmptyOptionalWhenDeleteByIdIsCalledWithInvalidId() {
        // Arrange
        Long invalidMenuItemId = 999L;
        when(menuItemsRepositoryPort.deleteById(invalidMenuItemId)).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = menuItemUseCase.deleteById(invalidMenuItemId);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemsRepositoryPort, times(1)).deleteById(invalidMenuItemId);
    }

    @Test
    @DisplayName("Should handle null menu item gracefully when save is called")
    void shouldHandleNullMenuItemGracefullyWhenSaveIsCalled() {
        // Arrange
        MenuItem nullMenuItem = null;
        when(menuItemsRepositoryPort.save(nullMenuItem)).thenReturn(null);

        // Act
        MenuItem result = menuItemUseCase.save(nullMenuItem);

        // Assert
        assertThat(result).isNull();
        verify(menuItemsRepositoryPort, times(1)).save(nullMenuItem);
    }
}
