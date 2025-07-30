package com.br.fiap.fortaleza.sabor.application.usecase.userType;

import com.br.fiap.fortaleza.sabor.application.gateways.UserTypesRepository;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GetUserTypeUseCaseTest {

    private UserTypesRepository userTypesRepository;
    private GetUserTypeUseCase getUserTypeUseCase;

    @BeforeEach
    void setUp() {
        userTypesRepository = mock(UserTypesRepository.class);
        getUserTypeUseCase = new GetUserTypeUseCase(userTypesRepository);
    }

    @Test
    void shouldReturnAllUserTypes() {
        // Arrange
        List<UserType> mockList = Arrays.asList(
                new UserType("ADMIN"),
                new UserType("CLIENTE")
        );

        when(userTypesRepository.getAll()).thenReturn(mockList);

        // Act
        List<UserType> result = getUserTypeUseCase.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userTypesRepository, times(1)).getAll();
    }

    @Test
    void shouldReturnUserTypeByIdWhenExists() {
        // Arrange
        Long id = 1L;
        UserType mockUserType = new UserType("CLIENTE");

        when(userTypesRepository.getById(id)).thenReturn(Optional.of(mockUserType));

        // Act
        Optional<UserType> result = getUserTypeUseCase.getById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockUserType, result.get());
        verify(userTypesRepository, times(1)).getById(id);
    }

    @Test
    void shouldReturnEmptyWhenUserTypeDoesNotExist() {
        // Arrange
        Long id = 999L;

        when(userTypesRepository.getById(id)).thenReturn(Optional.empty());

        // Act
        Optional<UserType> result = getUserTypeUseCase.getById(id);

        // Assert
        assertTrue(result.isEmpty());
        verify(userTypesRepository, times(1)).getById(id);
    }
}
