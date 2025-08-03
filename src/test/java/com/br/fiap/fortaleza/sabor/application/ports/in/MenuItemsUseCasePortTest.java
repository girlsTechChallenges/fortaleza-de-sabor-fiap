package com.br.fiap.fortaleza.sabor.application.ports.in;

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
@DisplayName("MenuItemsUseCasePort Contract Tests")
class MenuItemsUseCasePortTest {

    @Mock
    private MenuItemsUseCasePort menuItemsUseCasePort;

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
        when(menuItemsUseCasePort.getAll()).thenReturn(expectedItems);

        // Act
        List<MenuItem> result = menuItemsUseCasePort.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(menuItem1, menuItem2);
        verify(menuItemsUseCasePort).getAll();
    }

    @Test
    @DisplayName("Should save menu item successfully")
    void shouldSaveMenuItem() {
        // Arrange
        when(menuItemsUseCasePort.save(any(MenuItem.class))).thenReturn(menuItem1);

        // Act
        MenuItem result = menuItemsUseCasePort.save(menuItem1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo("Pizza Margherita");
        assertThat(result.getItemDescription()).isEqualTo("Pizza com molho de tomate e mussarela");
        assertThat(result.getItemPrice()).isEqualTo("25.90");
        verify(menuItemsUseCasePort).save(menuItem1);
    }

    @Test
    @DisplayName("Should update menu item successfully")
    void shouldUpdateMenuItem() {
        // Arrange
        when(menuItemsUseCasePort.update(anyLong(), any(MenuItem.class))).thenReturn(Optional.of(menuItem1));

        // Act
        Optional<MenuItem> result = menuItemsUseCasePort.update(1L, menuItem1);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNome()).isEqualTo("Pizza Margherita");
        verify(menuItemsUseCasePort).update(1L, menuItem1);
    }

    @Test
    @DisplayName("Should return empty when updating non-existent menu item")
    void shouldReturnEmptyWhenUpdatingNonExistentMenuItem() {
        // Arrange
        when(menuItemsUseCasePort.update(anyLong(), any(MenuItem.class))).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = menuItemsUseCasePort.update(999L, menuItem1);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemsUseCasePort).update(999L, menuItem1);
    }

    @Test
    @DisplayName("Should get menu item by id successfully")
    void shouldGetMenuItemById() {
        // Arrange
        when(menuItemsUseCasePort.getById(anyLong())).thenReturn(Optional.of(menuItem1));

        // Act
        Optional<MenuItem> result = menuItemsUseCasePort.getById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNome()).isEqualTo("Pizza Margherita");
        verify(menuItemsUseCasePort).getById(1L);
    }

    @Test
    @DisplayName("Should return empty when getting non-existent menu item by id")
    void shouldReturnEmptyWhenGettingNonExistentMenuItemById() {
        // Arrange
        when(menuItemsUseCasePort.getById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = menuItemsUseCasePort.getById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemsUseCasePort).getById(999L);
    }

    @Test
    @DisplayName("Should delete menu item by id successfully")
    void shouldDeleteMenuItemById() {
        // Arrange
        when(menuItemsUseCasePort.deleteById(anyLong())).thenReturn(Optional.of(menuItem1));

        // Act
        Optional<MenuItem> result = menuItemsUseCasePort.deleteById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNome()).isEqualTo("Pizza Margherita");
        verify(menuItemsUseCasePort).deleteById(1L);
    }

    @Test
    @DisplayName("Should return empty when deleting non-existent menu item")
    void shouldReturnEmptyWhenDeletingNonExistentMenuItem() {
        // Arrange
        when(menuItemsUseCasePort.deleteById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = menuItemsUseCasePort.deleteById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemsUseCasePort).deleteById(999L);
    }
}
