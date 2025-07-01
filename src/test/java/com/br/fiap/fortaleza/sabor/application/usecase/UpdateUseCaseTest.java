package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUseCaseTest {

    private UsersRepository usersRepository;
    private UpdateUseCase updateUseCase;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        updateUseCase = new UpdateUseCase(usersRepository, passwordEncoder);
    }

    @Test
    @DisplayName("Should update user successfully.")
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
    @DisplayName("Should return empty when user is not found.")
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
