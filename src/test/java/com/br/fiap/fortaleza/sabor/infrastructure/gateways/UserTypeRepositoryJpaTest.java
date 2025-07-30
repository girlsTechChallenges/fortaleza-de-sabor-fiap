package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserTypeAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserTypeNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserTypeEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserTypeEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeRepositoryJpaTest {

    private UserTypeRepository userTypeRepository;
    private UserTypeEntityMapper mapper;
    private UserTypeRepositoryJpa repositoryJpa;

    @BeforeEach
    void setUp() {
        userTypeRepository = mock(UserTypeRepository.class);
        mapper = mock(UserTypeEntityMapper.class);
        repositoryJpa = new UserTypeRepositoryJpa(userTypeRepository, mapper);
    }

    @Test
    void shouldSaveUserTypeSuccessfully() {
        UserType userType = new UserType("ADMIN");
        UserTypeEntity entity = new UserTypeEntity(1L,"ADMIN");

        when(userTypeRepository.getByNameType("ADMIN")).thenReturn(Optional.empty());
        when(mapper.toUserTypeEntity(userType)).thenReturn(entity);
        when(userTypeRepository.save(entity)).thenReturn(entity);
        when(mapper.toUserTypeDomain(entity)).thenReturn(userType);

        UserType result = repositoryJpa.save(userType);

        assertNotNull(result);
        assertEquals("ADMIN", result.getNameType());
        verify(userTypeRepository).getByNameType("ADMIN");
        verify(userTypeRepository).save(entity);
    }

    @Test
    void shouldThrowExceptionWhenSavingExistingUserType() {
        UserType userType = new UserType("ADMIN");
        UserTypeEntity existing = new UserTypeEntity(1L,"ADMIN");

        when(userTypeRepository.getByNameType("ADMIN")).thenReturn(Optional.of(existing));

        assertThrows(UserTypeAlreadyRegisteredException.class, () -> repositoryJpa.save(userType));
        verify(userTypeRepository, never()).save(any());
    }

    @Test
    void shouldUpdateUserType() {
        Long id = 1L;
        UserType userType = new UserType("CLIENTE");
        UserTypeEntity entity = new UserTypeEntity(1L, "ADMIN");
        entity.setId(id);

        when(userTypeRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userTypeRepository.save(entity)).thenReturn(entity);
        when(mapper.toUserTypeDomain(entity)).thenReturn(userType);

        Optional<UserType> result = repositoryJpa.update(id, userType);

        assertTrue(result.isPresent());
        assertEquals("CLIENTE", entity.getType());
        verify(userTypeRepository).save(entity);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingUserType() {
        when(userTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> repositoryJpa.update(1L, new UserType("ADMIN")));
    }

    @Test
    void shouldDeleteUserTypeById() {
        Long id = 1L;
        UserTypeEntity entity = new UserTypeEntity(1L,"ADMIN");

        when(userTypeRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toUserTypeDomain(entity)).thenReturn(new UserType("ADMIN"));

        Optional<UserType> result = repositoryJpa.deleteById(id);

        assertTrue(result.isPresent());
        verify(userTypeRepository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingUserType() {
        when(userTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> repositoryJpa.deleteById(1L));
    }

    @Test
    void shouldGetById() {
        Long id = 1L;
        UserTypeEntity entity = new UserTypeEntity(1L,"CLIENTE");
        when(userTypeRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toUserTypeDomain(entity)).thenReturn(new UserType("CLIENTE"));

        Optional<UserType> result = repositoryJpa.getById(id);

        assertTrue(result.isPresent());
        assertEquals("CLIENTE", result.get().getNameType());
    }

    @Test
    void shouldThrowWhenGettingNonExistingById() {
        when(userTypeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> repositoryJpa.getById(99L));
    }

    @Test
    void shouldGetByNameType() {
        UserTypeEntity entity = new UserTypeEntity(1L,"ADMIN");

        when(userTypeRepository.getByNameType("ADMIN")).thenReturn(Optional.of(entity));
        when(mapper.toUserTypeDomain(entity)).thenReturn(new UserType("ADMIN"));

        Optional<UserType> result = repositoryJpa.getByNameType("ADMIN");

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getNameType());
    }

    @Test
    void shouldGetAllUserTypes() {
        List<UserTypeEntity> entities = List.of(
            new UserTypeEntity(1L,"ADMIN"),
            new UserTypeEntity(1L,"CLIENTE")
        );

        when(userTypeRepository.findAll()).thenReturn(entities);
        when(mapper.toUserTypeDomain(entities.get(0))).thenReturn(new UserType("ADMIN"));
        when(mapper.toUserTypeDomain(entities.get(1))).thenReturn(new UserType("CLIENTE"));

        List<UserType> result = repositoryJpa.getAll();

        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getNameType());
        assertEquals("CLIENTE", result.get(1).getNameType());
    }
}
