package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
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
class CreateMenuItemUseCaseTest {

    @InjectMocks
    private CreateMenuItemUseCase createMenuItemUseCase;

    @Mock
    private MenuItemsRepository menuItemsRepository;

    @Test
    @DisplayName("Should create menu item successfully when valid item provided")
    void shouldCreateMenuItemSuccessfullyWhenValidItemProvided() {
        // Arrange
        MenuItem menuItemToCreate = TestDataBuilder.createValidMenuItem();
        MenuItem savedMenuItem = TestDataBuilder.createValidMenuItem();

        when(menuItemsRepository.save(any(MenuItem.class))).thenReturn(savedMenuItem);

        // Act
        MenuItem result = createMenuItemUseCase.save(menuItemToCreate);

        // Assert
        assertNotNull(result, "Created menu item should not be null");
        assertEquals(savedMenuItem.getNome(), result.getNome(), "Menu item name should match");
        assertEquals(savedMenuItem.getItemDescription(), result.getItemDescription(), "Menu item description should match");
        assertEquals(savedMenuItem.getItemPrice(), result.getItemPrice(), "Menu item price should match");
        assertEquals(savedMenuItem.getAvailability(), result.getAvailability(), "Menu item availability should match");
        assertEquals(savedMenuItem.getItemImage(), result.getItemImage(), "Menu item image should match");
        
        verify(menuItemsRepository, times(1)).save(menuItemToCreate);
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should throw exception when repository fails to save menu item")
    void shouldThrowExceptionWhenRepositoryFailsToSaveMenuItem() {
        // Arrange
        MenuItem menuItemToCreate = TestDataBuilder.createValidMenuItem();
        String errorMessage = "Database connection failed";
        
        when(menuItemsRepository.save(any(MenuItem.class)))
            .thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> createMenuItemUseCase.save(menuItemToCreate),
            "Should throw RuntimeException when repository fails"
        );
        
        assertEquals(errorMessage, exception.getMessage());
        verify(menuItemsRepository, times(1)).save(menuItemToCreate);
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should handle null menu item gracefully")
    void shouldHandleNullMenuItemGracefully() {
        // Arrange
        MenuItem nullMenuItem = null;

        when(menuItemsRepository.save(isNull())).thenThrow(new NullPointerException("Menu item cannot be null"));

        // Act & Assert
        assertThrows(NullPointerException.class,
            () -> createMenuItemUseCase.save(nullMenuItem),
            "Should throw NullPointerException when menu item is null"
        );
        
        verify(menuItemsRepository, times(1)).save(nullMenuItem);
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should save menu item with all required fields")
    void shouldSaveMenuItemWithAllRequiredFields() {
        // Arrange
        MenuItem menuItem = TestDataBuilder.createValidMenuItem();
        MenuItem expectedResult = TestDataBuilder.createValidMenuItem();
        
        when(menuItemsRepository.save(menuItem)).thenReturn(expectedResult);

        // Act
        MenuItem result = createMenuItemUseCase.save(menuItem);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getNome(), "Menu item name should not be null");
        assertNotNull(result.getItemDescription(), "Menu item description should not be null");
        assertNotNull(result.getItemPrice(), "Menu item price should not be null");
        assertNotNull(result.getAvailability(), "Menu item availability should not be null");
        assertNotNull(result.getItemImage(), "Menu item image should not be null");
        
        verify(menuItemsRepository, times(1)).save(menuItem);
    }
}