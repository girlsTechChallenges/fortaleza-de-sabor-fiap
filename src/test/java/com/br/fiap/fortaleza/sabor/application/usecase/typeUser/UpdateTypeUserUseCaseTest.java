package com.br.fiap.fortaleza.sabor.application.usecase.typeUser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UpdateTypeUserUseCaseTest {

    private TypeUsersRepository typeUserRepository;
    private UpdateTypeUserUseCase updateTypeUserUseCase;

    @BeforeEach
    void setUp() {
        typeUserRepository = mock(TypeUsersRepository.class);
        updateTypeUserUseCase = new UpdateTypeUserUseCase(typeUserRepository);
    }

    @Test
    void shouldUpdateTypeUserSuccessfully() {
        // Arrange
        Long id = 1L;
        TypeUser updatedUser = new TypeUser("ADMIN"); // ajuste conforme os campos reais
        Optional<TypeUser> expected = Optional.of(updatedUser);

        when(typeUserRepository.update(id, updatedUser)).thenReturn(expected);

        // Act
        Optional<TypeUser> result = updateTypeUserUseCase.update(id, updatedUser);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected.get(), result.get());
        verify(typeUserRepository, times(1)).update(id, updatedUser);
    }

    @Test
    void shouldReturnEmptyWhenTypeUserNotFound() {
        // Arrange
        Long id = 99L;
        TypeUser updatedUser = new TypeUser("CLIENTE");

        when(typeUserRepository.update(id, updatedUser)).thenReturn(Optional.empty());

        // Act
        Optional<TypeUser> result = updateTypeUserUseCase.update(id, updatedUser);

        // Assert
        assertTrue(result.isEmpty());
        verify(typeUserRepository, times(1)).update(id, updatedUser);
    }
}
