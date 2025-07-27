package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.typeUser.TypeUserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.typeUser.TypeUserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
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
    private UserEntityMapper mapper;
    @Mock
    private TypeUserEntityMapper typeMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private TypeUserRepository typeUserRepository;

    @BeforeEach
    public void setUp() {
        userRepositoryJpa = new UserRepositoryJpa(bCryptPasswordEncoder,userRepository,mapper, typeMapper,typeUserRepository);
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
        TypeUser typeUser = new TypeUser(1L,"CLIENTE");
        TypeUserEntity typeUserEntity = new TypeUserEntity(1L,"CLIENTE");
        User user = new User("Carlos", "carlos@email.com", "login", "novaSenha", null, typeUser, List.of());
        UserEntity userEntity = new UserEntity("Carlos", "carlos@email.com", "login", "senha", LocalDate.now(), typeUserEntity, List.of());

        when(userRepository.findByEmail("carlos@email.com")).thenReturn(Optional.empty());
        when(typeUserRepository.getByNameType("CLIENTE")).thenReturn(Optional.of(typeUserEntity));
        when(typeMapper.toTypeUserDomain(typeUserEntity)).thenReturn(typeUser);
        when(mapper.toUserEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        User resultado = userRepositoryJpa.save(user);

        assertNotNull(resultado);
        assertEquals("carlos@email.com", resultado.getEmail());
        verify(userRepository).save(userEntity);
    }

    @Test
    @DisplayName("Should update user successfully.")
    void shouldUpdateUserSuccessfully() {
        TypeUser typeUser = new TypeUser(1L,"CLIENTE");
        TypeUserEntity typeUserEntity = new TypeUserEntity(1L,"CLIENTE");
        User user = new User("Carlos", "carlos@email.com", "login", "novaSenha", null, typeUser, List.of());
        UserEntity userEntity = new UserEntity("Carlos", "carlos@email.com", "login", "senha", LocalDate.now(), typeUserEntity, List.of());

        when(typeUserRepository.getByNameType("CLIENTE")).thenReturn(Optional.of(typeUserEntity));
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
        TypeUser typeUser = new TypeUser("CLIENTE");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User user = new User("Carlos", "carlos@email.com", "login", "novaSenha", null, typeUser, List.of());

        RuntimeException exception = assertThrows(UserNotFoundException.class, () -> userRepositoryJpa.update(1L, user));
        assertTrue(exception.getMessage().contains(String.format("User %s not found", 1L)));
    }

    @Test
    @DisplayName("Should find user by ID.")
    void shouldFindUserById() {
        TypeUser typeUser = new TypeUser(1L,"DONO");
        TypeUserEntity typeUserEntity = new TypeUserEntity(1L,"DONO");
        UserEntity userEntity = new UserEntity("Joana", "joana@email.com", "login", "senha", LocalDate.now(), typeUserEntity, List.of());
        User user = new User("Joana", "joana@email.com", "login", "senha", LocalDate.now(), typeUser, List.of());

        when(userRepository.findById(2L)).thenReturn(Optional.of(userEntity));
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        Optional<User> found = userRepositoryJpa.getById(2L);

        assertTrue(found.isPresent());
        assertEquals("Joana", found.get().getNome());
    }

    @Test
    @DisplayName("Should delete user by ID")
    void shouldDeleteUserById() {
        TypeUser typeUser = new TypeUser(1L,"CLIENTE");
        TypeUserEntity typeUserEntity = new TypeUserEntity(1L,"CLIENTE");
        UserEntity userEntity = new UserEntity("Pedro", "pedro@email.com", "login", "senha", LocalDate.now(), typeUserEntity, List.of());
        User user = new User("Pedro", "pedro@email.com", "login", "senha", LocalDate.now(), typeUser, List.of());

        when(userRepository.findById(3L)).thenReturn(Optional.of(userEntity));
        doNothing().when(userRepository).deleteById(3L);
        when(mapper.toUserDomain(userEntity)).thenReturn(user);

        Optional<User> deleted = userRepositoryJpa.deleteById(3L);

        assertTrue(deleted.isPresent());
        assertEquals("Pedro", deleted.get().getNome());
        verify(userRepository).deleteById(3L);
    }
}