package com.br.fiap.fortaleza.sabor.application.usecase.userType;

import com.br.fiap.fortaleza.sabor.application.gateways.UserTypesRepository;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeleteUserTypeUseCaseTest {

    private UserTypesRepository userTypesRepository;
    private DeleteUserTypeUseCase deleteUserTypeUseCase;

    @BeforeEach
    void setUp() {
        userTypesRepository = mock(UserTypesRepository.class);
        deleteUserTypeUseCase = new DeleteUserTypeUseCase(userTypesRepository);
    }

    @Test
    void shouldDeleteUserTypeSuccessfully() {
        // Arrange
        Long id = 1L;
        UserType mockUserType = new UserType("CLIENTE"); // Ajuste conforme o construtor real
        Optional<UserType> expected = Optional.of(mockUserType);

        when(userTypesRepository.deleteById(id)).thenReturn(expected);

        // Act
        Optional<UserType> result = deleteUserTypeUseCase.delete(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected.get(), result.get());
        verify(userTypesRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldReturnEmptyWhenUserTypeNotFound() {
        // Arrange
        Long id = 999L;
        when(userTypesRepository.deleteById(id)).thenReturn(Optional.empty());

        // Act
        Optional<UserType> result = deleteUserTypeUseCase.delete(id);

        // Assert
        assertTrue(result.isEmpty());
        verify(userTypesRepository, times(1)).deleteById(id);
    }
}
