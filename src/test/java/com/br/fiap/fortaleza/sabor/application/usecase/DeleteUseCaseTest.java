package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUseCaseTest {

    private UsersRepository usersRepository;
    private DeleteUseCase deleteUseCase;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        deleteUseCase = new DeleteUseCase(usersRepository);
    }

    @Test
    void shouldDeleteUserByIdSuccessfully() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User(); // ou use um mock ou método de mock UserMock.getMock()
        Optional<User> expected = Optional.of(mockUser);

        when(usersRepository.deleteById(userId)).thenReturn(expected);

        // Act
        Optional<User> result = deleteUseCase.delete(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result);
        verify(usersRepository, times(1)).deleteById(userId);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // Arrange
        Long userId = 2L;
        when(usersRepository.deleteById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = deleteUseCase.delete(userId);

        // Assert
        assertTrue(result.isEmpty());
        verify(usersRepository, times(1)).deleteById(userId);
    }
}
