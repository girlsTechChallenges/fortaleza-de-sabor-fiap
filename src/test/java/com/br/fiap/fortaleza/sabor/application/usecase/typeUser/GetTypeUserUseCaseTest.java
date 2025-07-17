package com.br.fiap.fortaleza.sabor.application.usecase.typeUser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GetTypeUserUseCaseTest {

    private TypeUsersRepository typeUsersRepository;
    private GetTypeUserUseCase getTypeUserUseCase;

    @BeforeEach
    void setUp() {
        typeUsersRepository = mock(TypeUsersRepository.class);
        getTypeUserUseCase = new GetTypeUserUseCase(typeUsersRepository);
    }

    @Test
    void shouldReturnAllTypeUsers() {
        // Arrange
        List<TypeUser> mockList = Arrays.asList(
                new TypeUser("ADMIN"),
                new TypeUser("CLIENTE")
        );

        when(typeUsersRepository.getAll()).thenReturn(mockList);

        // Act
        List<TypeUser> result = getTypeUserUseCase.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(typeUsersRepository, times(1)).getAll();
    }

    @Test
    void shouldReturnTypeUserByIdWhenExists() {
        // Arrange
        Long id = 1L;
        TypeUser mockTypeUser = new TypeUser("CLIENTE");

        when(typeUsersRepository.getById(id)).thenReturn(Optional.of(mockTypeUser));

        // Act
        Optional<TypeUser> result = getTypeUserUseCase.getById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockTypeUser, result.get());
        verify(typeUsersRepository, times(1)).getById(id);
    }

    @Test
    void shouldReturnEmptyWhenTypeUserDoesNotExist() {
        // Arrange
        Long id = 999L;

        when(typeUsersRepository.getById(id)).thenReturn(Optional.empty());

        // Act
        Optional<TypeUser> result = getTypeUserUseCase.getById(id);

        // Assert
        assertTrue(result.isEmpty());
        verify(typeUsersRepository, times(1)).getById(id);
    }
}
