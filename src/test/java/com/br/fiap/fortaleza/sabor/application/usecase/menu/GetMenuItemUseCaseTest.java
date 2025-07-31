package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.utils.TestConstants;
import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetMenuItemUseCase Tests")
class GetMenuItemUseCaseTest {

    private GetMenuItemUseCase getMenuItemUseCase;

    @Mock
    private MenuItemsRepository menuItemsRepository;

    @BeforeEach
    void setUp() {
        getMenuItemUseCase = new GetMenuItemUseCase(menuItemsRepository);
    }

    @Test
    @DisplayName("Should return all menu items when repository contains items")
    void shouldReturnAllMenuItemsWhenRepositoryContainsItems() {
        // Arrange
        MenuItem menuItem1 = TestDataBuilder.createValidMenuItem();
        MenuItem menuItem2 = TestDataBuilder.createValidMenuItem();
        List<MenuItem> expectedMenuItems = Arrays.asList(menuItem1, menuItem2);

        when(menuItemsRepository.getAll()).thenReturn(expectedMenuItems);

        // Act
        List<MenuItem> result = getMenuItemUseCase.getAll();

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedMenuItems.size(), result.size(), "Should return correct number of menu items");
        assertEquals(expectedMenuItems, result, "Should return expected menu items");
        verify(menuItemsRepository, times(1)).getAll();
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should return empty list when no menu items exist")
    void shouldReturnEmptyListWhenNoMenuItemsExist() {
        // Arrange
        List<MenuItem> emptyList = Collections.emptyList();
        when(menuItemsRepository.getAll()).thenReturn(emptyList);

        // Act
        List<MenuItem> result = getMenuItemUseCase.getAll();

        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Should return empty list when no items exist");
        assertEquals(0, result.size(), "List size should be zero");
        verify(menuItemsRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return menu item by ID when item exists")
    void shouldReturnMenuItemByIdWhenItemExists() {
        // Arrange
        Long validId = TestConstants.VALID_ID;
        MenuItem expectedMenuItem = TestDataBuilder.createValidMenuItem();
        when(menuItemsRepository.getById(validId)).thenReturn(Optional.of(expectedMenuItem));

        // Act
        Optional<MenuItem> result = getMenuItemUseCase.getById(validId);

        // Assert
        assertTrue(result.isPresent(), "Should return present Optional when item exists");
        assertEquals(expectedMenuItem, result.get(), "Should return correct menu item");
        assertEquals(expectedMenuItem.getNome(), result.get().getNome(), "Menu item name should match");
        verify(menuItemsRepository, times(1)).getById(validId);
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should return empty Optional when menu item not found by ID")
    void shouldReturnEmptyOptionalWhenMenuItemNotFoundById() {
        // Arrange
        Long invalidId = TestConstants.INVALID_ID;
        when(menuItemsRepository.getById(invalidId)).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = getMenuItemUseCase.getById(invalidId);

        // Assert
        assertFalse(result.isPresent(), "Should return empty Optional when item not found");
        assertTrue(result.isEmpty(), "Optional should be empty");
        verify(menuItemsRepository, times(1)).getById(invalidId);
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should throw exception when repository fails during getAll")
    void shouldThrowExceptionWhenRepositoryFailsDuringGetAll() {
        // Arrange
        String errorMessage = "Database connection error";
        when(menuItemsRepository.getAll()).thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> getMenuItemUseCase.getAll(),
            "Should throw exception when repository fails"
        );
        
        assertEquals(errorMessage, exception.getMessage());
        verify(menuItemsRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Should throw exception when repository fails during getById")
    void shouldThrowExceptionWhenRepositoryFailsDuringGetById() {
        // Arrange
        Long validId = TestConstants.VALID_ID;
        String errorMessage = "Database timeout";
        when(menuItemsRepository.getById(anyLong())).thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> getMenuItemUseCase.getById(validId),
            "Should throw exception when repository fails"
        );
        
        assertEquals(errorMessage, exception.getMessage());
        verify(menuItemsRepository, times(1)).getById(validId);
    }
}
