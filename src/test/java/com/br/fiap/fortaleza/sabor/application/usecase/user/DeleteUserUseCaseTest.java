package com.br.fiap.fortaleza.sabor.application.usecase.user;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @Mock
    private UsersRepository usersRepository;

    @Test
    @DisplayName("Should delete user by ID successfully")
    void shouldDeleteUserByIdSuccessfully() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        Optional<User> expected = Optional.of(mockUser);

        when(usersRepository.deleteById(userId)).thenReturn(expected);

        // Act
        Optional<User> result = deleteUserUseCase.delete(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result);
        verify(usersRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Should return empty when user is not found")
    void shouldReturnEmptyWhenUserNotFound() {
        // Arrange
        Long userId = 2L;
        when(usersRepository.deleteById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = deleteUserUseCase.delete(userId);

        // Assert
        assertTrue(result.isEmpty());
        verify(usersRepository, times(1)).deleteById(userId);
    }
}
