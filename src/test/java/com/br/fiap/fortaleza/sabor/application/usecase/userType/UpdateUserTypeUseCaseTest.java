package com.br.fiap.fortaleza.sabor.application.usecase.userType;

import com.br.fiap.fortaleza.sabor.application.gateways.UserTypesRepository;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UpdateUserTypeUseCaseTest {

    private UserTypesRepository userTypeRepository;
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    @BeforeEach
    void setUp() {
        userTypeRepository = mock(UserTypesRepository.class);
        updateUserTypeUseCase = new UpdateUserTypeUseCase(userTypeRepository);
    }

    @Test
    void shouldUpdateUserTypeSuccessfully() {
        // Arrange
        Long id = 1L;
        UserType updatedUser = new UserType("ADMIN"); // ajuste conforme os campos reais
        Optional<UserType> expected = Optional.of(updatedUser);

        when(userTypeRepository.update(id, updatedUser)).thenReturn(expected);

        // Act
        Optional<UserType> result = updateUserTypeUseCase.update(id, updatedUser);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected.get(), result.get());
        verify(userTypeRepository, times(1)).update(id, updatedUser);
    }

    @Test
    void shouldReturnEmptyWhenUserTypeNotFound() {
        // Arrange
        Long id = 99L;
        UserType updatedUser = new UserType("CLIENTE");

        when(userTypeRepository.update(id, updatedUser)).thenReturn(Optional.empty());

        // Act
        Optional<UserType> result = updateUserTypeUseCase.update(id, updatedUser);

        // Assert
        assertTrue(result.isEmpty());
        verify(userTypeRepository, times(1)).update(id, updatedUser);
    }
}
