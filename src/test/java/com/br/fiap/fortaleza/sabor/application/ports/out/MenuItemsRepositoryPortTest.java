package com.br.fiap.fortaleza.sabor.application.ports.out;

import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("MenuItemsRepositoryPort Contract Tests")
class MenuItemsRepositoryPortTest {

    @Mock
    private MenuItemsRepositoryPort menuItemsRepositoryPort;

    private MenuItem menuItem1;
    private MenuItem menuItem2;

    @BeforeEach
    void setUp() {
        menuItem1 = new MenuItem("Pizza Margherita", "Pizza com molho de tomate e mussarela", "25.90", true, "pizza.jpg");
        menuItem2 = new MenuItem("Hambúrguer", "Hambúrguer artesanal com batata frita", "18.50", true, "hamburguer.jpg");
    }

    @Test
    @DisplayName("Should return all menu items when getAll is called")
    void shouldReturnAllMenuItems() {
        // Arrange
        List<MenuItem> expectedItems = Arrays.asList(menuItem1, menuItem2);
        when(menuItemsRepositoryPort.getAll()).thenReturn(expectedItems);

        // Act
        List<MenuItem> result = menuItemsRepositoryPort.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(menuItem1, menuItem2);
        verify(menuItemsRepositoryPort).getAll();
    }

    @Test
    @DisplayName("Should save menu item successfully")
    void shouldSaveMenuItem() {
        // Arrange
        when(menuItemsRepositoryPort.save(any(MenuItem.class))).thenReturn(menuItem1);

        // Act
        MenuItem result = menuItemsRepositoryPort.save(menuItem1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pizza Margherita");
        assertThat(result.getItemDescription()).isEqualTo("Pizza com molho de tomate e mussarela");
        assertThat(result.getItemPrice()).isEqualTo("25.90");
        verify(menuItemsRepositoryPort).save(menuItem1);
    }

    @Test
    @DisplayName("Should update menu item successfully")
    void shouldUpdateMenuItem() {
        // Arrange
        when(menuItemsRepositoryPort.update(anyLong(), any(MenuItem.class))).thenReturn(Optional.of(menuItem1));

        // Act
        Optional<MenuItem> result = menuItemsRepositoryPort.update(1L, menuItem1);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Pizza Margherita");
        verify(menuItemsRepositoryPort).update(1L, menuItem1);
    }

    @Test
    @DisplayName("Should return empty when updating non-existent menu item")
    void shouldReturnEmptyWhenUpdatingNonExistentMenuItem() {
        // Arrange
        when(menuItemsRepositoryPort.update(anyLong(), any(MenuItem.class))).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = menuItemsRepositoryPort.update(999L, menuItem1);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemsRepositoryPort).update(999L, menuItem1);
    }

    @Test
    @DisplayName("Should get menu item by id successfully")
    void shouldGetMenuItemById() {
        // Arrange
        when(menuItemsRepositoryPort.getById(anyLong())).thenReturn(Optional.of(menuItem1));

        // Act
        Optional<MenuItem> result = menuItemsRepositoryPort.getById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Pizza Margherita");
        verify(menuItemsRepositoryPort).getById(1L);
    }

    @Test
    @DisplayName("Should return empty when getting non-existent menu item by id")
    void shouldReturnEmptyWhenGettingNonExistentMenuItemById() {
        // Arrange
        when(menuItemsRepositoryPort.getById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = menuItemsRepositoryPort.getById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemsRepositoryPort).getById(999L);
    }

    @Test
    @DisplayName("Should delete menu item by id successfully")
    void shouldDeleteMenuItemById() {
        // Arrange
        when(menuItemsRepositoryPort.deleteById(anyLong())).thenReturn(Optional.of(menuItem1));

        // Act
        Optional<MenuItem> result = menuItemsRepositoryPort.deleteById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Pizza Margherita");
        verify(menuItemsRepositoryPort).deleteById(1L);
    }

    @Test
    @DisplayName("Should return empty when deleting non-existent menu item")
    void shouldReturnEmptyWhenDeletingNonExistentMenuItem() {
        // Arrange
        when(menuItemsRepositoryPort.deleteById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = menuItemsRepositoryPort.deleteById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemsRepositoryPort).deleteById(999L);
    }
}
