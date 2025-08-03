package com.br.fiap.fortaleza.sabor.application.ports.out;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsersRepositoryPort Contract Tests")
class UsersRepositoryPortTest {

    @Mock
    private UsersRepositoryPort usersRepositoryPort;

    private User user;
    private User user2;

    @BeforeEach
    void setUp() {
        Address address1 = new Address("Rua Teste", "Centro", "Apto 123", 123, "SP", "São Paulo", "12345-678");
        Address address2 = new Address("Rua Nova", "Jardins", "Casa", 456, "SP", "São Paulo", "54321-876");
        
        user = new User("João Silva", "joao@email.com", "password123", "ADMIN", Arrays.asList(address1));
        user.setLogin("joao123");
        user.setDataAlteracao(LocalDate.of(1990, 1, 1));
        
        user2 = new User("Maria Silva", "maria@email.com", "password456", "USER", Arrays.asList(address2));
        user2.setLogin("maria123");
        user2.setDataAlteracao(LocalDate.of(1995, 5, 15));
    }

    @Test
    @DisplayName("Should return all users when getAll is called")
    void shouldReturnAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(user, user2);
        when(usersRepositoryPort.getAll()).thenReturn(expectedUsers);

        // Act
        List<User> result = usersRepositoryPort.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(user, user2);
        verify(usersRepositoryPort).getAll();
    }

    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUser() {
        // Arrange
        when(usersRepositoryPort.save(any(User.class))).thenReturn(user);

        // Act
        User result = usersRepositoryPort.save(user);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo("João Silva");
        assertThat(result.getEmail()).isEqualTo("joao@email.com");
        verify(usersRepositoryPort).save(user);
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUser() {
        // Arrange
        when(usersRepositoryPort.update(anyLong(), any(User.class))).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = usersRepositoryPort.update(1L, user);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNome()).isEqualTo("João Silva");
        verify(usersRepositoryPort).update(1L, user);
    }

    @Test
    @DisplayName("Should return empty when updating non-existent user")
    void shouldReturnEmptyWhenUpdatingNonExistentUser() {
        // Arrange
        when(usersRepositoryPort.update(anyLong(), any(User.class))).thenReturn(Optional.empty());

        // Act
        Optional<User> result = usersRepositoryPort.update(999L, user);

        // Assert
        assertThat(result).isEmpty();
        verify(usersRepositoryPort).update(999L, user);
    }

    @Test
    @DisplayName("Should get user by id successfully")
    void shouldGetUserById() {
        // Arrange
        when(usersRepositoryPort.getById(anyLong())).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = usersRepositoryPort.getById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNome()).isEqualTo("João Silva");
        verify(usersRepositoryPort).getById(1L);
    }

    @Test
    @DisplayName("Should return empty when getting non-existent user by id")
    void shouldReturnEmptyWhenGettingNonExistentUserById() {
        // Arrange
        when(usersRepositoryPort.getById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<User> result = usersRepositoryPort.getById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(usersRepositoryPort).getById(999L);
    }

    @Test
    @DisplayName("Should delete user by id successfully")
    void shouldDeleteUserById() {
        // Arrange
        when(usersRepositoryPort.deleteById(anyLong())).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = usersRepositoryPort.deleteById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNome()).isEqualTo("João Silva");
        verify(usersRepositoryPort).deleteById(1L);
    }

    @Test
    @DisplayName("Should return empty when deleting non-existent user")
    void shouldReturnEmptyWhenDeletingNonExistentUser() {
        // Arrange
        when(usersRepositoryPort.deleteById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<User> result = usersRepositoryPort.deleteById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(usersRepositoryPort).deleteById(999L);
    }

    @Test
    @DisplayName("Should find user by email successfully")
    void shouldFindUserByEmail() {
        // Arrange
        when(usersRepositoryPort.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = usersRepositoryPort.findByEmail("joao@email.com");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("joao@email.com");
        verify(usersRepositoryPort).findByEmail("joao@email.com");
    }

    @Test
    @DisplayName("Should return empty when finding non-existent user by email")
    void shouldReturnEmptyWhenFindingNonExistentUserByEmail() {
        // Arrange
        when(usersRepositoryPort.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<User> result = usersRepositoryPort.findByEmail("notfound@email.com");

        // Assert
        assertThat(result).isEmpty();
        verify(usersRepositoryPort).findByEmail("notfound@email.com");
    }

    @Test
    @DisplayName("Should update password successfully")
    void shouldUpdatePassword() {
        // Arrange
        doNothing().when(usersRepositoryPort).updatePassword(anyString(), anyString());

        // Act
        usersRepositoryPort.updatePassword("joao@email.com", "newPassword123");

        // Assert
        verify(usersRepositoryPort).updatePassword("joao@email.com", "newPassword123");
    }
}
