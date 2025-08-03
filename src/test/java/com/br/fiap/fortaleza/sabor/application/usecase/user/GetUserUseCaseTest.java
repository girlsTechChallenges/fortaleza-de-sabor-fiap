package com.br.fiap.fortaleza.sabor.application.usecase.user;

import com.br.fiap.fortaleza.sabor.application.ports.out.UsersRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseTest {

   private UsersRepositoryPort usersRepositoryPort;
   private UserUseCase userUseCase;
   private PasswordEncoder passwordEncoder;

   @BeforeEach
   void setUp() {
       usersRepositoryPort = mock(UsersRepositoryPort.class);
       passwordEncoder = mock(PasswordEncoder.class);
       userUseCase = new UserUseCase(passwordEncoder, usersRepositoryPort);
   }

   @Test
   @DisplayName("Should return all users")
   void shouldReturnAllUsers() {
       // Arrange
       User user1 = new User();
       User user2 = new User();
       List<User> expectedUsers = List.of(user1, user2);

       when(usersRepositoryPort.getAll()).thenReturn(expectedUsers);

       // Act
       List<User> result = userUseCase.getAll();

       // Assert
       assertEquals(expectedUsers.size(), result.size());
       assertEquals(expectedUsers, result);
       verify(usersRepositoryPort, times(1)).getAll();
   }

   @Test
   @DisplayName("Should return user by ID.")
   void shouldReturnUserById() {
       // Arrange
       Long userId = 1L;
       User expectedUser = new User();
       when(usersRepositoryPort.getById(userId)).thenReturn(Optional.of(expectedUser));

       // Act
       Optional<User> result = userUseCase.getById(userId);

       // Assert
       assertTrue(result.isPresent());
       assertEquals(expectedUser, result.get());
       verify(usersRepositoryPort, times(1)).getById(userId);
   }

   @Test
   @DisplayName("Should return empty when user is not found")
   void shouldReturnEmptyWhenUserNotFound() {
       // Arrange
       Long userId = 99L;
       when(usersRepositoryPort.getById(userId)).thenReturn(Optional.empty());

       // Act
       Optional<User> result = userUseCase.getById(userId);

       // Assert
       assertTrue(result.isEmpty());
       verify(usersRepositoryPort, times(1)).getById(userId);
   }
}
