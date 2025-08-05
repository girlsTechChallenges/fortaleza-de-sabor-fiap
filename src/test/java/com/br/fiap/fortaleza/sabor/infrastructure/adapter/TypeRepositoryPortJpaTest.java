package com.br.fiap.fortaleza.sabor.infrastructure.adapter;

import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserTypeMismatchException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeUserRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TypeRepositoryPortJpa Tests")
class TypeRepositoryPortJpaTest {

    @Mock
    private TypeUserRepositoryAdapter typeUserRepositoryAdapter;

    @Mock
    private UserRepositoryAdapter userRepositoryAdapter;

    @Mock
    private TypeUserMapper typeMapper;

    @InjectMocks
    private TypeRepositoryPortJpa typeRepositoryPortJpa;

    private TypeUser typeUser;
    private TypeEntity typeEntity;
    private TypeEntity typeEntity2;

    @BeforeEach
    void setUp() {
        typeUser = new TypeUser(1L, "ADMIN");
        
        typeEntity = new TypeEntity(1L, "ADMIN");
        typeEntity2 = new TypeEntity(2L, "USER");
    }

    @Test
    @DisplayName("Should create type user successfully when type does not exist")
    void shouldCreateTypeUserSuccessfully() {
        // Arrange
        when(typeUserRepositoryAdapter.findByNameType(anyString())).thenReturn(Optional.empty());
        when(typeUserRepositoryAdapter.save(any(TypeEntity.class))).thenReturn(typeEntity);
        when(typeMapper.toTypeUserEntity(any(TypeEntity.class))).thenReturn(typeUser);

        // Act
        TypeUser result = typeRepositoryPortJpa.create(typeUser);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("ADMIN");
        verify(typeUserRepositoryAdapter).findByNameType("ADMIN");
        verify(typeUserRepositoryAdapter).save(any(TypeEntity.class));
        verify(typeMapper).toTypeUserEntity(typeEntity);
    }

    @Test
    @DisplayName("Should return existing type user when type already exists")
    void shouldReturnExistingTypeUserWhenTypeExists() {
        // Arrange
        when(typeUserRepositoryAdapter.findByNameType(anyString())).thenReturn(Optional.of(typeEntity));
        when(typeMapper.toTypeUserEntity(any(TypeEntity.class))).thenReturn(typeUser);

        // Act
        TypeUser result = typeRepositoryPortJpa.create(typeUser);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("ADMIN");
        verify(typeUserRepositoryAdapter).findByNameType("ADMIN");
        verify(typeUserRepositoryAdapter, never()).save(any());
        verify(typeMapper).toTypeUserEntity(typeEntity);
    }

    @Test
    @DisplayName("Should handle DataIntegrityViolationException during type creation")
    void shouldHandleDataIntegrityViolationException() {
        // Arrange
        when(typeUserRepositoryAdapter.findByNameType(anyString()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(typeEntity));
        when(typeUserRepositoryAdapter.save(any(TypeEntity.class))).thenThrow(DataIntegrityViolationException.class);
        when(typeMapper.toTypeUserEntity(any(TypeEntity.class))).thenReturn(typeUser);

        // Act
        TypeUser result = typeRepositoryPortJpa.create(typeUser);

        // Assert
        assertThat(result).isNotNull();
        verify(typeUserRepositoryAdapter, times(2)).findByNameType("ADMIN");
        verify(typeUserRepositoryAdapter).save(any(TypeEntity.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when type name is null or blank")
    void shouldThrowExceptionWhenTypeNameIsNullOrBlank() {
        // Arrange
        TypeUser invalidTypeUser = new TypeUser(1L, null);

        // Act & Assert
        assertThatThrownBy(() -> typeRepositoryPortJpa.create(invalidTypeUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User type cannot be empty.");

        verify(typeUserRepositoryAdapter, never()).findByNameType(any());
    }

    @Test
    @DisplayName("Should update type user successfully when type exists")
    void shouldUpdateTypeUserSuccessfully() {
        // Arrange
        TypeUser updatedTypeUser = new TypeUser(1L, "MODERATOR");
        when(typeUserRepositoryAdapter.findById(anyLong())).thenReturn(Optional.of(typeEntity));
        when(typeUserRepositoryAdapter.save(any(TypeEntity.class))).thenReturn(typeEntity);
        when(typeMapper.toTypeUserEntity(any(TypeEntity.class))).thenReturn(updatedTypeUser);

        // Act
        Optional<TypeUser> result = typeRepositoryPortJpa.update(1L, updatedTypeUser);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getType()).isEqualTo("MODERATOR");
        verify(typeUserRepositoryAdapter).findById(1L);
        verify(typeUserRepositoryAdapter).save(typeEntity);
        verify(typeMapper).toTypeUserEntity(typeEntity);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when trying to update non-existent type")
    void shouldThrowExceptionWhenUpdatingNonExistentType() {
        // Arrange
        when(typeUserRepositoryAdapter.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> typeRepositoryPortJpa.update(999L, typeUser))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User TypeUser not found with id: 999 not found");

        verify(typeUserRepositoryAdapter).findById(999L);
        verify(typeUserRepositoryAdapter, never()).save(any());
    }

    @Test
    @DisplayName("Should delete type user successfully when no users are linked")
    void shouldDeleteTypeUserSuccessfully() {
        // Arrange
        when(userRepositoryAdapter.existsByTypeId(anyLong())).thenReturn(false);
        when(typeUserRepositoryAdapter.findById(anyLong())).thenReturn(Optional.of(typeEntity));
        when(typeMapper.toTypeUserEntity(any(TypeEntity.class))).thenReturn(typeUser);

        // Act
        Optional<TypeUser> result = typeRepositoryPortJpa.deleteById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getType()).isEqualTo("ADMIN");
        verify(userRepositoryAdapter).existsByTypeId(1L);
        verify(typeUserRepositoryAdapter).findById(1L);
        verify(typeUserRepositoryAdapter).delete(typeEntity);
        verify(typeMapper).toTypeUserEntity(typeEntity);
    }

    @Test
    @DisplayName("Should throw UserTypeMismatchException when trying to delete type with linked users")
    void shouldThrowExceptionWhenDeletingTypeWithLinkedUsers() {
        // Arrange
        when(userRepositoryAdapter.existsByTypeId(anyLong())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> typeRepositoryPortJpa.deleteById(1L))
                .isInstanceOf(UserTypeMismatchException.class)
                .hasMessage("It is not possible to delete the type. There are users linked to it.");

        verify(userRepositoryAdapter).existsByTypeId(1L);
        verify(typeUserRepositoryAdapter, never()).findById(any());
        verify(typeUserRepositoryAdapter, never()).delete(any());
    }

    @Test
    @DisplayName("Should return empty when trying to delete non-existent type")
    void shouldReturnEmptyWhenDeletingNonExistentType() {
        // Arrange
        when(userRepositoryAdapter.existsByTypeId(anyLong())).thenReturn(false);
        when(typeUserRepositoryAdapter.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<TypeUser> result = typeRepositoryPortJpa.deleteById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(userRepositoryAdapter).existsByTypeId(999L);
        verify(typeUserRepositoryAdapter).findById(999L);
        verify(typeUserRepositoryAdapter, never()).delete(any());
    }

    @Test
    @DisplayName("Should get type user by id successfully when type exists")
    void shouldGetTypeUserByIdSuccessfully() {
        // Arrange
        when(typeUserRepositoryAdapter.findById(anyLong())).thenReturn(Optional.of(typeEntity));
        when(typeMapper.toTypeUserEntity(any(TypeEntity.class))).thenReturn(typeUser);

        // Act
        Optional<TypeUser> result = typeRepositoryPortJpa.getById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getType()).isEqualTo("ADMIN");
        verify(typeUserRepositoryAdapter).findById(1L);
        verify(typeMapper).toTypeUserEntity(typeEntity);
    }

    @Test
    @DisplayName("Should return empty when getting non-existent type by id")
    void shouldReturnEmptyWhenGettingNonExistentTypeById() {
        // Arrange
        when(typeUserRepositoryAdapter.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<TypeUser> result = typeRepositoryPortJpa.getById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(typeUserRepositoryAdapter).findById(999L);
        verify(typeMapper, never()).toTypeUserEntity(any());
    }

    @Test
    @DisplayName("Should return all type users when getAll is called")
    void shouldReturnAllTypeUsers() {
        // Arrange
        List<TypeEntity> entities = Arrays.asList(typeEntity, typeEntity2);
        TypeUser typeUser2 = new TypeUser(2L, "USER");
        
        when(typeUserRepositoryAdapter.findAll()).thenReturn(entities);
        when(typeMapper.toTypeUserEntity(typeEntity)).thenReturn(typeUser);
        when(typeMapper.toTypeUserEntity(typeEntity2)).thenReturn(typeUser2);

        // Act
        List<TypeUser> result = typeRepositoryPortJpa.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(typeUserRepositoryAdapter).findAll();
        verify(typeMapper, times(2)).toTypeUserEntity(any(TypeEntity.class));
    }

    @Test
    @DisplayName("Should return empty list when no types exist")
    void shouldReturnEmptyListWhenNoTypesExist() {
        // Arrange
        when(typeUserRepositoryAdapter.findAll()).thenReturn(List.of());

        // Act
        List<TypeUser> result = typeRepositoryPortJpa.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(typeUserRepositoryAdapter).findAll();
        verify(typeMapper, never()).toTypeUserEntity(any());
    }
}
