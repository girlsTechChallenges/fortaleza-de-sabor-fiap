package com.br.fiap.fortaleza.sabor.application.usecase.usuario;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.application.usecase.user.GetUserUseCase;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.utils.TestConstants;
import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetUserUseCase Tests")
class GetUserUseCaseTest {

    @InjectMocks
    private GetUserUseCase getUserUseCase;

    @Mock
    private UsersRepository usersRepository;

    @Test
    @DisplayName("Should return all users when repository contains users")
    void shouldReturnAllUsersWhenRepositoryContainsUsers() {
        // Arrange
        User user1 = TestDataBuilder.createValidUser();
        User user2 = TestDataBuilder.createValidUser();
        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(usersRepository.getAll()).thenReturn(expectedUsers);

        // Act
        List<User> result = getUserUseCase.getAll();

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedUsers.size(), result.size(), "Should return correct number of users");
        assertEquals(expectedUsers, result, "Should return expected users");
        verify(usersRepository, times(1)).getAll();
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    @DisplayName("Should return empty list when no users exist")
    void shouldReturnEmptyListWhenNoUsersExist() {
        // Arrange
        List<User> emptyList = Collections.emptyList();
        when(usersRepository.getAll()).thenReturn(emptyList);

        // Act
        List<User> result = getUserUseCase.getAll();

        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Should return empty list when no users exist");
        assertEquals(0, result.size(), "List size should be zero");
        verify(usersRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return user by ID when user exists")
    void shouldReturnUserByIdWhenUserExists() {
        // Arrange
        Long validId = TestConstants.VALID_ID;
        User expectedUser = TestDataBuilder.createValidUser();
        when(usersRepository.getById(validId)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = getUserUseCase.getById(validId);

        // Assert
        assertTrue(result.isPresent(), "Should return present Optional when user exists");
        assertEquals(expectedUser, result.get(), "Should return correct user");
        assertEquals(expectedUser.getNome(), result.get().getNome(), "User name should match");
        assertEquals(expectedUser.getEmail(), result.get().getEmail(), "User email should match");
        verify(usersRepository, times(1)).getById(validId);
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    @DisplayName("Should return empty Optional when user not found by ID")
    void shouldReturnEmptyOptionalWhenUserNotFoundById() {
        // Arrange
        Long invalidId = TestConstants.INVALID_ID;
        when(usersRepository.getById(invalidId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = getUserUseCase.getById(invalidId);

        // Assert
        assertFalse(result.isPresent(), "Should return empty Optional when user not found");
        assertTrue(result.isEmpty(), "Optional should be empty");
        verify(usersRepository, times(1)).getById(invalidId);
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    @DisplayName("Should throw exception when repository fails during getAll")
    void shouldThrowExceptionWhenRepositoryFailsDuringGetAll() {
        // Arrange
        String errorMessage = "Database connection error";
        when(usersRepository.getAll()).thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> getUserUseCase.getAll(),
            "Should throw exception when repository fails"
        );
        
        assertEquals(errorMessage, exception.getMessage());
        verify(usersRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Should throw exception when repository fails during getById")
    void shouldThrowExceptionWhenRepositoryFailsDuringGetById() {
        // Arrange
        Long validId = TestConstants.VALID_ID;
        String errorMessage = "Database timeout";
        when(usersRepository.getById(anyLong())).thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> getUserUseCase.getById(validId),
            "Should throw exception when repository fails"
        );
        
        assertEquals(errorMessage, exception.getMessage());
        verify(usersRepository, times(1)).getById(validId);
    }

    @Test
    @DisplayName("Should handle null ID gracefully")
    void shouldHandleNullIdGracefully() {
        // Arrange
        Long nullId = null;
        when(usersRepository.getById(nullId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = getUserUseCase.getById(nullId);

        // Assert
        assertTrue(result.isEmpty(), "Should return empty Optional when ID is null");
        verify(usersRepository, times(1)).getById(nullId);
    }
}
