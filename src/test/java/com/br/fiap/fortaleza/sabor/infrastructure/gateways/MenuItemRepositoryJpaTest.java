package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.MenuNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.MenuItemRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemsEntity;
import com.br.fiap.fortaleza.sabor.utils.TestConstants;
import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MenuItemRepositoryJpa Tests")
class MenuItemRepositoryJpaTest {

    @InjectMocks
    private MenuItemRepositoryJpa menuItemRepositoryJpa;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuMapper menuMapper;

    @BeforeEach
    void setUp() {
        // Setup comum se necessário
    }

    @Test
    @DisplayName("Should return all menu items when items exist in database")
    void shouldReturnAllMenuItemsWhenItemsExistInDatabase() {
        // Arrange
        MenuItemsEntity entity1 = TestDataBuilder.createValidMenuItemEntity();
        MenuItemsEntity entity2 = TestDataBuilder.createValidMenuItemEntity();
        List<MenuItemsEntity> entities = Arrays.asList(entity1, entity2);

        MenuItem domain1 = TestDataBuilder.createValidMenuItem();
        MenuItem domain2 = TestDataBuilder.createValidMenuItem();

        when(menuItemRepository.findAll()).thenReturn(entities);
        when(menuMapper.toMenuItemDomain(entity1)).thenReturn(domain1);
        when(menuMapper.toMenuItemDomain(entity2)).thenReturn(domain2);

        // Act
        List<MenuItem> result = menuItemRepositoryJpa.getAll();

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Should return correct number of menu items");
        assertEquals(domain1, result.get(0), "First item should match");
        assertEquals(domain2, result.get(1), "Second item should match");
        
        verify(menuItemRepository, times(1)).findAll();
        verify(menuMapper, times(1)).toMenuItemDomain(entity1);
        verify(menuMapper, times(1)).toMenuItemDomain(entity2);
    }

    @Test
    @DisplayName("Should return empty list when no menu items exist in database")
    void shouldReturnEmptyListWhenNoMenuItemsExistInDatabase() {
        // Arrange
        List<MenuItemsEntity> emptyList = Collections.emptyList();
        when(menuItemRepository.findAll()).thenReturn(emptyList);

        // Act
        List<MenuItem> result = menuItemRepositoryJpa.getAll();

        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty");
        assertEquals(0, result.size(), "Result size should be zero");
        
        verify(menuItemRepository, times(1)).findAll();
        verifyNoInteractions(menuMapper);
    }

    @Test
    @DisplayName("Should save menu item successfully when valid item provided")
    void shouldSaveMenuItemSuccessfullyWhenValidItemProvided() {
        // Arrange
        MenuItem menuItemToSave = TestDataBuilder.createValidMenuItem();
        MenuItemsEntity entityToSave = TestDataBuilder.createValidMenuItemEntity();
        MenuItemsEntity savedEntity = TestDataBuilder.createValidMenuItemEntity();
        MenuItem expectedResult = TestDataBuilder.createValidMenuItem();

        when(menuMapper.toMenuItemsEntity(menuItemToSave)).thenReturn(entityToSave);
        when(menuItemRepository.save(entityToSave)).thenReturn(savedEntity);
        when(menuMapper.toMenuItemDomain(savedEntity)).thenReturn(expectedResult);

        // Act
        MenuItem result = menuItemRepositoryJpa.save(menuItemToSave);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedResult, result, "Result should match expected menu item");
        
        verify(menuMapper, times(1)).toMenuItemsEntity(menuItemToSave);
        verify(menuItemRepository, times(1)).save(entityToSave);
        verify(menuMapper, times(1)).toMenuItemDomain(savedEntity);
    }

    @Test
    @DisplayName("Should find menu item by ID when item exists")
    void shouldFindMenuItemByIdWhenItemExists() {
        // Arrange
        Long validId = TestConstants.VALID_ID;
        MenuItemsEntity entity = TestDataBuilder.createValidMenuItemEntity();
        MenuItem expectedMenuItem = TestDataBuilder.createValidMenuItem();

        when(menuItemRepository.findById(validId)).thenReturn(Optional.of(entity));
        when(menuMapper.toMenuItemDomain(entity)).thenReturn(expectedMenuItem);

        // Act
        Optional<MenuItem> result = menuItemRepositoryJpa.getById(validId);

        // Assert
        assertTrue(result.isPresent(), "Result should be present");
        assertEquals(expectedMenuItem, result.get(), "Should return correct menu item");
        
        verify(menuItemRepository, times(1)).findById(validId);
        verify(menuMapper, times(1)).toMenuItemDomain(entity);
    }

    @Test
    @DisplayName("Should throw MenuNotFoundException when menu item not found by ID")
    void shouldThrowMenuNotFoundExceptionWhenMenuItemNotFoundById() {
        // Arrange
        Long invalidId = TestConstants.INVALID_ID;
        when(menuItemRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        MenuNotFoundException exception = assertThrows(MenuNotFoundException.class, 
            () -> menuItemRepositoryJpa.getById(invalidId),
            "Should throw MenuNotFoundException when menu item not found");
        
        assertTrue(exception.getMessage().contains(invalidId.toString()), 
            "Exception message should contain the invalid ID");
        
        verify(menuItemRepository, times(1)).findById(invalidId);
        verifyNoInteractions(menuMapper);
    }

    @Test
    @DisplayName("Should update menu item successfully when item exists")
    void shouldUpdateMenuItemSuccessfullyWhenItemExists() {
        // Arrange
        Long itemId = TestConstants.VALID_ID;
        MenuItem updatedMenuItem = TestDataBuilder.createValidMenuItem();
        MenuItemsEntity existingEntity = TestDataBuilder.createValidMenuItemEntity();
        MenuItemsEntity savedEntity = TestDataBuilder.createValidMenuItemEntity();
        MenuItem expectedResult = TestDataBuilder.createValidMenuItem();

        when(menuItemRepository.findById(itemId)).thenReturn(Optional.of(existingEntity));
        when(menuItemRepository.save(any(MenuItemsEntity.class))).thenReturn(savedEntity);
        when(menuMapper.toMenuItemDomain(savedEntity)).thenReturn(expectedResult);

        // Act
        Optional<MenuItem> result = menuItemRepositoryJpa.update(itemId, updatedMenuItem);

        // Assert
        assertTrue(result.isPresent(), "Result should be present");
        assertEquals(expectedResult, result.get(), "Should return updated menu item");
        
        verify(menuItemRepository, times(1)).findById(itemId);
        verify(menuItemRepository, times(1)).save(any(MenuItemsEntity.class));
        verify(menuMapper, times(1)).toMenuItemDomain(savedEntity);
    }

    @Test
    @DisplayName("Should throw MenuNotFoundException when updating non-existent menu item")
    void shouldThrowMenuNotFoundExceptionWhenUpdatingNonExistentMenuItem() {
        // Arrange
        Long nonExistentId = TestConstants.INVALID_ID;
        MenuItem updatedMenuItem = TestDataBuilder.createValidMenuItem();
        
        when(menuItemRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        MenuNotFoundException exception = assertThrows(MenuNotFoundException.class,
            () -> menuItemRepositoryJpa.update(nonExistentId, updatedMenuItem),
            "Should throw MenuNotFoundException when menu item not found for update");
        
        assertTrue(exception.getMessage().contains(nonExistentId.toString()), 
            "Exception message should contain the invalid ID");
        
        verify(menuItemRepository, times(1)).findById(nonExistentId);
        verify(menuItemRepository, never()).save(any(MenuItemsEntity.class));
        verifyNoInteractions(menuMapper);
    }

    @Test
    @DisplayName("Should delete menu item successfully when item exists")
    void shouldDeleteMenuItemSuccessfullyWhenItemExists() {
        // Arrange
        Long itemId = TestConstants.VALID_ID;
        MenuItemsEntity existingEntity = TestDataBuilder.createValidMenuItemEntity();
        MenuItem expectedDeletedItem = TestDataBuilder.createValidMenuItem();
        
        when(menuItemRepository.findById(itemId)).thenReturn(Optional.of(existingEntity));
        doNothing().when(menuItemRepository).deleteById(itemId);
        when(menuMapper.toMenuItemDomain(existingEntity)).thenReturn(expectedDeletedItem);

        // Act
        Optional<MenuItem> result = menuItemRepositoryJpa.deleteById(itemId);

        // Assert
        assertTrue(result.isPresent(), "Delete operation should return menu item");
        assertEquals(expectedDeletedItem, result.get(), "Should return deleted menu item");
        
        verify(menuItemRepository, times(2)).findById(itemId); // Called twice in deleteById method
        verify(menuItemRepository, times(1)).deleteById(itemId);
        verify(menuMapper, times(1)).toMenuItemDomain(existingEntity);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent menu item")
    void shouldThrowExceptionWhenDeletingNonExistentMenuItem() {
        // Arrange
        Long nonExistentId = TestConstants.INVALID_ID;
        
        when(menuItemRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        MenuNotFoundException exception = assertThrows(
            MenuNotFoundException.class,
            () -> menuItemRepositoryJpa.deleteById(nonExistentId)
        );
        
        assertTrue(exception.getMessage().contains("was not found"), "Should throw appropriate exception");
        
        verify(menuItemRepository, times(1)).findById(nonExistentId);
        verify(menuItemRepository, never()).deleteById(anyLong());
        verifyNoInteractions(menuMapper);
    }

    @Test
    @DisplayName("Should handle repository exceptions gracefully")
    void shouldHandleRepositoryExceptionsGracefully() {
        // Arrange
        String errorMessage = "Database connection failed";
        when(menuItemRepository.findAll()).thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> menuItemRepositoryJpa.getAll(),
            "Should throw exception when repository fails"
        );
        
        assertEquals(errorMessage, exception.getMessage());
        verify(menuItemRepository, times(1)).findAll();
        verifyNoInteractions(menuMapper);
    }
}
