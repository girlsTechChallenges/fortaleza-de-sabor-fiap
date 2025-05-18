package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import com.br.fiap.fortaleza.sabor.mock.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryJpaTest {

    @InjectMocks
    UserRepositoryJpa userRepositoryJpa;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        userRepositoryJpa = new UserRepositoryJpa(userRepository,mapper);
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
}