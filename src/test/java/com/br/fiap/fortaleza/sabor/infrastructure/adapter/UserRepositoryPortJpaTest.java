package com.br.fiap.fortaleza.sabor.infrastructure.adapter;

import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeUserRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserRepositoryPortJpa Tests")
class UserRepositoryPortJpaTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserRepositoryAdapter userRepositoryAdapter;

    @Mock
    private TypeUserRepositoryAdapter typeUserRepositoryAdapter;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserRepositoryPortJpa userRepositoryPortJpa;

    private User user;
    private UserEntity userEntity;
    private TypeEntity typeEntity;
    private final String email = "joao@test.com";
    private final String encodedPassword = "$2a$10$encodedPasswordHash";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setNome("João Silva");
        user.setEmail(email);
        user.setLogin("joao123");
        user.setSenha("password123");
        user.setDataAlteracao(LocalDate.now());
        user.setTipo("CLIENTE");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setNome("João Silva");
        userEntity.setEmail(email);
        userEntity.setLogin("joao123");
        userEntity.setSenha(encodedPassword);

        typeEntity = new TypeEntity();
        typeEntity.setId(1L);
        typeEntity.setNameType("CLIENTE");
    }

    @Test
    @DisplayName("Should return all users when getAll is called")
    void shouldReturnAllUsersWhenGetAllIsCalled() {
        // Arrange
        List<UserEntity> userEntities = Arrays.asList(userEntity);
        when(userRepositoryAdapter.findAll()).thenReturn(userEntities);
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        // Act
        List<User> result = userRepositoryPortJpa.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(user);
        verify(userRepositoryAdapter, times(1)).findAll();
        verify(mapper, times(1)).toUserDomain(userEntity);
    }

    @Test
    @DisplayName("Should save user successfully when save is called with valid user")
    void shouldSaveUserSuccessfullyWhenSaveIsCalledWithValidUser() {
        // Arrange
        when(typeUserRepositoryAdapter.findByNameType("CLIENTE")).thenReturn(Optional.of(typeEntity));
        when(userRepositoryAdapter.findByEmail(email)).thenReturn(Optional.empty());
        when(mapper.toUserEntity(user)).thenReturn(userEntity);
        when(userRepositoryAdapter.save(userEntity)).thenReturn(userEntity);
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        // Act
        User result = userRepositoryPortJpa.save(user);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(user);
        verify(typeUserRepositoryAdapter, times(1)).findByNameType("CLIENTE");
        verify(userRepositoryAdapter, times(1)).findByEmail(email);
        verify(mapper, times(1)).toUserEntity(user);
        verify(userRepositoryAdapter, times(1)).save(userEntity);
        verify(mapper, times(1)).toUserDomain(userEntity);
    }

    @Test
    @DisplayName("Should throw UserAlreadyRegisteredException when save is called with existing email")
    void shouldThrowUserAlreadyRegisteredExceptionWhenSaveIsCalledWithExistingEmail() {
        // Arrange
        when(typeUserRepositoryAdapter.findByNameType("CLIENTE")).thenReturn(Optional.of(typeEntity));
        when(userRepositoryAdapter.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // Act & Assert
        assertThatThrownBy(() -> userRepositoryPortJpa.save(user))
                .isInstanceOf(UserAlreadyRegisteredException.class)
                .hasMessage("This user already exists. Check your credentials or recover your password.");
        
        verify(typeUserRepositoryAdapter, times(1)).findByNameType("CLIENTE");
        verify(userRepositoryAdapter, times(1)).findByEmail(email);
        verify(userRepositoryAdapter, never()).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should create new type when save is called with non-existing type")
    void shouldCreateNewTypeWhenSaveIsCalledWithNonExistingType() {
        // Arrange
        user.setTipo("NOVO_TIPO");
        TypeEntity savedTypeEntity = new TypeEntity(2L, "NOVO_TIPO");

        when(typeUserRepositoryAdapter.findByNameType("NOVO_TIPO")).thenReturn(Optional.empty());
        when(typeUserRepositoryAdapter.findAll()).thenReturn(Arrays.asList());
        when(typeUserRepositoryAdapter.save(any(TypeEntity.class))).thenReturn(savedTypeEntity);
        when(userRepositoryAdapter.findByEmail(email)).thenReturn(Optional.empty());
        when(mapper.toUserEntity(user)).thenReturn(userEntity);
        when(userRepositoryAdapter.save(userEntity)).thenReturn(userEntity);
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        // Act
        User result = userRepositoryPortJpa.save(user);

        // Assert
        assertThat(result).isNotNull();
        verify(typeUserRepositoryAdapter, times(1)).findByNameType("NOVO_TIPO");
        verify(typeUserRepositoryAdapter, times(1)).findAll();
        verify(typeUserRepositoryAdapter, times(1)).save(any(TypeEntity.class));
        verify(userRepositoryAdapter, times(1)).save(userEntity);
    }

    @Test
    @DisplayName("Should update user successfully when update is called with valid id")
    void shouldUpdateUserSuccessfullyWhenUpdateIsCalledWithValidId() {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setNome("João Silva Updated");
        updatedUser.setEmail("joao.updated@test.com");
        updatedUser.setSenha("newpassword");
        updatedUser.setTipo("ADMIN");

        when(userRepositoryAdapter.findById(userId)).thenReturn(Optional.of(userEntity));
        when(typeUserRepositoryAdapter.findByNameType("ADMIN")).thenReturn(Optional.of(typeEntity));
        when(userRepositoryAdapter.save(any(UserEntity.class))).thenReturn(userEntity);
        when(mapper.toUserDomain(userEntity)).thenReturn(updatedUser);

        // Act
        Optional<User> result = userRepositoryPortJpa.update(userId, updatedUser);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(updatedUser);
        verify(userRepositoryAdapter, times(1)).findById(userId);
        verify(typeUserRepositoryAdapter, times(1)).findByNameType("ADMIN");
        verify(userRepositoryAdapter, times(1)).save(any(UserEntity.class));
        verify(mapper, times(1)).toUserDomain(userEntity);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when update is called with invalid id")
    void shouldThrowUserNotFoundExceptionWhenUpdateIsCalledWithInvalidId() {
        // Arrange
        Long invalidUserId = 999L;
        when(userRepositoryAdapter.findById(invalidUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userRepositoryPortJpa.update(invalidUserId, user))
                .isInstanceOf(UserNotFoundException.class);
        
        verify(userRepositoryAdapter, times(1)).findById(invalidUserId);
        verify(userRepositoryAdapter, never()).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should get user by id when getById is called with valid id")
    void shouldGetUserByIdWhenGetByIdIsCalledWithValidId() {
        // Arrange
        Long userId = 1L;
        when(userRepositoryAdapter.findById(userId)).thenReturn(Optional.of(userEntity));
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        // Act
        Optional<User> result = userRepositoryPortJpa.getById(userId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
        verify(userRepositoryAdapter, times(1)).findById(userId);
        verify(mapper, times(1)).toUserDomain(userEntity);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when getById is called with invalid id")
    void shouldThrowUserNotFoundExceptionWhenGetByIdIsCalledWithInvalidId() {
        // Arrange
        Long invalidUserId = 999L;
        when(userRepositoryAdapter.findById(invalidUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userRepositoryPortJpa.getById(invalidUserId))
                .isInstanceOf(UserNotFoundException.class);
        
        verify(userRepositoryAdapter, times(1)).findById(invalidUserId);
        verify(mapper, never()).toUserDomain(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should delete user by id when deleteById is called with valid id")
    void shouldDeleteUserByIdWhenDeleteByIdIsCalledWithValidId() {
        // Arrange
        Long userId = 1L;
        when(userRepositoryAdapter.findById(userId)).thenReturn(Optional.of(userEntity));
        when(mapper.toUserDomain(userEntity)).thenReturn(user);
        doNothing().when(userRepositoryAdapter).deleteById(userId);

        // Act
        Optional<User> result = userRepositoryPortJpa.deleteById(userId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
        verify(userRepositoryAdapter, times(2)).findById(userId); // Called twice: once for validation, once for return
        verify(userRepositoryAdapter, times(1)).deleteById(userId);
        verify(mapper, times(1)).toUserDomain(userEntity);
    }

    @Test
    @DisplayName("Should find user by email when findByEmail is called with valid email")
    void shouldFindUserByEmailWhenFindByEmailIsCalledWithValidEmail() {
        // Arrange
        when(userRepositoryAdapter.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        // Act
        Optional<User> result = userRepositoryPortJpa.findByEmail(email);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
        verify(userRepositoryAdapter, times(1)).findByEmail(email);
        verify(mapper, times(1)).toUserDomain(userEntity);
    }

    @Test
    @DisplayName("Should return empty optional when findByEmail is called with non-existing email")
    void shouldReturnEmptyOptionalWhenFindByEmailIsCalledWithNonExistingEmail() {
        // Arrange
        String nonExistingEmail = "nonexisting@test.com";
        when(userRepositoryAdapter.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userRepositoryPortJpa.findByEmail(nonExistingEmail);

        // Assert
        assertThat(result).isEmpty();
        verify(userRepositoryAdapter, times(1)).findByEmail(nonExistingEmail);
        verify(mapper, never()).toUserDomain(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should update password successfully when updatePassword is called with valid email")
    void shouldUpdatePasswordSuccessfullyWhenUpdatePasswordIsCalledWithValidEmail() {
        // Arrange
        String newPassword = "newpassword123";
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        when(userRepositoryAdapter.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userRepositoryAdapter.save(userEntity)).thenReturn(userEntity);

        // Act
        userRepositoryPortJpa.updatePassword(email, newPassword);

        // Assert
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepositoryAdapter, times(1)).findByEmail(email);
        verify(userRepositoryAdapter, times(1)).save(userEntity);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when updatePassword is called with invalid email")
    void shouldThrowUserNotFoundExceptionWhenUpdatePasswordIsCalledWithInvalidEmail() {
        // Arrange
        String invalidEmail = "invalid@test.com";
        String newPassword = "newpassword123";
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        when(userRepositoryAdapter.findByEmail(invalidEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userRepositoryPortJpa.updatePassword(invalidEmail, newPassword))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(String.format("User %s not found", invalidEmail));
        
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepositoryAdapter, times(1)).findByEmail(invalidEmail);
        verify(userRepositoryAdapter, never()).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should handle type case insensitivity correctly")
    void shouldHandleTypeCaseInsensitivityCorrectly() {
        // Arrange
        user.setTipo("cliente"); // lowercase
        when(typeUserRepositoryAdapter.findByNameType("CLIENTE")).thenReturn(Optional.of(typeEntity));
        when(userRepositoryAdapter.findByEmail(email)).thenReturn(Optional.empty());
        when(mapper.toUserEntity(user)).thenReturn(userEntity);
        when(userRepositoryAdapter.save(userEntity)).thenReturn(userEntity);
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        // Act
        User result = userRepositoryPortJpa.save(user);

        // Assert
        assertThat(result).isNotNull();
        verify(typeUserRepositoryAdapter, times(1)).findByNameType("CLIENTE");
    }

    @Test
    @DisplayName("Should handle email case insensitivity during validation")
    void shouldHandleEmailCaseInsensitivityDuringValidation() {
        // Arrange
        String upperCaseEmail = "JOAO@TEST.COM";
        user.setEmail(upperCaseEmail);
        
        when(typeUserRepositoryAdapter.findByNameType("CLIENTE")).thenReturn(Optional.of(typeEntity));
        when(userRepositoryAdapter.findByEmail("joao@test.com")).thenReturn(Optional.of(userEntity));

        // Act & Assert
        assertThatThrownBy(() -> userRepositoryPortJpa.save(user))
                .isInstanceOf(UserAlreadyRegisteredException.class);
        
        verify(userRepositoryAdapter, times(1)).findByEmail("joao@test.com");
    }
}
