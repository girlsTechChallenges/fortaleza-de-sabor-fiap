package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.ports.out.MenuItemsRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
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
@DisplayName("MenuItemUseCase Tests")
class GetMenuItemUseCaseTest {

   private MenuItemUseCase menuItemUseCase;

   @Mock
   private MenuItemsRepositoryPort menuItemsRepositoryPort;

   @BeforeEach
   void setUp() {
       menuItemUseCase = new MenuItemUseCase(menuItemsRepositoryPort);
   }

   @Test
   @DisplayName("Should return all menu items when repository contains items")
   void shouldReturnAllMenuItemsWhenRepositoryContainsItems() {
       // Arrange
       MenuItem menuItem1 = TestDataBuilder.createValidMenuItem();
       MenuItem menuItem2 = TestDataBuilder.createValidMenuItem();
       List<MenuItem> expectedMenuItems = Arrays.asList(menuItem1, menuItem2);

       when(menuItemsRepositoryPort.getAll()).thenReturn(expectedMenuItems);

       // Act
       List<MenuItem> result = menuItemUseCase.getAll();

       // Assert
       assertNotNull(result, "Result should not be null");
       assertEquals(expectedMenuItems.size(), result.size(), "Should return correct number of menu items");
       assertEquals(expectedMenuItems, result, "Should return expected menu items");
       verify(menuItemsRepositoryPort, times(1)).getAll();
       verifyNoMoreInteractions(menuItemsRepositoryPort);
   }

   @Test
   @DisplayName("Should return empty list when no menu items exist")
   void shouldReturnEmptyListWhenNoMenuItemsExist() {
       // Arrange
       List<MenuItem> emptyList = Collections.emptyList();
       when(menuItemsRepositoryPort.getAll()).thenReturn(emptyList);

       // Act
       List<MenuItem> result = menuItemUseCase.getAll();

       // Assert
       assertNotNull(result, "Result should not be null");
       assertTrue(result.isEmpty(), "Should return empty list when no items exist");
       assertEquals(0, result.size(), "List size should be zero");
       verify(menuItemsRepositoryPort, times(1)).getAll();
   }

   @Test
   @DisplayName("Should return menu item by ID when item exists")
   void shouldReturnMenuItemByIdWhenItemExists() {
       // Arrange
       Long validId = TestConstants.VALID_ID;
       MenuItem expectedMenuItem = TestDataBuilder.createValidMenuItem();
       when(menuItemsRepositoryPort.getById(validId)).thenReturn(Optional.of(expectedMenuItem));

       // Act
       Optional<MenuItem> result = menuItemUseCase.getById(validId);

       // Assert
       assertTrue(result.isPresent(), "Should return present Optional when item exists");
       assertEquals(expectedMenuItem, result.get(), "Should return correct menu item");
       assertEquals(expectedMenuItem.getNome(), result.get().getNome(), "Menu item name should match");
       verify(menuItemsRepositoryPort, times(1)).getById(validId);
       verifyNoMoreInteractions(menuItemsRepositoryPort);
   }

   @Test
   @DisplayName("Should return empty Optional when menu item not found by ID")
   void shouldReturnEmptyOptionalWhenMenuItemNotFoundById() {
       // Arrange
       Long invalidId = TestConstants.INVALID_ID;
       when(menuItemsRepositoryPort.getById(invalidId)).thenReturn(Optional.empty());

       // Act
       Optional<MenuItem> result = menuItemUseCase.getById(invalidId);

       // Assert
       assertFalse(result.isPresent(), "Should return empty Optional when item not found");
       assertTrue(result.isEmpty(), "Optional should be empty");
       verify(menuItemsRepositoryPort, times(1)).getById(invalidId);
       verifyNoMoreInteractions(menuItemsRepositoryPort);
   }

   @Test
   @DisplayName("Should throw exception when repository fails during getAll")
   void shouldThrowExceptionWhenRepositoryFailsDuringGetAll() {
       // Arrange
       String errorMessage = "Database connection error";
       when(menuItemsRepositoryPort.getAll()).thenThrow(new RuntimeException(errorMessage));

       // Act & Assert
       RuntimeException exception = assertThrows(RuntimeException.class,
           () -> menuItemUseCase.getAll(),
           "Should throw exception when repository fails"
       );

       assertEquals(errorMessage, exception.getMessage());
       verify(menuItemsRepositoryPort, times(1)).getAll();
   }

   @Test
   @DisplayName("Should throw exception when repository fails during getById")
   void shouldThrowExceptionWhenRepositoryFailsDuringGetById() {
       // Arrange
       Long validId = TestConstants.VALID_ID;
       String errorMessage = "Database timeout";
       when(menuItemsRepositoryPort.getById(anyLong())).thenThrow(new RuntimeException(errorMessage));

       // Act & Assert
       RuntimeException exception = assertThrows(RuntimeException.class,
           () -> menuItemUseCase.getById(validId),
           "Should throw exception when repository fails"
       );

       assertEquals(errorMessage, exception.getMessage());
       verify(menuItemsRepositoryPort, times(1)).getById(validId);
   }
}
