package com.br.fiap.fortaleza.sabor.application.usecase.user;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
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
@DisplayName("DeleteUserUseCase Tests")
class DeleteUserUseCaseTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @Test
    @DisplayName("Should delete user successfully when user exists")
    void shouldDeleteUserSuccessfullyWhenUserExists() {
        // Arrange
        Long userId = TestConstants.VALID_ID;
        User deletedUser = TestDataBuilder.createValidUser();
        Optional<User> expectedResult = Optional.of(deletedUser);

        when(usersRepository.deleteById(eq(userId))).thenReturn(expectedResult);

        // Act
        Optional<User> result = deleteUserUseCase.delete(userId);

        // Assert
        assertTrue(result.isPresent(), "Delete result should be present");
        assertEquals(deletedUser, result.get(), "Should return deleted user");
        
        verify(usersRepository, times(1)).deleteById(userId);
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    @DisplayName("Should return empty Optional when user not found")
    void shouldReturnEmptyOptionalWhenUserNotFound() {
        // Arrange
        Long nonExistentId = TestConstants.INVALID_ID;
        
        when(usersRepository.deleteById(eq(nonExistentId))).thenReturn(Optional.empty());

        // Act
        Optional<User> result = deleteUserUseCase.delete(nonExistentId);

        // Assert
        assertFalse(result.isPresent(), "Result should not be present");
        assertTrue(result.isEmpty(), "Result should be empty when user not found");
        
        verify(usersRepository, times(1)).deleteById(nonExistentId);
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    @DisplayName("Should handle null ID gracefully")
    void shouldHandleNullIdGracefully() {
        // Arrange
        Long nullId = null;
        
        when(usersRepository.deleteById(isNull())).thenReturn(Optional.empty());

        // Act
        Optional<User> result = deleteUserUseCase.delete(nullId);

        // Assert
        assertFalse(result.isPresent(), "Result should not be present for null ID");
        
        verify(usersRepository, times(1)).deleteById(nullId);
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    @DisplayName("Should propagate repository exceptions")
    void shouldPropagateRepositoryExceptions() {
        // Arrange
        Long validId = TestConstants.VALID_ID;
        RuntimeException repositoryException = new RuntimeException("Database connection failed");
        
        when(usersRepository.deleteById(eq(validId))).thenThrow(repositoryException);

        // Act & Assert
        RuntimeException thrownException = assertThrows(
            RuntimeException.class,
            () -> deleteUserUseCase.delete(validId),
            "Should propagate repository exceptions"
        );
        
        assertEquals(repositoryException.getMessage(), thrownException.getMessage(), 
            "Exception message should be preserved");
        
        verify(usersRepository, times(1)).deleteById(validId);
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    @DisplayName("Should handle valid deletion with specific user details")
    void shouldHandleValidDeletionWithSpecificUserDetails() {
        // Arrange
        Long userId = TestConstants.VALID_ID;
        User deletedUser = TestDataBuilder.createValidUser();
        Optional<User> expectedResult = Optional.of(deletedUser);

        when(usersRepository.deleteById(eq(userId))).thenReturn(expectedResult);

        // Act
        Optional<User> result = deleteUserUseCase.delete(userId);

        // Assert
        assertTrue(result.isPresent(), "Delete result should be present");
        User returnedUser = result.get();
        assertNotNull(returnedUser.getNome(), "Deleted user should have name");
        assertNotNull(returnedUser.getEmail(), "Deleted user should have email");
        assertNotNull(returnedUser.getLogin(), "Deleted user should have login");
        
        verify(usersRepository, times(1)).deleteById(userId);
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    @DisplayName("Should handle edge case with zero ID")
    void shouldHandleEdgeCaseWithZeroId() {
        // Arrange
        Long zeroId = 0L;
        
        when(usersRepository.deleteById(eq(zeroId))).thenReturn(Optional.empty());

        // Act
        Optional<User> result = deleteUserUseCase.delete(zeroId);

        // Assert
        assertFalse(result.isPresent(), "Result should not be present for zero ID");
        
        verify(usersRepository, times(1)).deleteById(zeroId);
        verifyNoMoreInteractions(usersRepository);
    }
}
