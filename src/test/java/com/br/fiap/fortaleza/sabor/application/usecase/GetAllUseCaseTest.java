package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.mock.MockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllUseCaseTest {

    @InjectMocks
    private GetAllUseCase getAllUseCas;

    @Mock
    private UsersRepository usersRepository;

    @Test
    @DisplayName("Use Case GetAll Users")
    void getAll() {
        //GIVEN
        var request = List.of(MockUser.userMockOne(), MockUser.userMockTwo());

        //WHEN
        when(usersRepository.getAll()).thenReturn(request);

        //THEN
        assertNotNull(getAllUseCas.getAll());
        assertDoesNotThrow(getAllUseCas::getAll);
    }
}