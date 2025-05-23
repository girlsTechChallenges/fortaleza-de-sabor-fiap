package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUseCaseTest {

    private UsersRepository usersRepository;
    private UpdateUseCase updateUseCase;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        updateUseCase = new UpdateUseCase(usersRepository);
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        // Arrange
        Long userId = 1L;
        User userToUpdate = new User(); // ou use um mock ou builder
        User updatedUser = new User();  // retorno esperado após atualização
        Optional<User> expected = Optional.of(updatedUser);

        when(usersRepository.update(userId, userToUpdate)).thenReturn(expected);

        // Act
        Optional<User> result = updateUseCase.update(userId, userToUpdate);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result);
        verify(usersRepository, times(1)).update(userId, userToUpdate);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // Arrange
        Long userId = 2L;
        User userToUpdate = new User();
        when(usersRepository.update(userId, userToUpdate)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = updateUseCase.update(userId, userToUpdate);

        // Assert
        assertTrue(result.isEmpty());
        verify(usersRepository, times(1)).update(userId, userToUpdate);
    }
}
