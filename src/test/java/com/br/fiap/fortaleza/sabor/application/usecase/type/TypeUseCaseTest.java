package com.br.fiap.fortaleza.sabor.application.usecase.type;

import com.br.fiap.fortaleza.sabor.application.ports.out.TypeRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TypeUseCase Tests")
class TypeUseCaseTest {

    @Mock
    private TypeRepositoryPort typeRepositoryPort;

    @InjectMocks
    private TypeUseCase typeUseCase;

    private TypeUser typeUser;
    private TypeUser anotherTypeUser;

    @BeforeEach
    void setUp() {
        typeUser = new TypeUser();
        typeUser.setId(null); // For creation tests
        typeUser.setType("CLIENTE");

        anotherTypeUser = new TypeUser();
        anotherTypeUser.setId(2L);
        anotherTypeUser.setType("PROPRIETARIO");
    }

    @Test
    @DisplayName("Should create type user when create is called with valid TypeUser")
    void shouldCreateTypeUserWhenCreateIsCalledWithValidTypeUser() {
        // Arrange
        TypeUser createdTypeUser = new TypeUser();
        createdTypeUser.setId(1L);
        createdTypeUser.setType("CLIENTE");
        
        when(typeRepositoryPort.create(any(TypeUser.class))).thenReturn(createdTypeUser);

        // Act
        TypeUser result = typeUseCase.create(typeUser);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getType()).isEqualTo("CLIENTE");
        verify(typeRepositoryPort, times(1)).create(typeUser);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when create is called with TypeUser having ID")
    void shouldThrowIllegalArgumentExceptionWhenCreateIsCalledWithTypeUserHavingId() {
        // Arrange
        TypeUser typeUserWithId = new TypeUser();
        typeUserWithId.setId(1L);
        typeUserWithId.setType("CLIENTE");

        // Act & Assert
        assertThatThrownBy(() -> typeUseCase.create(typeUserWithId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("TypeUser ID must be null for creation");
        
        verify(typeRepositoryPort, never()).create(any(TypeUser.class));
    }

    @Test
    @DisplayName("Should return all type users when getAll is called")
    void shouldReturnAllTypeUsersWhenGetAllIsCalled() {
        // Arrange
        List<TypeUser> expectedTypeUsers = Arrays.asList(typeUser, anotherTypeUser);
        when(typeRepositoryPort.getAll()).thenReturn(expectedTypeUsers);

        // Act
        List<TypeUser> actualTypeUsers = typeUseCase.getAll();

        // Assert
        assertThat(actualTypeUsers).isNotNull();
        assertThat(actualTypeUsers).hasSize(2);
        assertThat(actualTypeUsers).containsExactly(typeUser, anotherTypeUser);
        verify(typeRepositoryPort, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return empty list when no type users exist")
    void shouldReturnEmptyListWhenNoTypeUsersExist() {
        // Arrange
        when(typeRepositoryPort.getAll()).thenReturn(Arrays.asList());

        // Act
        List<TypeUser> actualTypeUsers = typeUseCase.getAll();

        // Assert
        assertThat(actualTypeUsers).isNotNull();
        assertThat(actualTypeUsers).isEmpty();
        verify(typeRepositoryPort, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return type user when getById is called with valid id")
    void shouldReturnTypeUserWhenGetByIdIsCalledWithValidId() {
        // Arrange
        Long typeUserId = 1L;
        when(typeRepositoryPort.getById(typeUserId)).thenReturn(Optional.of(typeUser));

        // Act
        Optional<TypeUser> result = typeUseCase.getById(typeUserId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(typeUser);
        verify(typeRepositoryPort, times(1)).getById(typeUserId);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when getById is called with invalid id")
    void shouldThrowIllegalArgumentExceptionWhenGetByIdIsCalledWithInvalidId() {
        // Arrange
        Long invalidTypeUserId = 999L;
        when(typeRepositoryPort.getById(invalidTypeUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> typeUseCase.getById(invalidTypeUserId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User type not found.");
        verify(typeRepositoryPort, times(1)).getById(invalidTypeUserId);
    }

    @Test
    @DisplayName("Should update type user when update is called with valid id")
    void shouldUpdateTypeUserWhenUpdateIsCalledWithValidId() {
        // Arrange
        Long typeUserId = 1L;
        TypeUser updatedTypeUser = new TypeUser();
        updatedTypeUser.setId(typeUserId);
        updatedTypeUser.setType("ADMIN");

        when(typeRepositoryPort.update(typeUserId, typeUser)).thenReturn(Optional.of(updatedTypeUser));

        // Act
        Optional<TypeUser> result = typeUseCase.update(typeUserId, typeUser);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(typeUserId);
        assertThat(result.get().getType()).isEqualTo("ADMIN");
        verify(typeRepositoryPort, times(1)).update(typeUserId, typeUser);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when update is called with invalid id")
    void shouldThrowIllegalArgumentExceptionWhenUpdateIsCalledWithInvalidId() {
        // Arrange
        Long invalidTypeUserId = 999L;
        when(typeRepositoryPort.update(invalidTypeUserId, typeUser)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> typeUseCase.update(invalidTypeUserId, typeUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User type not found.");
        verify(typeRepositoryPort, times(1)).update(invalidTypeUserId, typeUser);
    }

    @Test
    @DisplayName("Should delete type user when deleteById is called with valid id")
    void shouldDeleteTypeUserWhenDeleteByIdIsCalledWithValidId() {
        // Arrange
        Long typeUserId = 1L;
        when(typeRepositoryPort.deleteById(typeUserId)).thenReturn(Optional.of(typeUser));

        // Act
        Optional<TypeUser> result = typeUseCase.deleteById(typeUserId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(typeUser);
        verify(typeRepositoryPort, times(1)).deleteById(typeUserId);
    }

    @Test
    @DisplayName("Should throw RuntimeException when deleteById is called with invalid id")
    void shouldThrowRuntimeExceptionWhenDeleteByIdIsCalledWithInvalidId() {
        // Arrange
        Long invalidTypeUserId = 999L;
        when(typeRepositoryPort.deleteById(invalidTypeUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> typeUseCase.deleteById(invalidTypeUserId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Type user not found with id: 999");
        verify(typeRepositoryPort, times(1)).deleteById(invalidTypeUserId);
    }

    @Test
    @DisplayName("Should throw NullPointerException when create is called with null TypeUser")
    void shouldThrowNullPointerExceptionWhenCreateIsCalledWithNullTypeUser() {
        // Arrange
        TypeUser nullTypeUser = null;

        // Act & Assert
        assertThatThrownBy(() -> typeUseCase.create(nullTypeUser))
                .isInstanceOf(NullPointerException.class);
        
        verify(typeRepositoryPort, never()).create(any());
    }

    @Test
    @DisplayName("Should create type user with different properties")
    void shouldCreateTypeUserWithDifferentProperties() {
        // Arrange
        TypeUser customTypeUser = new TypeUser();
        customTypeUser.setId(null);
        customTypeUser.setType("FUNCIONARIO");

        TypeUser createdCustomTypeUser = new TypeUser();
        createdCustomTypeUser.setId(3L);
        createdCustomTypeUser.setType("FUNCIONARIO");

        when(typeRepositoryPort.create(customTypeUser)).thenReturn(createdCustomTypeUser);

        // Act
        TypeUser result = typeUseCase.create(customTypeUser);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getType()).isEqualTo("FUNCIONARIO");
        verify(typeRepositoryPort, times(1)).create(customTypeUser);
    }

    @Test
    @DisplayName("Should validate business rule for ID during creation")
    void shouldValidateBusinessRuleForIdDuringCreation() {
        // Arrange
        TypeUser invalidTypeUser = new TypeUser();
        invalidTypeUser.setId(10L); // Should be null for creation
        invalidTypeUser.setType("TESTE");

        // Act & Assert
        assertThatThrownBy(() -> typeUseCase.create(invalidTypeUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("TypeUser ID must be null for creation");
        
        // Verify repository is never called when validation fails
        verify(typeRepositoryPort, never()).create(any(TypeUser.class));
    }
}
