package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.utils.TestConstants;
import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteMenuItemUseCase Tests")
class DeleteMenuItemUseCaseTest {

    @Mock
    private MenuItemsRepository menuItemsRepository;

    @InjectMocks
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @Test
    @DisplayName("Should delete menu item successfully when item exists")
    void shouldDeleteMenuItemSuccessfullyWhenItemExists() {
        // Arrange
        Long menuItemId = TestConstants.VALID_ID;
        MenuItem deletedMenuItem = TestDataBuilder.createValidMenuItem();
        Optional<MenuItem> expectedResult = Optional.of(deletedMenuItem);

        when(menuItemsRepository.deleteById(eq(menuItemId))).thenReturn(expectedResult);

        // Act
        Optional<MenuItem> result = deleteMenuItemUseCase.delete(menuItemId);

        // Assert
        assertTrue(result.isPresent(), "Delete result should be present");
        assertEquals(deletedMenuItem, result.get(), "Should return deleted menu item");
        
        verify(menuItemsRepository, times(1)).deleteById(menuItemId);
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should return empty Optional when menu item not found")
    void shouldReturnEmptyOptionalWhenMenuItemNotFound() {
        // Arrange
        Long nonExistentId = TestConstants.INVALID_ID;
        
        when(menuItemsRepository.deleteById(eq(nonExistentId))).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = deleteMenuItemUseCase.delete(nonExistentId);

        // Assert
        assertFalse(result.isPresent(), "Result should not be present");
        assertTrue(result.isEmpty(), "Result should be empty when item not found");
        
        verify(menuItemsRepository, times(1)).deleteById(nonExistentId);
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should handle null ID gracefully")
    void shouldHandleNullIdGracefully() {
        // Arrange
        Long nullId = null;
        
        when(menuItemsRepository.deleteById(isNull())).thenReturn(Optional.empty());

        // Act
        Optional<MenuItem> result = deleteMenuItemUseCase.delete(nullId);

        // Assert
        assertFalse(result.isPresent(), "Result should not be present for null ID");
        
        verify(menuItemsRepository, times(1)).deleteById(nullId);
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should propagate repository exceptions")
    void shouldPropagateRepositoryExceptions() {
        // Arrange
        Long validId = TestConstants.VALID_ID;
        RuntimeException repositoryException = new RuntimeException("Database connection failed");
        
        when(menuItemsRepository.deleteById(eq(validId))).thenThrow(repositoryException);

        // Act & Assert
        RuntimeException thrownException = assertThrows(
            RuntimeException.class,
            () -> deleteMenuItemUseCase.delete(validId),
            "Should propagate repository exceptions"
        );
        
        assertEquals(repositoryException.getMessage(), thrownException.getMessage(), 
            "Exception message should be preserved");
        
        verify(menuItemsRepository, times(1)).deleteById(validId);
        verifyNoMoreInteractions(menuItemsRepository);
    }

    @Test
    @DisplayName("Should handle valid deletion with specific menu item details")
    void shouldHandleValidDeletionWithSpecificMenuItemDetails() {
        // Arrange
        Long menuItemId = TestConstants.VALID_ID;
        MenuItem deletedMenuItem = TestDataBuilder.createValidMenuItem();
        Optional<MenuItem> expectedResult = Optional.of(deletedMenuItem);

        when(menuItemsRepository.deleteById(eq(menuItemId))).thenReturn(expectedResult);

        // Act
        Optional<MenuItem> result = deleteMenuItemUseCase.delete(menuItemId);

        // Assert
        assertTrue(result.isPresent(), "Delete result should be present");
        MenuItem returnedItem = result.get();
        assertNotNull(returnedItem.getNome(), "Deleted item should have name");
        assertNotNull(returnedItem.getItemDescription(), "Deleted item should have description");
        assertNotNull(returnedItem.getItemPrice(), "Deleted item should have price");
        
        verify(menuItemsRepository, times(1)).deleteById(menuItemId);
        verifyNoMoreInteractions(menuItemsRepository);
    }
}
