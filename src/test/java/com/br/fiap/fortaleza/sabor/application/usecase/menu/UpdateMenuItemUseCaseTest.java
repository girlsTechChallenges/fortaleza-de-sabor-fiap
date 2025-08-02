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
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("UpdateMenuItemUseCase Tests")
//class UpdateMenuItemUseCaseTest {
//
//    @Mock
//    private MenuItemsRepositoryPort menuItemsRepositoryPort;
//
//    @InjectMocks
//    private UpdateMenuItemUseCase updateMenuItemUseCase;
//
//    @Test
//    @DisplayName("Should update menu item successfully when item exists")
//    void shouldUpdateMenuItemSuccessfullyWhenItemExists() {
//        // Arrange
//        Long menuItemId = TestConstants.VALID_ID;
//        MenuItem menuItemToUpdate = TestDataBuilder.createValidMenuItem();
//        MenuItem updatedMenuItem = TestDataBuilder.createValidMenuItem();
//        Optional<MenuItem> expectedResult = Optional.of(updatedMenuItem);
//
//        when(menuItemsRepositoryPort.update(eq(menuItemId), any(MenuItem.class)))
//            .thenReturn(expectedResult);
//
//        // Act
//        Optional<MenuItem> result = updateMenuItemUseCase.update(menuItemId, menuItemToUpdate);
//
//        // Assert
//        assertTrue(result.isPresent(), "Update result should be present");
//        assertEquals(updatedMenuItem, result.get(), "Should return updated menu item");
//
//        verify(menuItemsRepositoryPort, times(1)).update(menuItemId, menuItemToUpdate);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//
//    @Test
//    @DisplayName("Should return empty Optional when menu item not found")
//    void shouldReturnEmptyOptionalWhenMenuItemNotFound() {
//        // Arrange
//        Long nonExistentId = TestConstants.INVALID_ID;
//        MenuItem menuItemToUpdate = TestDataBuilder.createValidMenuItem();
//
//        when(menuItemsRepositoryPort.update(eq(nonExistentId), any(MenuItem.class)))
//            .thenReturn(Optional.empty());
//
//        // Act
//        Optional<MenuItem> result = updateMenuItemUseCase.update(nonExistentId, menuItemToUpdate);
//
//        // Assert
//        assertFalse(result.isPresent(), "Result should not be present");
//        assertTrue(result.isEmpty(), "Result should be empty when item not found");
//
//        verify(menuItemsRepositoryPort, times(1)).update(nonExistentId, menuItemToUpdate);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//
//    @Test
//    @DisplayName("Should handle null menu item gracefully")
//    void shouldHandleNullMenuItemGracefully() {
//        // Arrange
//        Long validId = TestConstants.VALID_ID;
//        MenuItem nullMenuItem = null;
//
//        when(menuItemsRepositoryPort.update(eq(validId), isNull()))
//            .thenReturn(Optional.empty());
//
//        // Act
//        Optional<MenuItem> result = updateMenuItemUseCase.update(validId, nullMenuItem);
//
//        // Assert
//        assertFalse(result.isPresent(), "Result should not be present for null menu item");
//
//        verify(menuItemsRepositoryPort, times(1)).update(validId, nullMenuItem);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//
//    @Test
//    @DisplayName("Should handle null ID gracefully")
//    void shouldHandleNullIdGracefully() {
//        // Arrange
//        Long nullId = null;
//        MenuItem menuItemToUpdate = TestDataBuilder.createValidMenuItem();
//
//        when(menuItemsRepositoryPort.update(isNull(), any(MenuItem.class)))
//            .thenReturn(Optional.empty());
//
//        // Act
//        Optional<MenuItem> result = updateMenuItemUseCase.update(nullId, menuItemToUpdate);
//
//        // Assert
//        assertFalse(result.isPresent(), "Result should not be present for null ID");
//
//        verify(menuItemsRepositoryPort, times(1)).update(nullId, menuItemToUpdate);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//
//    @Test
//    @DisplayName("Should propagate repository exceptions")
//    void shouldPropagateRepositoryExceptions() {
//        // Arrange
//        Long validId = TestConstants.VALID_ID;
//        MenuItem menuItemToUpdate = TestDataBuilder.createValidMenuItem();
//        RuntimeException repositoryException = new RuntimeException("Database connection failed");
//
//        when(menuItemsRepositoryPort.update(eq(validId), any(MenuItem.class)))
//            .thenThrow(repositoryException);
//
//        // Act & Assert
//        RuntimeException thrownException = assertThrows(
//            RuntimeException.class,
//            () -> updateMenuItemUseCase.update(validId, menuItemToUpdate),
//            "Should propagate repository exceptions"
//        );
//
//        assertEquals(repositoryException.getMessage(), thrownException.getMessage(),
//            "Exception message should be preserved");
//
//        verify(menuItemsRepositoryPort, times(1)).update(validId, menuItemToUpdate);
//        verifyNoMoreInteractions(menuItemsRepositoryPort);
//    }
//}
