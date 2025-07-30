package com.br.fiap.fortaleza.sabor.application.usecase.userType;

import com.br.fiap.fortaleza.sabor.application.gateways.UserTypesRepository;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateUserTypeUseCaseTest {

    private UserTypesRepository userTypesRepository;
    private CreateUserTypeUseCase createUserTypeUseCase;

    @BeforeEach
    void setUp() {
        userTypesRepository = mock(UserTypesRepository.class);
        createUserTypeUseCase = new CreateUserTypeUseCase(userTypesRepository);
    }

    @Test
    void shouldSaveUserTypeSuccessfully() {
        // Arrange
        UserType userType = new UserType("ADMIN"); // substitua conforme os atributos reais
        when(userTypesRepository.save(userType)).thenReturn(userType);

        // Act
        UserType result = createUserTypeUseCase.save(userType);

        // Assert
        assertNotNull(result);
        assertEquals(userType, result);
        verify(userTypesRepository, times(1)).save(userType);
    }
}
