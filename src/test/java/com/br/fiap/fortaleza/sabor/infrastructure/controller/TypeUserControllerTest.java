package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.typeUser.*;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TypeUserControllerTest {

    private CreateTypeUserUseCase createUseCase;
    private GetTypeUserUseCase getUseCase;
    private UpdateTypeUserUseCase updateUseCase;
    private DeleteTypeUserUseCase deleteUseCase;
    private TypeUserEntityMapper mapper;

    private TypeUserController controller;

    @BeforeEach
    void setUp() {
        createUseCase = mock(CreateTypeUserUseCase.class);
        getUseCase = mock(GetTypeUserUseCase.class);
        updateUseCase = mock(UpdateTypeUserUseCase.class);
        deleteUseCase = mock(DeleteTypeUserUseCase.class);
        mapper = mock(TypeUserEntityMapper.class);

        controller = new TypeUserController(createUseCase, getUseCase, updateUseCase, deleteUseCase, mapper);
    }

    @Test
    void shouldCreateTypeUser() {
        // Arrange
        TypeUserRequestDto requestDto = new TypeUserRequestDto("CLIENTE");
        TypeUser domain = new TypeUser("CLIENTE");
        TypeUserResponseDto responseDto = new TypeUserResponseDto(1L,"CLIENTE");

        when(mapper.toTypeUserDomain(requestDto)).thenReturn(domain);
        when(createUseCase.save(domain)).thenReturn(domain);
        when(mapper.toTypeUserResponseDto(domain)).thenReturn(responseDto);

        // Act
        ResponseEntity<?> response = controller.create(requestDto);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void shouldGetTypeUserById() {
        // Arrange
        Long id = 1L;
        TypeUser typeUser = new TypeUser("ADMIN");
        when(getUseCase.getById(id)).thenReturn(Optional.of(typeUser));

        // Act
        ResponseEntity<?> response = controller.getTypeUserByID(id);

        // Assert
        assertEquals(202, response.getStatusCodeValue());
        assertTrue(((ResponseEntity<?>) response.getBody()).getBody() instanceof Optional<?>);
    }

    @Test
    void shouldGetAllTypeUsers() {
        // Arrange
        List<TypeUser> domainList = Arrays.asList(new TypeUser("ADMIN"), new TypeUser("CLIENTE"));
        List<TypeUserResponseDto> responseList = Arrays.asList(
                new TypeUserResponseDto(1L,"ADMIN"),
                new TypeUserResponseDto(2L,"CLIENTE")
        );

        when(getUseCase.getAll()).thenReturn(domainList);
        when(mapper.toTypeUserResponseDto(domainList.get(0))).thenReturn(responseList.get(0));
        when(mapper.toTypeUserResponseDto(domainList.get(1))).thenReturn(responseList.get(1));

        // Act
        List<TypeUserResponseDto> result = controller.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).nameType());
        assertEquals("CLIENTE", result.get(1).nameType());
    }

    @Test
    void shouldUpdateTypeUser() {
        // Arrange
        Long id = 1L;
        TypeUserRequestDto requestDto = new TypeUserRequestDto("CLIENTE");
        TypeUser domain = new TypeUser("CLIENTE");

        when(mapper.toTypeUserDomain(requestDto)).thenReturn(domain);
        when(updateUseCase.update(id, domain)).thenReturn(Optional.of(domain));

        // Act
        ResponseEntity<?> response = controller.update(id, requestDto);

        // Assert
        assertEquals(202, response.getStatusCodeValue());
    }

    @Test
    void shouldDeleteTypeUser() {
        // Arrange
        Long id = 1L;
        when(deleteUseCase.delete(id)).thenReturn(Optional.of(new TypeUser("CLIENTE")));

        // Act
        ResponseEntity<Void> response = controller.delete(id);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
    }
}
