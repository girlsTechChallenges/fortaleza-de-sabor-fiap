//package com.br.fiap.fortaleza.sabor.application.usecase.menu;
//
//import com.br.fiap.fortaleza.sabor.application.ports.out.MenuItemsRepositoryPort;
//import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
//import com.br.fiap.fortaleza.sabor.utils.TestConstants;
//import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("DeleteMenuItemUseCase Tests")
//class DeleteMenuItemUseCaseTest {
//
//    @Mock
//    private MenuItemsRepositoryPort menuItemsRepositoryPort;
//
//    @InjectMocks
//    private DeleteMenuItemUseCase deleteMenuItemUseCase;
//
//    @Test
//    @DisplayName("Should delete menu item successfully when item exists")
//    void shouldDeleteMenuItemSuccessfullyWhenItemExists() {
//        // Arrange
//        Long menuItemId = TestConstants.VALID_ID;
//        MenuItem deletedMenuItem = TestDataBuilder.createValidMenuItem();
//        Optional<MenuItem> expectedResult = Optional.of(deletedMenuItem);
//
//        when(menuItemsRepositoryPort.deleteById(eq(menuItemId))).thenReturn(expectedResult);
//
//        // Act
//        Optional<MenuItem> result = deleteMenuItemUseCase.delete(menuItemId);
//
//        // Assert
//        assertTrue(result.isPresent(), "Delete result should be present");
//        assertEquals(deletedMenuItem, result.get(), "Should return deleted menu item");
//
//        verify(menuItemsRepositoryPort, times(1)).deleteById(menuItemId);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//
//    @Test
//    @DisplayName("Should return empty Optional when menu item not found")
//    void shouldReturnEmptyOptionalWhenMenuItemNotFound() {
//        // Arrange
//        Long nonExistentId = TestConstants.INVALID_ID;
//
//        when(menuItemsRepositoryPort.deleteById(eq(nonExistentId))).thenReturn(Optional.empty());
//
//        // Act
//        Optional<MenuItem> result = deleteMenuItemUseCase.delete(nonExistentId);
//
//        // Assert
//        assertFalse(result.isPresent(), "Result should not be present");
//        assertTrue(result.isEmpty(), "Result should be empty when item not found");
//
//        verify(menuItemsRepositoryPort, times(1)).deleteById(nonExistentId);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//
//    @Test
//    @DisplayName("Should handle null ID gracefully")
//    void shouldHandleNullIdGracefully() {
//        // Arrange
//        Long nullId = null;
//
//        when(menuItemsRepositoryPort.deleteById(isNull())).thenReturn(Optional.empty());
//
//        // Act
//        Optional<MenuItem> result = deleteMenuItemUseCase.delete(nullId);
//
//        // Assert
//        assertFalse(result.isPresent(), "Result should not be present for null ID");
//
//        verify(menuItemsRepositoryPort, times(1)).deleteById(nullId);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//
//    @Test
//    @DisplayName("Should propagate repository exceptions")
//    void shouldPropagateRepositoryExceptions() {
//        // Arrange
//        Long validId = TestConstants.VALID_ID;
//        RuntimeException repositoryException = new RuntimeException("Database connection failed");
//
//        when(menuItemsRepositoryPort.deleteById(eq(validId))).thenThrow(repositoryException);
//
//        // Act & Assert
//        RuntimeException thrownException = assertThrows(
//            RuntimeException.class,
//            () -> deleteMenuItemUseCase.delete(validId),
//            "Should propagate repository exceptions"
//        );
//
//        assertEquals(repositoryException.getMessage(), thrownException.getMessage(),
//            "Exception message should be preserved");
//
//        verify(menuItemsRepositoryPort, times(1)).deleteById(validId);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//
//    @Test
//    @DisplayName("Should handle valid deletion with specific menu item details")
//    void shouldHandleValidDeletionWithSpecificMenuItemDetails() {
//        // Arrange
//        Long menuItemId = TestConstants.VALID_ID;
//        MenuItem deletedMenuItem = TestDataBuilder.createValidMenuItem();
//        Optional<MenuItem> expectedResult = Optional.of(deletedMenuItem);
//
//        when(menuItemsRepositoryPort.deleteById(eq(menuItemId))).thenReturn(expectedResult);
//
//        // Act
//        Optional<MenuItem> result = deleteMenuItemUseCase.delete(menuItemId);
//
//        // Assert
//        assertTrue(result.isPresent(), "Delete result should be present");
//        MenuItem returnedItem = result.get();
//        assertNotNull(returnedItem.getNome(), "Deleted item should have name");
//        assertNotNull(returnedItem.getItemDescription(), "Deleted item should have description");
//        assertNotNull(returnedItem.getItemPrice(), "Deleted item should have price");
//
//        verify(menuItemsRepositoryPort, times(1)).deleteById(menuItemId);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//}
