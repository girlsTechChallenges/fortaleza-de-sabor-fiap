package com.br.fiap.fortaleza.sabor.application.usecase;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.mock.MockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    @InjectMocks
    private CreateUseCase createUseCase;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Create Use Case - Must create and save a database user.")
    void save() {
        //GIVEN
        var request = MockUser.userMockOne();

        //WHEN
        when(usersRepository.save(request)).thenReturn(MockUser.userMockOne());
        when(passwordEncoder.encode(anyString())).thenReturn(anyString());

        //THEN
        assertNotNull(createUseCase.save(request));
        assertDoesNotThrow(() -> createUseCase.save(request));
    }
}