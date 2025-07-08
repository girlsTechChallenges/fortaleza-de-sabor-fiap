package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import com.br.fiap.fortaleza.sabor.mock.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryJpaTest {

    @InjectMocks
    UserRepositoryJpa userRepositoryJpa;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        userRepositoryJpa = new UserRepositoryJpa(bCryptPasswordEncoder,userRepository,mapper);
    }

    @Test
    @DisplayName("Service JPA - GetAll Users")
    void getAll() {
        // GIVEN
        List<UserEntity> userEntities = List.of(MockUser.getUserEntityMock(), MockUser.getUserEntityMock());
        List<User> expectedUsers = List.of(MockUser.userMockOne(), MockUser.userMockOne());

        when(userRepository.findAll()).thenReturn(userEntities);
        when(mapper.toUserDomain(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity entity = invocation.getArgument(0);
            return MockUser.userMockOne();
        });

        // WHEN
        List<User> response = userRepositoryJpa.getAll();

        // THEN
        assertNotNull(response);
        assertEquals(expectedUsers.size(), response.size());
        verify(userRepository).findAll();
        verify(mapper, times(userEntities.size())).toUserDomain(any(UserEntity.class));
    }

    @Test
    @DisplayName("Service JPA - Save a user in the database")
    void save() {
        // GIVEN
        User user = MockUser.userMockOne();
        UserEntity userEntity = MockUser.getUserEntityMock();
        UserEntity savedEntity = MockUser.getUserEntityMock();
        User expectedUser = MockUser.userMockOne();

        when(mapper.toUserEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedEntity);
        when(mapper.toUserDomain(savedEntity)).thenReturn(expectedUser);

        // WHEN
        User response = userRepositoryJpa.save(user);

        // THEN
        assertNotNull(response);
        assertEquals(expectedUser, response);
        verify(mapper).toUserEntity(user);
        verify(userRepository).save(userEntity);
        verify(mapper).toUserDomain(savedEntity);
    }

    @Test
    @DisplayName("Should update user successfully.")
    void shouldUpdateUserSuccessfully() {
        User user = new User("Carlos", "carlos@email.com", "login", "novaSenha", null, TypeEnum.CLIENTE, List.of());
        UserEntity userEntity = new UserEntity(1L, "Carlos", "carlos@email.com", "login", "senha", LocalDate.now(), TypeEntityEnum.CLIENTE, List.of());

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any())).thenReturn(userEntity);
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        Optional<User> updated = userRepositoryJpa.update(1L, user);

        assertTrue(updated.isPresent());
        assertEquals("Carlos", updated.get().getNome());
    }

    @Test
    @DisplayName("Should throw exception when user is not found for update.")
    void shouldThrowExceptionWhenUserNotFoundToUpdate() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User user = new User("Carlos", "carlos@email.com", "login", "novaSenha", null, TypeEnum.CLIENTE, List.of());

        RuntimeException exception = assertThrows(UserNotFoundException.class, () -> userRepositoryJpa.update(1L, user));
        assertTrue(exception.getMessage().contains(String.format("User %s not found", 1L)));
    }

    @Test
    @DisplayName("Should find user by ID.")
    void shouldFindUserById() {
        UserEntity userEntity = new UserEntity(1L,"Joana", "joana@email.com", "login", "senha", LocalDate.now(), TypeEntityEnum.DONO, List.of());
        User user = new User("Joana", "joana@email.com", "login", "senha", LocalDate.now(), TypeEnum.DONO, List.of());

        when(userRepository.findById(2L)).thenReturn(Optional.of(userEntity));
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        Optional<User> found = userRepositoryJpa.getById(2L);

        assertTrue(found.isPresent());
        assertEquals("Joana", found.get().getNome());
    }

    @Test
    @DisplayName("Should delete user by ID")
    void shouldDeleteUserById() {
        UserEntity userEntity = new UserEntity(1L, "Pedro", "pedro@email.com", "login", "senha", LocalDate.now(), TypeEntityEnum.CLIENTE, List.of());
        User user = new User("Pedro", "pedro@email.com", "login", "senha", LocalDate.now(), TypeEnum.CLIENTE, List.of());

        when(userRepository.findById(3L)).thenReturn(Optional.of(userEntity));
        doNothing().when(userRepository).deleteById(3L);
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        Optional<User> deleted = userRepositoryJpa.deleteById(3L);

        assertTrue(deleted.isPresent());
        assertEquals("Pedro", deleted.get().getNome());
        verify(userRepository).deleteById(3L);
    }
}