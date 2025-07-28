package com.br.fiap.fortaleza.sabor.application.usecase.typeUser;

import com.br.fiap.fortaleza.sabor.application.gateways.TypeUsersRepository;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateTypeUserUseCaseTest {

    private TypeUsersRepository typeUsersRepository;
    private CreateTypeUserUseCase createTypeUserUseCase;

    @BeforeEach
    void setUp() {
        typeUsersRepository = mock(TypeUsersRepository.class);
        createTypeUserUseCase = new CreateTypeUserUseCase(typeUsersRepository);
    }

    @Test
    void shouldSaveTypeUserSuccessfully() {
        // Arrange
        TypeUser typeUser = new TypeUser("ADMIN"); // substitua conforme os atributos reais
        when(typeUsersRepository.save(typeUser)).thenReturn(typeUser);

        // Act
        TypeUser result = createTypeUserUseCase.save(typeUser);

        // Assert
        assertNotNull(result);
        assertEquals(typeUser, result);
        verify(typeUsersRepository, times(1)).save(typeUser);
    }
}
