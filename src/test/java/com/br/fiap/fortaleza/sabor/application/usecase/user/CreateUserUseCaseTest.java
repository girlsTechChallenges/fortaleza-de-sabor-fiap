//package com.br.fiap.fortaleza.sabor.application.usecase.user;
//
//import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
//import com.br.fiap.fortaleza.sabor.mock.MockUser;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class CreateUserUseCaseTest {
//
//    @InjectMocks
//    private CreateUserUseCase createUserUseCase;
//
//    @Mock
//    private UsersRepository usersRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    @DisplayName("Should create and save user successfully")
//    void shouldCreateAndSaveUserSuccessfully() {
//        // Arrange
//        var request = MockUser.userMockOne();
//
//        when(usersRepository.save(request)).thenReturn(MockUser.userMockOne());
//        when(passwordEncoder.encode(anyString())).thenReturn(anyString());
//
//        // Act & Assert
//        assertNotNull(createUserUseCase.save(request));
//        assertDoesNotThrow(() -> createUserUseCase.save(request));
//    }
//}