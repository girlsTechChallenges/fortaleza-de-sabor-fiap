package com.br.fiap.fortaleza.sabor.application.usecase.user;

import com.br.fiap.fortaleza.sabor.application.ports.out.UsersRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserUseCase Tests")
class UserUseCaseTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsersRepositoryPort usersRepositoryPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;
    private final String rawPassword = "password123";
    private final String encodedPassword = "encodedPassword123";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setNome("John Doe");
        user.setEmail("john.doe@test.com");
        user.setLogin("johndoe");
        user.setSenha(rawPassword);
        user.setDataAlteracao(LocalDate.now());
        user.setTipo("CLIENTE");
    }

    @Test
    @DisplayName("Should return all users when getAll is called")
    void shouldReturnAllUsersWhenGetAllIsCalled() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(user, new User());
        when(usersRepositoryPort.getAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userUseCase.getAll();

        // Assert
        assertThat(actualUsers).isNotNull();
        assertThat(actualUsers).hasSize(2);
        assertThat(actualUsers).isEqualTo(expectedUsers);
        verify(usersRepositoryPort, times(1)).getAll();
    }

    @Test
    @DisplayName("Should save user with encoded password when save is called")
    void shouldSaveUserWithEncodedPasswordWhenSaveIsCalled() {
        // Arrange
        User expectedUser = new User();
        expectedUser.setNome(user.getNome());
        expectedUser.setEmail(user.getEmail());
        expectedUser.setLogin(user.getLogin());
        expectedUser.setSenha(encodedPassword);
        expectedUser.setDataAlteracao(user.getDataAlteracao());
        expectedUser.setTipo(user.getTipo());

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(usersRepositoryPort.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User actualUser = userUseCase.save(user);

        // Assert
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getSenha()).isEqualTo(encodedPassword);
        assertThat(user.getSenha()).isEqualTo(encodedPassword); // Verify password was encoded in original object
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(usersRepositoryPort, times(1)).save(user);
    }

    @Test
    @DisplayName("Should update user with encoded password when update is called with valid id")
    void shouldUpdateUserWithEncodedPasswordWhenUpdateIsCalledWithValidId() {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setNome("Updated Name");
        updatedUser.setEmail("updated@test.com");
        updatedUser.setSenha(encodedPassword);
        
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(usersRepositoryPort.update(userId, user)).thenReturn(Optional.of(updatedUser));

        // Act
        Optional<User> result = userUseCase.update(userId, user);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getSenha()).isEqualTo(encodedPassword);
        assertThat(user.getSenha()).isEqualTo(encodedPassword); // Verify password was encoded in original object
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(usersRepositoryPort, times(1)).update(userId, user);
    }

    @Test
    @DisplayName("Should return empty optional when update is called with invalid id")
    void shouldReturnEmptyOptionalWhenUpdateIsCalledWithInvalidId() {
        // Arrange
        Long invalidUserId = 999L;
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(usersRepositoryPort.update(invalidUserId, user)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userUseCase.update(invalidUserId, user);

        // Assert
        assertThat(result).isEmpty();
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(usersRepositoryPort, times(1)).update(invalidUserId, user);
    }

    @Test
    @DisplayName("Should return user when getById is called with valid id")
    void shouldReturnUserWhenGetByIdIsCalledWithValidId() {
        // Arrange
        Long userId = 1L;
        when(usersRepositoryPort.getById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userUseCase.getById(userId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
        verify(usersRepositoryPort, times(1)).getById(userId);
    }

    @Test
    @DisplayName("Should return empty optional when getById is called with invalid id")
    void shouldReturnEmptyOptionalWhenGetByIdIsCalledWithInvalidId() {
        // Arrange
        Long invalidUserId = 999L;
        when(usersRepositoryPort.getById(invalidUserId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userUseCase.getById(invalidUserId);

        // Assert
        assertThat(result).isEmpty();
        verify(usersRepositoryPort, times(1)).getById(invalidUserId);
    }

    @Test
    @DisplayName("Should delete user when deleteById is called with valid id")
    void shouldDeleteUserWhenDeleteByIdIsCalledWithValidId() {
        // Arrange
        Long userId = 1L;
        when(usersRepositoryPort.deleteById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userUseCase.deleteById(userId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
        verify(usersRepositoryPort, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Should return empty optional when deleteById is called with invalid id")
    void shouldReturnEmptyOptionalWhenDeleteByIdIsCalledWithInvalidId() {
        // Arrange
        Long invalidUserId = 999L;
        when(usersRepositoryPort.deleteById(invalidUserId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userUseCase.deleteById(invalidUserId);

        // Assert
        assertThat(result).isEmpty();
        verify(usersRepositoryPort, times(1)).deleteById(invalidUserId);
    }

    @Test
    @DisplayName("Should return user when findByEmail is called with valid email")
    void shouldReturnUserWhenFindByEmailIsCalledWithValidEmail() {
        // Arrange
        String email = "john.doe@test.com";
        when(usersRepositoryPort.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userUseCase.findByEmail(email);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
        verify(usersRepositoryPort, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Should return empty optional when findByEmail is called with invalid email")
    void shouldReturnEmptyOptionalWhenFindByEmailIsCalledWithInvalidEmail() {
        // Arrange
        String invalidEmail = "nonexistent@test.com";
        when(usersRepositoryPort.findByEmail(invalidEmail)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userUseCase.findByEmail(invalidEmail);

        // Assert
        assertThat(result).isEmpty();
        verify(usersRepositoryPort, times(1)).findByEmail(invalidEmail);
    }

    @Test
    @DisplayName("Should call updatePassword method without implementation")
    void shouldCallUpdatePasswordMethodWithoutImplementation() {
        // Arrange
        String email = "john.doe@test.com";
        String newPassword = "newPassword123";

        // Act & Assert - Method exists but has no implementation yet
        userUseCase.updatePassword(email, newPassword);
        
        // This test validates the method signature exists
        // Implementation should be added when the method is properly implemented
    }
}
