package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.MenuNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemsEntity;
import com.br.fiap.fortaleza.sabor.mock.MockMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuItemRepositoryJpaTest {

    @InjectMocks
    MenuItemRepositoryJpa menuItemRepositoryJpa;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private MenuEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        menuItemRepositoryJpa = new MenuItemRepositoryJpa(menuItemRepository,mapper);
    }

    @Test
    @DisplayName("Service JPA - GetAll Menu Items")
    void getAll() {
        // GIVEN
        List<MenuItemsEntity> menuItemsEntities = List.of(MockMenu.menuEntityMockOne(), MockMenu.menuEntityMockTwo());
        List<MenuItem> expectedMenus = List.of(MockMenu.menuItemMockOne(), MockMenu.menuItemMockTwo());

        when(menuItemRepository.findAll()).thenReturn(menuItemsEntities);
        when(mapper.toMenuItemDomain(any(MenuItemsEntity.class))).thenAnswer(invocation -> {
            MenuItemsEntity entity = invocation.getArgument(0);
            return MockMenu.menuItemMockOne();
        });

        // WHEN
        List<MenuItem> response = menuItemRepositoryJpa.getAll();

        // THEN
        assertNotNull(response);
        assertEquals(expectedMenus.size(), response.size());
        verify(menuItemRepository).findAll();
        verify(mapper, times(menuItemsEntities.size())).toMenuItemDomain(any(MenuItemsEntity.class));
    }

    @Test
    @DisplayName("Service JPA - Save a menu item in the database")
    void save() {
        // GIVEN
        MenuItem menuItem = MockMenu.menuItemMockOne();
        MenuItemsEntity menuItemsEntity = MockMenu.menuEntityMockOne();
        MenuItemsEntity savedEntity = MockMenu.menuEntityMockOne();
        MenuItem expectedMenuItem = MockMenu.menuItemMockOne();

        when(mapper.toMenuItemsEntity(menuItem)).thenReturn(menuItemsEntity);
        when(menuItemRepository.save(menuItemsEntity)).thenReturn(savedEntity);
        when(mapper.toMenuItemDomain(savedEntity)).thenReturn(expectedMenuItem);

        // WHEN
        MenuItem response = menuItemRepositoryJpa.save(menuItem);

        // THEN
        assertNotNull(response);
        assertEquals(expectedMenuItem, response);
        verify(mapper).toMenuItemsEntity(menuItem);
        verify(menuItemRepository).save(menuItemsEntity);
        verify(mapper).toMenuItemDomain(savedEntity);
    }

    @Test
    @DisplayName("Should update menu item successfully.")
    void shouldUpdateMenuItemSuccessfully() {
        MenuItemsEntity menuEntity = new MenuItemsEntity(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        MenuItem menuDomain = new MenuItem(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuEntity));
        when(menuItemRepository.save(any())).thenReturn(menuEntity);
        when(mapper.toMenuItemDomain(menuEntity)).thenReturn(menuDomain);

        Optional<MenuItem> updated = menuItemRepositoryJpa.update(1L, menuDomain);

        assertTrue(updated.isPresent());
        assertEquals("Pizza Margherita", updated.get().getName());
    }

    @Test
    @DisplayName("Should throw exception when menuItem is not found for update.")
    void shouldThrowExceptionWhenMenuItemNotFoundToUpdate() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());

        MenuItem menuDomain = new MenuItem(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        RuntimeException exception = assertThrows(MenuNotFoundException.class, () -> menuItemRepositoryJpa.update(1L, menuDomain));
        assertTrue(exception.getMessage().contains("Menu 1 was not found"));
    }

    @Test
    @DisplayName("Should find menuItem by ID.")
    void shouldFindMenuItemById() {
        MenuItemsEntity menuEntity = new MenuItemsEntity(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        MenuItem menuDomain = new MenuItem(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        when(menuItemRepository.findById(2L)).thenReturn(Optional.of(menuEntity));
        when(mapper.toMenuItemDomain(menuEntity)).thenReturn(menuDomain);

        Optional<MenuItem> found = menuItemRepositoryJpa.getById(2L);

        assertTrue(found.isPresent());
        assertEquals("Pizza Margherita", found.get().getName());
    }

    @Test
    @DisplayName("Should delete menuItem by ID")
    void shouldDeleteMenuItemById() {
        MenuItemsEntity menuEntity = new MenuItemsEntity(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        MenuItem menuDomain = new MenuItem(
                "Pizza Margherita",
                "Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco",
                "29.90",
                true,
                "https://exemplo.com/images/pizza-margherita.png"
        );

        when(menuItemRepository.findById(3L)).thenReturn(Optional.of(menuEntity));
        doNothing().when(menuItemRepository).deleteById(3L);
        when(mapper.toMenuItemDomain(menuEntity)).thenReturn(menuDomain);

        Optional<MenuItem> deleted = menuItemRepositoryJpa.deleteById(3L);

        assertTrue(deleted.isPresent());
        assertEquals("Pizza Margherita", deleted.get().getName());
        verify(menuItemRepository).deleteById(3L);
    }
}
