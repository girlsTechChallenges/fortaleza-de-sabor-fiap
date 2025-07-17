package com.br.fiap.fortaleza.sabor.application.usecase.typeUser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeleteTypeUserUseCaseTest {

    private TypeUsersRepository typeUsersRepository;
    private DeleteTypeUserUseCase deleteTypeUserUseCase;

    @BeforeEach
    void setUp() {
        typeUsersRepository = mock(TypeUsersRepository.class);
        deleteTypeUserUseCase = new DeleteTypeUserUseCase(typeUsersRepository);
    }

    @Test
    void shouldDeleteTypeUserSuccessfully() {
        // Arrange
        Long id = 1L;
        TypeUser mockTypeUser = new TypeUser("CLIENTE"); // Ajuste conforme o construtor real
        Optional<TypeUser> expected = Optional.of(mockTypeUser);

        when(typeUsersRepository.deleteById(id)).thenReturn(expected);

        // Act
        Optional<TypeUser> result = deleteTypeUserUseCase.delete(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected.get(), result.get());
        verify(typeUsersRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldReturnEmptyWhenTypeUserNotFound() {
        // Arrange
        Long id = 999L;
        when(typeUsersRepository.deleteById(id)).thenReturn(Optional.empty());

        // Act
        Optional<TypeUser> result = deleteTypeUserUseCase.delete(id);

        // Assert
        assertTrue(result.isEmpty());
        verify(typeUsersRepository, times(1)).deleteById(id);
    }
}
