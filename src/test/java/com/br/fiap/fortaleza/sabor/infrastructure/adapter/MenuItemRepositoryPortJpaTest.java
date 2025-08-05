package com.br.fiap.fortaleza.sabor.infrastructure.adapter;

import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.MenuAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.MenuNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemsEntity;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MenuItemRepositoryPortJpa Tests")
class MenuItemRepositoryPortJpaTest {

    @Mock
    private MenuItemRepositoryAdapter menuItemRepositoryAdapter;

    @Mock
    private MenuMapper mapper;

    @InjectMocks
    private MenuItemRepositoryPortJpa menuItemRepositoryPortJpa;

    private MenuItem menuItem;
    private MenuItemsEntity menuItemEntity;
    private MenuItemsEntity menuItemEntity2;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem("Pizza Margherita", "Pizza com molho de tomate e mussarela", "25.90", true, "pizza.jpg");
        
        menuItemEntity = new MenuItemsEntity();
        menuItemEntity.setIdItemCardapio(1L);
        menuItemEntity.setNome("Pizza Margherita");
        menuItemEntity.setItemDescription("Pizza com molho de tomate e mussarela");
        menuItemEntity.setItemPrice("25.90");
        menuItemEntity.setAvailability(true);
        menuItemEntity.setItemImage("pizza.jpg");

        menuItemEntity2 = new MenuItemsEntity();
        menuItemEntity2.setIdItemCardapio(2L);
        menuItemEntity2.setNome("Hambúrguer");
        menuItemEntity2.setItemDescription("Hambúrguer artesanal");
        menuItemEntity2.setItemPrice("18.50");
        menuItemEntity2.setAvailability(true);
        menuItemEntity2.setItemImage("hamburguer.jpg");
    }

    @Test
    @DisplayName("Should return all menu items when getAll is called")
    void shouldReturnAllMenuItems() {
        // Arrange
        List<MenuItemsEntity> entities = Arrays.asList(menuItemEntity, menuItemEntity2);
        MenuItem menuItem2 = new MenuItem("Hambúrguer", "Hambúrguer artesanal", "18.50", true, "hamburguer.jpg");
        
        when(menuItemRepositoryAdapter.findAll()).thenReturn(entities);
        when(mapper.toMenuItemDomain(menuItemEntity)).thenReturn(menuItem);
        when(mapper.toMenuItemDomain(menuItemEntity2)).thenReturn(menuItem2);

        // Act
        List<MenuItem> result = menuItemRepositoryPortJpa.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(menuItemRepositoryAdapter).findAll();
        verify(mapper, times(2)).toMenuItemDomain(any(MenuItemsEntity.class));
    }

    @Test
    @DisplayName("Should save menu item successfully when item does not exist")
    void shouldSaveMenuItemSuccessfully() {
        // Arrange
        when(menuItemRepositoryAdapter.findByName(anyString())).thenReturn(Optional.empty());
        when(mapper.toMenuItemsEntity(any(MenuItem.class))).thenReturn(menuItemEntity);
        when(menuItemRepositoryAdapter.save(any(MenuItemsEntity.class))).thenReturn(menuItemEntity);
        when(mapper.toMenuItemDomain(any(MenuItemsEntity.class))).thenReturn(menuItem);

        // Act
        MenuItem result = menuItemRepositoryPortJpa.save(menuItem);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pizza Margherita");
        verify(menuItemRepositoryAdapter).findByName("Pizza Margherita");
        verify(mapper).toMenuItemsEntity(menuItem);
        verify(menuItemRepositoryAdapter).save(menuItemEntity);
        verify(mapper).toMenuItemDomain(menuItemEntity);
    }

    @Test
    @DisplayName("Should throw MenuAlreadyRegisteredException when trying to save existing menu item")
    void shouldThrowExceptionWhenSavingExistingMenuItem() {
        // Arrange
        when(menuItemRepositoryAdapter.findByName(anyString())).thenReturn(Optional.of(menuItemEntity));

        // Act & Assert
        assertThatThrownBy(() -> menuItemRepositoryPortJpa.save(menuItem))
                .isInstanceOf(MenuAlreadyRegisteredException.class)
                .hasMessage("This item already exists.");

        verify(menuItemRepositoryAdapter).findByName("Pizza Margherita");
        verify(mapper, never()).toMenuItemsEntity(any());
        verify(menuItemRepositoryAdapter, never()).save(any());
    }

    @Test
    @DisplayName("Should update menu item successfully when item exists")
    void shouldUpdateMenuItemSuccessfully() {
        // Arrange
        MenuItem updatedMenuItem = new MenuItem("Pizza Margherita Especial", "Pizza com molho especial", "29.90", true, "pizza_especial.jpg");
        
        when(menuItemRepositoryAdapter.findById(anyLong())).thenReturn(Optional.of(menuItemEntity));
        when(menuItemRepositoryAdapter.save(any(MenuItemsEntity.class))).thenReturn(menuItemEntity);
        when(mapper.toMenuItemDomain(any(MenuItemsEntity.class))).thenReturn(updatedMenuItem);

        // Act
        Optional<MenuItem> result = menuItemRepositoryPortJpa.update(1L, updatedMenuItem);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Pizza Margherita Especial");
        verify(menuItemRepositoryAdapter).findById(1L);
        verify(menuItemRepositoryAdapter).save(menuItemEntity);
        verify(mapper).toMenuItemDomain(menuItemEntity);
    }

    @Test
    @DisplayName("Should throw MenuNotFoundException when trying to update non-existent menu item")
    void shouldThrowExceptionWhenUpdatingNonExistentMenuItem() {
        // Arrange
        when(menuItemRepositoryAdapter.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> menuItemRepositoryPortJpa.update(999L, menuItem))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Menu 999 was not found");

        verify(menuItemRepositoryAdapter).findById(999L);
        verify(menuItemRepositoryAdapter, never()).save(any());
    }

    @Test
    @DisplayName("Should get menu item by id successfully when item exists")
    void shouldGetMenuItemByIdSuccessfully() {
        // Arrange
        when(menuItemRepositoryAdapter.findById(anyLong())).thenReturn(Optional.of(menuItemEntity));
        when(mapper.toMenuItemDomain(any(MenuItemsEntity.class))).thenReturn(menuItem);

        // Act
        Optional<MenuItem> result = menuItemRepositoryPortJpa.getById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Pizza Margherita");
        verify(menuItemRepositoryAdapter).findById(1L);
        verify(mapper).toMenuItemDomain(menuItemEntity);
    }

    @Test
    @DisplayName("Should throw MenuNotFoundException when trying to get non-existent menu item by id")
    void shouldThrowExceptionWhenGettingNonExistentMenuItemById() {
        // Arrange
        when(menuItemRepositoryAdapter.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> menuItemRepositoryPortJpa.getById(999L))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Menu999was not found");

        verify(menuItemRepositoryAdapter).findById(999L);
        verify(mapper, never()).toMenuItemDomain(any());
    }

    @Test
    @DisplayName("Should delete menu item by id successfully when item exists")
    void shouldDeleteMenuItemByIdSuccessfully() {
        // Arrange
        when(menuItemRepositoryAdapter.findById(anyLong())).thenReturn(Optional.of(menuItemEntity));
        when(mapper.toMenuItemDomain(any(MenuItemsEntity.class))).thenReturn(menuItem);

        // Act
        Optional<MenuItem> result = menuItemRepositoryPortJpa.deleteById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Pizza Margherita");
        verify(menuItemRepositoryAdapter, times(2)).findById(1L);
        verify(menuItemRepositoryAdapter).deleteById(1L);
        verify(mapper).toMenuItemDomain(menuItemEntity);
    }

    @Test
    @DisplayName("Should throw MenuNotFoundException when trying to delete non-existent menu item")
    void shouldThrowExceptionWhenDeletingNonExistentMenuItem() {
        // Arrange
        when(menuItemRepositoryAdapter.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> menuItemRepositoryPortJpa.deleteById(999L))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Menu999was not found");

        verify(menuItemRepositoryAdapter).findById(999L);
        verify(menuItemRepositoryAdapter, never()).deleteById(anyLong());
    }
}
