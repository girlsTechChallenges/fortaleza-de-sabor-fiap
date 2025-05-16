package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryJpaTest {

    @Mock
    private UserRepository userRepository;  // Mock do UserRepository

    @Mock
    private UserEntityMapper mapper;  // Mock do UserEntityMapper

    @InjectMocks
    private UserRepositoryJpa userRepositoryJpa;  // A classe a ser testada

    private User mockUser;
    private UserEntity mockUserEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Preparando dados mockados
        mockUser = new User("John Doe", "john@example.com", "john123", "password123", LocalDate.now(), null, null);
        mockUserEntity = new UserEntity("John Doe", "john@example.com", "john123", "password123", LocalDate.now(), null, null);
    }

    @Test
    void testGetAll() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(mockUserEntity));
        when(mapper.toUserDomain(mockUserEntity)).thenReturn(mockUser);

        // Act
        var result = userRepositoryJpa.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getNome());
    }

    @Test
    void testSave() {
        // Arrange
        when(mapper.toUserEntity(mockUser)).thenReturn(mockUserEntity);
        when(userRepository.save(mockUserEntity)).thenReturn(mockUserEntity);
        when(mapper.toUserDomain(mockUserEntity)).thenReturn(mockUser);

        // Act
        User result = userRepositoryJpa.save(mockUser);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getNome());
        verify(userRepository, times(1)).save(mockUserEntity);
    }

    @Test
    void testUpdate_UserFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));
        when(mapper.toAddressEntityList(mockUser.getAddress())).thenReturn(mockUserEntity.getEnderecos());
        when(userRepository.save(mockUserEntity)).thenReturn(mockUserEntity);
        when(mapper.toUserDomain(mockUserEntity)).thenReturn(mockUser);

        // Act
        var result = userRepositoryJpa.update(userId, mockUser);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getNome());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(mockUserEntity);
    }

    @Test
    void testUpdate_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRepositoryJpa.update(userId, mockUser));
        assertEquals("Usuário não encontrado com id: " + userId, exception.getMessage());
    }

    @Test
    void testGetById_UserFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));
        when(mapper.toUserDomain(mockUserEntity)).thenReturn(mockUser);

        // Act
        var result = userRepositoryJpa.getById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getNome());
    }

    @Test
    void testGetById_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRepositoryJpa.getById(userId));
        assertEquals("Usuário não encontrado com id: " + userId, exception.getMessage());
    }

    @Test
    void testDeleteById_UserFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));
        when(mapper.toUserDomain(mockUserEntity)).thenReturn(mockUser);

        // Act
        var result = userRepositoryJpa.deleteById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getNome());
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteById_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> userRepositoryJpa.deleteById(userId));
        verify(userRepository, times(1)).deleteById(userId);
    }
}
