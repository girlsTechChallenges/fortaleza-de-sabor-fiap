package com.br.fiap.fortaleza.sabor.application.usecase.user;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateUserUseCase Tests")
class CreateUserUseCaseTest {

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should create user successfully when valid user provided")
    void shouldCreateUserSuccessfullyWhenValidUserProvided() {
        // Arrange
        User userToCreate = TestDataBuilder.createValidUser();
        User savedUser = TestDataBuilder.createValidUser();
        String encodedPassword = "encodedPassword123";

        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(usersRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = createUserUseCase.save(userToCreate);

        // Assert
        assertNotNull(result, "Created user should not be null");
        assertEquals(savedUser.getNome(), result.getNome(), "User name should match");
        assertEquals(savedUser.getEmail(), result.getEmail(), "User email should match");
        
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usersRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(passwordEncoder, usersRepository);
    }

    @Test
    @DisplayName("Should encode password when creating user")
    void shouldEncodePasswordWhenCreatingUser() {
        // Arrange
        User userToCreate = TestDataBuilder.createValidUser();
        User savedUser = TestDataBuilder.createValidUser();
        String originalPassword = userToCreate.getSenha();
        String encodedPassword = "encoded_" + originalPassword;

        when(passwordEncoder.encode(originalPassword)).thenReturn(encodedPassword);
        when(usersRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        createUserUseCase.save(userToCreate);

        // Assert
        verify(passwordEncoder, times(1)).encode(originalPassword);
        assertEquals(encodedPassword, userToCreate.getSenha(), "Password should be encoded");
    }

    @Test
    @DisplayName("Should throw exception when user repository fails")
    void shouldThrowExceptionWhenUserRepositoryFails() {
        // Arrange
        User userToCreate = TestDataBuilder.createValidUser();
        String encodedPassword = "encodedPassword123";
        
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(usersRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> createUserUseCase.save(userToCreate),
            "Should throw RuntimeException when repository fails"
        );
        
        assertEquals("Database error", exception.getMessage());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usersRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should not save user when password encoding fails")
    void shouldNotSaveUserWhenPasswordEncodingFails() {
        // Arrange
        User userToCreate = TestDataBuilder.createValidUser();
        
        when(passwordEncoder.encode(anyString())).thenThrow(new RuntimeException("Encoding error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> createUserUseCase.save(userToCreate),
            "Should throw RuntimeException when password encoding fails"
        );
        
        assertEquals("Encoding error", exception.getMessage());
        verify(passwordEncoder, times(1)).encode(anyString());
        verifyNoInteractions(usersRepository);
    }
}