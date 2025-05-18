package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.mock.MockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUseCaseTest {

    @InjectMocks
    private CreateUseCase createUseCase;

    @Mock
    private UsersRepository usersRepository;

    @Test
    @DisplayName("Create Use Case - Must create and save a database user.")
    void save() {
        //GIVEN
        var request = MockUser.userMockOne();

        //WHEN
        when(usersRepository.save(request)).thenReturn(MockUser.userMockOne());

        //THEN
        assertNotNull(createUseCase.save(request));
        assertDoesNotThrow(() -> createUseCase.save(request));
    }
}