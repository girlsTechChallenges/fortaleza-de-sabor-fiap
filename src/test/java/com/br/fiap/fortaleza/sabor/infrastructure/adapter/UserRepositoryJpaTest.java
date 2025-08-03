package com.br.fiap.fortaleza.sabor.infrastructure.adapter;

import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeUserRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryPortJpaTest {

   @InjectMocks
   UserRepositoryPortJpa UserRepositoryPortJpa;
   @Mock
   private UserRepositoryAdapter UserRepositoryAdapter;
   @Mock
   private TypeUserRepositoryAdapter TypeUserRepositoryAdapter;
   @Mock
   private UserMapper mapper;
   @Mock
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   @BeforeEach
   public void setUp() {
       UserRepositoryPortJpa = new UserRepositoryPortJpa(bCryptPasswordEncoder,UserRepositoryAdapter,TypeUserRepositoryAdapter, mapper);
   }

   @Test
   @DisplayName("Service JPA - GetAll Users")
   void shouldGetAllUsers() {
       // GIVEN
       List<UserEntity> userEntities = List.of(MockUser.getUserEntityMock(), MockUser.getUserEntityMock());
       List<User> expectedUsers = List.of(MockUser.userMockOne(), MockUser.userMockOne());

       when(UserRepositoryAdapter.findAll()).thenReturn(userEntities);
       when(mapper.toUserDomain(any(UserEntity.class))).thenAnswer(invocation -> {
           return MockUser.userMockOne();
       });

       // WHEN
       List<User> response = UserRepositoryPortJpa.getAll();

       // THEN
       assertNotNull(response);
       assertEquals(expectedUsers.size(), response.size());
       verify(UserRepositoryAdapter).findAll();
       verify(mapper, times(userEntities.size())).toUserDomain(any(UserEntity.class));
   }

   @Test
   @DisplayName("Service JPA - Save a user in the database")
   void shouldSaveUser() {
       // GIVEN
       User user = MockUser.userMockOne();
       UserEntity userEntity = MockUser.getUserEntityMock();
       UserEntity savedEntity = MockUser.getUserEntityMock();
       User expectedUser = MockUser.userMockOne();

       when(mapper.toUserEntity(user)).thenReturn(userEntity);
       when(UserRepositoryAdapter.save(userEntity)).thenReturn(savedEntity);
       when(mapper.toUserDomain(savedEntity)).thenReturn(expectedUser);

       // WHEN
       User response = UserRepositoryPortJpa.save(user);

       // THEN
       assertNotNull(response);
       assertEquals(expectedUser, response);
       verify(mapper).toUserEntity(user);
       verify(UserRepositoryAdapter).save(userEntity);
       verify(mapper).toUserDomain(savedEntity);
   }

   @Test
   @DisplayName("Should update user successfully.")
   void shouldUpdateUserSuccessfully() {
       User user = new User("Carlos", "carlos@email.com", "novaSenha", "CLIENTE", List.of());
       UserEntity userEntity = new UserEntity(1L, "Carlos", "carlos@email.com", "login", "senha", LocalDate.now(), new TypeEntity(1L, "CLIENTE"), List.of());

       when(UserRepositoryAdapter.findById(1L)).thenReturn(Optional.of(userEntity));
       when(UserRepositoryAdapter.save(any())).thenReturn(userEntity);
       when(mapper.toUserDomain(userEntity)).thenReturn(user);

       Optional<User> updated = UserRepositoryPortJpa.update(1L, user);

       assertTrue(updated.isPresent());
       assertEquals("Carlos", updated.get().getNome());
   }

   @Test
   @DisplayName("Should throw exception when user is not found for update.")
   void shouldThrowExceptionWhenUserNotFoundToUpdate() {
       when(UserRepositoryAdapter.findById(1L)).thenReturn(Optional.empty());

       User user = new User("User", "email@test.com", "senha", "CLIENTE", List.of());

       RuntimeException exception = assertThrows(UserNotFoundException.class, () -> UserRepositoryPortJpa.update(1L, user));
       assertTrue(exception.getMessage().contains(String.format("User %s not found", 1L)));
   }

   @Test
   @DisplayName("Should find user by ID.")
   void shouldFindUserById() {
       UserEntity userEntity = new UserEntity(1L,"Joana", "joana@email.com", "login", "senha", LocalDate.now(), new TypeEntity(1L, "DONO"), List.of());
       User user = new User("Joana", "joana@email.com", "senha", "DONO", List.of());

       when(UserRepositoryAdapter.findById(2L)).thenReturn(Optional.of(userEntity));
       when(mapper.toUserDomain(userEntity)).thenReturn(user);

       Optional<User> found = UserRepositoryPortJpa.getById(2L);

       assertTrue(found.isPresent());
       assertEquals("Joana", found.get().getNome());
   }

   @Test
   @DisplayName("Should delete user by ID")
   void shouldDeleteUserById() {
       UserEntity userEntity = new UserEntity(1L, "Pedro", "pedro@email.com", "login", "senha", LocalDate.now(), new TypeEntity(1L, "CLIENTE"), List.of());
       User user = new User("Pedro", "pedro@email.com", "senha", "CLIENTE", List.of());

       when(UserRepositoryAdapter.findById(3L)).thenReturn(Optional.of(userEntity));
       doNothing().when(UserRepositoryAdapter).deleteById(3L);
       when(mapper.toUserDomain(userEntity)).thenReturn(user);

       Optional<User> deleted = UserRepositoryPortJpa.deleteById(3L);

       assertTrue(deleted.isPresent());
       assertEquals("Pedro", deleted.get().getNome());
       verify(UserRepositoryAdapter).deleteById(3L);
   }
}
