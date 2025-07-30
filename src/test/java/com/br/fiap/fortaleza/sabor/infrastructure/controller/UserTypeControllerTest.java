package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.userType.*;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserTypeRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserTypeResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserTypeEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTypeControllerTest {

    private CreateUserTypeUseCase createUseCase;
    private GetUserTypeUseCase getUseCase;
    private UpdateUserTypeUseCase updateUseCase;
    private DeleteUserTypeUseCase deleteUseCase;
    private UserTypeEntityMapper mapper;

    private UserTypeController controller;

    @BeforeEach
    void setUp() {
        createUseCase = mock(CreateUserTypeUseCase.class);
        getUseCase = mock(GetUserTypeUseCase.class);
        updateUseCase = mock(UpdateUserTypeUseCase.class);
        deleteUseCase = mock(DeleteUserTypeUseCase.class);
        mapper = mock(UserTypeEntityMapper.class);

        controller = new UserTypeController(createUseCase, getUseCase, updateUseCase, deleteUseCase, mapper);
    }

    @Test
    void shouldCreateUserType() {
        // Arrange
        UserTypeRequestDto requestDto = new UserTypeRequestDto("CLIENTE");
        UserType domain = new UserType("CLIENTE");
        UserTypeResponseDto responseDto = new UserTypeResponseDto(1L,"CLIENTE");

        when(mapper.toUserTypeDomain(requestDto)).thenReturn(domain);
        when(createUseCase.save(domain)).thenReturn(domain);
        when(mapper.toUserTypeResponseDto(domain)).thenReturn(responseDto);

        // Act
        ResponseEntity<?> response = controller.create(requestDto);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void shouldGetUserTypeById() {
        // Arrange
        Long id = 1L;
        UserType userType = new UserType("ADMIN");
        when(getUseCase.getById(id)).thenReturn(Optional.of(userType));

        // Act
        ResponseEntity<?> response = controller.getUserTypeByID(id);

        // Assert
        assertEquals(202, response.getStatusCodeValue());
        assertTrue(((ResponseEntity<?>) response.getBody()).getBody() instanceof Optional<?>);
    }

    @Test
    void shouldGetAllUserTypes() {
        // Arrange
        List<UserType> domainList = Arrays.asList(new UserType("ADMIN"), new UserType("CLIENTE"));
        List<UserTypeResponseDto> responseList = Arrays.asList(
                new UserTypeResponseDto(1L,"ADMIN"),
                new UserTypeResponseDto(2L,"CLIENTE")
        );

        when(getUseCase.getAll()).thenReturn(domainList);
        when(mapper.toUserTypeResponseDto(domainList.get(0))).thenReturn(responseList.get(0));
        when(mapper.toUserTypeResponseDto(domainList.get(1))).thenReturn(responseList.get(1));

        // Act
        List<UserTypeResponseDto> result = controller.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).nameType());
        assertEquals("CLIENTE", result.get(1).nameType());
    }

    @Test
    void shouldUpdateUserType() {
        // Arrange
        Long id = 1L;
        UserTypeRequestDto requestDto = new UserTypeRequestDto("CLIENTE");
        UserType domain = new UserType("CLIENTE");

        when(mapper.toUserTypeDomain(requestDto)).thenReturn(domain);
        when(updateUseCase.update(id, domain)).thenReturn(Optional.of(domain));

        // Act
        ResponseEntity<?> response = controller.update(id, requestDto);

        // Assert
        assertEquals(202, response.getStatusCodeValue());
    }

    @Test
    void shouldDeleteUserType() {
        // Arrange
        Long id = 1L;
        when(deleteUseCase.delete(id)).thenReturn(Optional.of(new UserType("CLIENTE")));

        // Act
        ResponseEntity<Void> response = controller.delete(id);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
    }
}
