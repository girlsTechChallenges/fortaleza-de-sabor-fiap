package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.typeUser.TypeUserAlreadyRegisteredException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.typeUser.TypeUserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserEntityMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.typeUser.TypeUserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.typeUser.TypeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TypeUserRepositoryJpaTest {

    private TypeUserRepository typeUserRepository;
    private TypeUserEntityMapper mapper;
    private TypeUserRepositoryJpa repositoryJpa;

    @BeforeEach
    void setUp() {
        typeUserRepository = mock(TypeUserRepository.class);
        mapper = mock(TypeUserEntityMapper.class);
        repositoryJpa = new TypeUserRepositoryJpa(typeUserRepository, mapper);
    }

    @Test
    void shouldSaveTypeUserSuccessfully() {
        TypeUser typeUser = new TypeUser("ADMIN");
        TypeUserEntity entity = new TypeUserEntity("ADMIN");

        when(typeUserRepository.getByNameType("ADMIN")).thenReturn(Optional.empty());
        when(mapper.toTypeUserEntity(typeUser)).thenReturn(entity);
        when(typeUserRepository.save(entity)).thenReturn(entity);
        when(mapper.toTypeUserDomain(entity)).thenReturn(typeUser);

        TypeUser result = repositoryJpa.save(typeUser);

        assertNotNull(result);
        assertEquals("ADMIN", result.getNameType());
        verify(typeUserRepository).getByNameType("ADMIN");
        verify(typeUserRepository).save(entity);
    }

    @Test
    void shouldThrowExceptionWhenSavingExistingTypeUser() {
        TypeUser typeUser = new TypeUser("ADMIN");
        TypeUserEntity existing = new TypeUserEntity("ADMIN");

        when(typeUserRepository.getByNameType("ADMIN")).thenReturn(Optional.of(existing));

        assertThrows(TypeUserAlreadyRegisteredException.class, () -> repositoryJpa.save(typeUser));
        verify(typeUserRepository, never()).save(any());
    }

    @Test
    void shouldUpdateTypeUser() {
        Long id = 1L;
        TypeUser typeUser = new TypeUser("CLIENTE");
        TypeUserEntity entity = new TypeUserEntity("ADMIN");
        entity.setId(id);

        when(typeUserRepository.findById(id)).thenReturn(Optional.of(entity));
        when(typeUserRepository.save(entity)).thenReturn(entity);
        when(mapper.toTypeUserDomain(entity)).thenReturn(typeUser);

        Optional<TypeUser> result = repositoryJpa.update(id, typeUser);

        assertTrue(result.isPresent());
        assertEquals("CLIENTE", entity.getType());
        verify(typeUserRepository).save(entity);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingTypeUser() {
        when(typeUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TypeUserNotFoundException.class, () -> repositoryJpa.update(1L, new TypeUser("ADMIN")));
    }

    @Test
    void shouldDeleteTypeUserById() {
        Long id = 1L;
        TypeUserEntity entity = new TypeUserEntity("ADMIN");

        when(typeUserRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toTypeUserDomain(entity)).thenReturn(new TypeUser("ADMIN"));

        Optional<TypeUser> result = repositoryJpa.deleteById(id);

        assertTrue(result.isPresent());
        verify(typeUserRepository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingTypeUser() {
        when(typeUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TypeUserNotFoundException.class, () -> repositoryJpa.deleteById(1L));
    }

    @Test
    void shouldGetById() {
        Long id = 1L;
        TypeUserEntity entity = new TypeUserEntity("CLIENTE");
        when(typeUserRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toTypeUserDomain(entity)).thenReturn(new TypeUser("CLIENTE"));

        Optional<TypeUser> result = repositoryJpa.getById(id);

        assertTrue(result.isPresent());
        assertEquals("CLIENTE", result.get().getNameType());
    }

    @Test
    void shouldThrowWhenGettingNonExistingById() {
        when(typeUserRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TypeUserNotFoundException.class, () -> repositoryJpa.getById(99L));
    }

    @Test
    void shouldGetByNameType() {
        TypeUserEntity entity = new TypeUserEntity("ADMIN");

        when(typeUserRepository.getByNameType("ADMIN")).thenReturn(Optional.of(entity));
        when(mapper.toTypeUserDomain(entity)).thenReturn(new TypeUser("ADMIN"));

        Optional<TypeUser> result = repositoryJpa.getByNameType("ADMIN");

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getNameType());
    }

    @Test
    void shouldGetAllTypeUsers() {
        List<TypeUserEntity> entities = List.of(
            new TypeUserEntity("ADMIN"),
            new TypeUserEntity("CLIENTE")
        );

        when(typeUserRepository.findAll()).thenReturn(entities);
        when(mapper.toTypeUserDomain(entities.get(0))).thenReturn(new TypeUser("ADMIN"));
        when(mapper.toTypeUserDomain(entities.get(1))).thenReturn(new TypeUser("CLIENTE"));

        List<TypeUser> result = repositoryJpa.getAll();

        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getNameType());
        assertEquals("CLIENTE", result.get(1).getNameType());
    }
}
