package com.br.fiap.fortaleza.sabor.application.usecase.menu;

import com.br.fiap.fortaleza.sabor.application.gateways.MenuItemsRepository;
import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.application.usecase.usuario.CreateUserUseCase;
import com.br.fiap.fortaleza.sabor.mock.MockMenu;
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
class CreateMenuItemUseCaseTest {
    @InjectMocks
    private CreateMenuItemUseCase createMenuItemUseCase;

    @Mock
    private MenuItemsRepository menuItemsRepository;


    @Test
    @DisplayName("Create Use Case - Must create and save a database menu.")
    void save() {
        //GIVEN
        var request = MockMenu.menuItemMockOne();

        //WHEN
        when(menuItemsRepository.save(request)).thenReturn(MockMenu.menuItemMockOne());

        //THEN
        assertNotNull(createMenuItemUseCase.save(request));
        assertDoesNotThrow(() -> createMenuItemUseCase.save(request));
    }
}
