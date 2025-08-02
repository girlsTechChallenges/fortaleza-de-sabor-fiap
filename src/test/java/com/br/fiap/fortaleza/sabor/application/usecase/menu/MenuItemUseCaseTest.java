package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.ports.out.MenuItemsRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateMenuItemUseCase Tests")
class MenuItemUseCaseTest {

    @InjectMocks
    private MenuItemUseCase menuItemUseCase;

    @Mock
    private MenuItemsRepositoryPort menuItemsRepositoryPort;

    @Test
    @DisplayName("Should create menu item successfully when valid item provided")
    void shouldCreateMenuItemSuccessfullyWhenValidItemProvided() {
        // Arrange
        MenuItem menuItemToCreate = TestDataBuilder.createValidMenuItem();
        MenuItem savedMenuItem = TestDataBuilder.createValidMenuItem();

        when(menuItemsRepositoryPort.save(any(MenuItem.class))).thenReturn(savedMenuItem);

        // Act
        MenuItem result = menuItemUseCase.save(menuItemToCreate);

        // Assert
        assertNotNull(result, "Created menu item should not be null");
        assertEquals(savedMenuItem.getNome(), result.getNome(), "Menu item name should match");
        assertEquals(savedMenuItem.getItemDescription(), result.getItemDescription(), "Menu item description should match");
        assertEquals(savedMenuItem.getItemPrice(), result.getItemPrice(), "Menu item price should match");
        assertEquals(savedMenuItem.getAvailability(), result.getAvailability(), "Menu item availability should match");
        assertEquals(savedMenuItem.getItemImage(), result.getItemImage(), "Menu item image should match");
        
        verify(menuItemsRepositoryPort, times(1)).save(menuItemToCreate);
        verifyNoMoreInteractions(menuItemsRepositoryPort);
    }

    @Test
    @DisplayName("Should throw exception when repository fails to save menu item")
    void shouldThrowExceptionWhenRepositoryFailsToSaveMenuItem() {
        // Arrange
        MenuItem menuItemToCreate = TestDataBuilder.createValidMenuItem();
        String errorMessage = "Database connection failed";
        
        when(menuItemsRepositoryPort.save(any(MenuItem.class)))
            .thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> menuItemUseCase.save(menuItemToCreate),
            "Should throw RuntimeException when repository fails"
        );
        
        assertEquals(errorMessage, exception.getMessage());
        verify(menuItemsRepositoryPort, times(1)).save(menuItemToCreate);
        verifyNoMoreInteractions(menuItemsRepositoryPort);
    }

    @Test
    @DisplayName("Should handle null menu item gracefully")
    void shouldHandleNullMenuItemGracefully() {
        // Arrange
        MenuItem nullMenuItem = null;

        when(menuItemsRepositoryPort.save(isNull())).thenThrow(new NullPointerException("Menu item cannot be null"));

        // Act & Assert
        assertThrows(NullPointerException.class,
            () -> menuItemUseCase.save(nullMenuItem),
            "Should throw NullPointerException when menu item is null"
        );
        
        verify(menuItemsRepositoryPort, times(1)).save(nullMenuItem);
        verifyNoMoreInteractions(menuItemsRepositoryPort);
    }

    @Test
    @DisplayName("Should save menu item with all required fields")
    void shouldSaveMenuItemWithAllRequiredFields() {
        // Arrange
        MenuItem menuItem = TestDataBuilder.createValidMenuItem();
        MenuItem expectedResult = TestDataBuilder.createValidMenuItem();
        
        when(menuItemsRepositoryPort.save(menuItem)).thenReturn(expectedResult);

        // Act
        MenuItem result = menuItemUseCase.save(menuItem);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getNome(), "Menu item name should not be null");
        assertNotNull(result.getItemDescription(), "Menu item description should not be null");
        assertNotNull(result.getItemPrice(), "Menu item price should not be null");
        assertNotNull(result.getAvailability(), "Menu item availability should not be null");
        assertNotNull(result.getItemImage(), "Menu item image should not be null");
        
        verify(menuItemsRepositoryPort, times(1)).save(menuItem);
    }
}