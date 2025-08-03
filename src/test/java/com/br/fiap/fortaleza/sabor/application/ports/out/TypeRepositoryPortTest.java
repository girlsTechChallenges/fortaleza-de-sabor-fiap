package com.br.fiap.fortaleza.sabor.application.ports.out;

import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TypeRepositoryPort Contract Tests")
class TypeRepositoryPortTest {

    @Mock
    private TypeRepositoryPort typeRepositoryPort;

    private TypeUser typeUser1;
    private TypeUser typeUser2;

    @BeforeEach
    void setUp() {
        typeUser1 = new TypeUser(1L, "ADMIN");
        typeUser2 = new TypeUser(2L, "USER");
    }

    @Test
    @DisplayName("Should create type user successfully")
    void shouldCreateTypeUser() {
        // Arrange
        when(typeRepositoryPort.create(any(TypeUser.class))).thenReturn(typeUser1);

        // Act
        TypeUser result = typeRepositoryPort.create(typeUser1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getType()).isEqualTo("ADMIN");
        verify(typeRepositoryPort).create(typeUser1);
    }

    @Test
    @DisplayName("Should update type user successfully")
    void shouldUpdateTypeUser() {
        // Arrange
        when(typeRepositoryPort.update(anyLong(), any(TypeUser.class))).thenReturn(Optional.of(typeUser1));

        // Act
        Optional<TypeUser> result = typeRepositoryPort.update(1L, typeUser1);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getType()).isEqualTo("ADMIN");
        verify(typeRepositoryPort).update(1L, typeUser1);
    }

    @Test
    @DisplayName("Should return empty when updating non-existent type user")
    void shouldReturnEmptyWhenUpdatingNonExistentTypeUser() {
        // Arrange
        when(typeRepositoryPort.update(anyLong(), any(TypeUser.class))).thenReturn(Optional.empty());

        // Act
        Optional<TypeUser> result = typeRepositoryPort.update(999L, typeUser1);

        // Assert
        assertThat(result).isEmpty();
        verify(typeRepositoryPort).update(999L, typeUser1);
    }

    @Test
    @DisplayName("Should delete type user by id successfully")
    void shouldDeleteTypeUserById() {
        // Arrange
        when(typeRepositoryPort.deleteById(anyLong())).thenReturn(Optional.of(typeUser1));

        // Act
        Optional<TypeUser> result = typeRepositoryPort.deleteById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(typeRepositoryPort).deleteById(1L);
    }

    @Test
    @DisplayName("Should return empty when deleting non-existent type user")
    void shouldReturnEmptyWhenDeletingNonExistentTypeUser() {
        // Arrange
        when(typeRepositoryPort.deleteById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<TypeUser> result = typeRepositoryPort.deleteById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(typeRepositoryPort).deleteById(999L);
    }

    @Test
    @DisplayName("Should get type user by id successfully")
    void shouldGetTypeUserById() {
        // Arrange
        when(typeRepositoryPort.getById(anyLong())).thenReturn(Optional.of(typeUser1));

        // Act
        Optional<TypeUser> result = typeRepositoryPort.getById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getType()).isEqualTo("ADMIN");
        verify(typeRepositoryPort).getById(1L);
    }

    @Test
    @DisplayName("Should return empty when getting non-existent type user by id")
    void shouldReturnEmptyWhenGettingNonExistentTypeUserById() {
        // Arrange
        when(typeRepositoryPort.getById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<TypeUser> result = typeRepositoryPort.getById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(typeRepositoryPort).getById(999L);
    }

    @Test
    @DisplayName("Should return all type users when getAll is called")
    void shouldReturnAllTypeUsers() {
        // Arrange
        List<TypeUser> expectedTypes = Arrays.asList(typeUser1, typeUser2);
        when(typeRepositoryPort.getAll()).thenReturn(expectedTypes);

        // Act
        List<TypeUser> result = typeRepositoryPort.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(typeUser1, typeUser2);
        verify(typeRepositoryPort).getAll();
    }
}
