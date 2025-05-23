package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetUseCaseTest {

    private UsersRepository usersRepository;
    private GetUseCase getUseCase;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        getUseCase = new GetUseCase(usersRepository);
    }

    @Test
    void shouldReturnAllUsers() {
        // Arrange
        User user1 = new User(); // ou use um método de mock: UserMock.getMock()
        User user2 = new User();
        List<User> expectedUsers = List.of(user1, user2);

        when(usersRepository.getAll()).thenReturn(expectedUsers);

        // Act
        List<User> result = getUseCase.getAll();

        // Assert
        assertEquals(expectedUsers.size(), result.size());
        assertEquals(expectedUsers, result);
        verify(usersRepository, times(1)).getAll();
    }

    @Test
    void shouldReturnUserById() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User();
        when(usersRepository.getById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = getUseCase.getById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(usersRepository, times(1)).getById(userId);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // Arrange
        Long userId = 99L;
        when(usersRepository.getById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = getUseCase.getById(userId);

        // Assert
        assertTrue(result.isEmpty());
        verify(usersRepository, times(1)).getById(userId);
    }
}
