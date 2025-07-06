package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.application.usecase.usuario.GetUserUseCase;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseTest {

    private UsersRepository usersRepository;
    private GetUserUseCase getUserUseCase;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        getUserUseCase = new GetUserUseCase(usersRepository);
    }

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        List<User> expectedUsers = List.of(user1, user2);

        when(usersRepository.getAll()).thenReturn(expectedUsers);

        // Act
        List<User> result = getUserUseCase.getAll();

        // Assert
        assertEquals(expectedUsers.size(), result.size());
        assertEquals(expectedUsers, result);
        verify(usersRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return user by ID.")
    void shouldReturnUserById() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User();
        when(usersRepository.getById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = getUserUseCase.getById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(usersRepository, times(1)).getById(userId);
    }

    @Test
    @DisplayName("Should return empty when user is not found")
    void shouldReturnEmptyWhenUserNotFound() {
        // Arrange
        Long userId = 99L;
        when(usersRepository.getById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = getUserUseCase.getById(userId);

        // Assert
        assertTrue(result.isEmpty());
        verify(usersRepository, times(1)).getById(userId);
    }
}
