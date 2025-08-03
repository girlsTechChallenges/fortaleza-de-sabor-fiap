package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.ports.in.TypeUseCasePort;
import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponse;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("TypeController Tests")
class TypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TypeUseCasePort typeUseCasePort;

    @MockitoBean
    private TypeUserMapper typeUserMapper;

    private ObjectMapper objectMapper;
    private TypeUser typeUser;
    private TypeUserRequestDto typeUserRequest;
    private TypeUserResponse typeUserResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        
        // Setup common test data
        typeUser = new TypeUser(1L, "ADMIN");
        typeUserRequest = new TypeUserRequestDto("ADMIN");
        typeUserResponse = new TypeUserResponse(1L, "ADMIN");
    }

    @Test
    @DisplayName("Deve criar tipo de usuário com sucesso")
    void shouldCreateTypeUserSuccessfully() throws Exception {
        // Given
        String validTypeUserJson = "{\"type\":\"ADMIN\"}";
        TypeUser mockTypeUser = new TypeUser(1L, "ADMIN");
        TypeUserResponse mockResponse = new TypeUserResponse(1L, "ADMIN");

        when(typeUserMapper.toTypeUserDomain(any(TypeUserRequestDto.class))).thenReturn(mockTypeUser);
        when(typeUseCasePort.create(any(TypeUser.class))).thenReturn(mockTypeUser);
        when(typeUserMapper.typeUserResponse(any(TypeUser.class))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/type-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validTypeUserJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/type-users/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameType").value("ADMIN"));

        verify(typeUserMapper).toTypeUserDomain(any(TypeUserRequestDto.class));
        verify(typeUseCasePort).create(any(TypeUser.class));
        verify(typeUserMapper).typeUserResponse(any(TypeUser.class));
    }

    @Test
    @DisplayName("Should return validation error when creating type user with invalid data")
    void shouldReturnValidationErrorWhenCreatingTypeUserWithInvalidData() throws Exception {
        // Arrange - JSON com campo obrigatório vazio
        String invalidTypeUserJson = "{\"type\":\"\"}";

        // Act & Assert
        mockMvc.perform(post("/type-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTypeUserJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return validation error when creating type user with null field")
    void shouldReturnValidationErrorWhenCreatingTypeUserWithNullField() throws Exception {
        // Arrange - JSON com campo nulo
        String nullFieldJson = "{\"type\":null}";

        // Act & Assert
        mockMvc.perform(post("/type-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nullFieldJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should update type user successfully")
    void shouldUpdateTypeUserSuccessfully() throws Exception {
        // Arrange
        Long typeId = 1L;
        String updateJson = objectMapper.writeValueAsString(new TypeUserRequestDto("MODERATOR"));
        TypeUser updatedTypeUser = new TypeUser(typeId, "MODERATOR");
        TypeUserResponse mockResponse = new TypeUserResponse(typeId, "MODERATOR");

        when(typeUserMapper.toTypeUserDomain(any(TypeUserRequestDto.class))).thenReturn(updatedTypeUser);
        when(typeUseCasePort.update(anyLong(), any(TypeUser.class))).thenReturn(Optional.of(updatedTypeUser));
        when(typeUserMapper.typeUserResponse(Optional.of(updatedTypeUser))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(put("/type-users/{id}", typeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameType").value("MODERATOR"));

        verify(typeUserMapper).toTypeUserDomain(any(TypeUserRequestDto.class));
        verify(typeUseCasePort).update(typeId, updatedTypeUser);
        verify(typeUserMapper).typeUserResponse(Optional.of(updatedTypeUser));
    }

    @Test
    @DisplayName("Should return internal server error for invalid update data")
    void shouldReturnInternalServerErrorForInvalidUpdateData() throws Exception {
        // Arrange
        Long typeId = 1L;
        String validUpdateJson = "{\"type\":\"ADMIN\"}";
        
        TypeUser mockTypeUser = new TypeUser(typeId, "ADMIN");
        
        when(typeUserMapper.toTypeUserDomain(any(TypeUserRequestDto.class))).thenReturn(mockTypeUser);
        when(typeUseCasePort.update(anyLong(), any(TypeUser.class)))
                .thenThrow(new RuntimeException("Use case error"));

        // Act & Assert
        mockMvc.perform(put("/type-users/{id}", typeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUpdateJson))
                .andExpect(status().isInternalServerError());

        verify(typeUserMapper).toTypeUserDomain(any(TypeUserRequestDto.class));
        verify(typeUseCasePort).update(typeId, mockTypeUser);
    }

    @Test
    @DisplayName("Should get all type users successfully")
    void shouldGetAllTypeUsersSuccessfully() throws Exception {
        // Arrange
        TypeUser typeUser1 = new TypeUser(1L, "ADMIN");
        TypeUser typeUser2 = new TypeUser(2L, "USER");
        TypeUserResponse response1 = new TypeUserResponse(1L, "ADMIN");
        TypeUserResponse response2 = new TypeUserResponse(2L, "USER");

        when(typeUseCasePort.getAll()).thenReturn(List.of(typeUser1, typeUser2));
        when(typeUserMapper.typeUserResponse(typeUser1)).thenReturn(response1);
        when(typeUserMapper.typeUserResponse(typeUser2)).thenReturn(response2);

        // Act & Assert
        mockMvc.perform(get("/type-users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameType").value("ADMIN"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameType").value("USER"));

        verify(typeUseCasePort).getAll();
        verify(typeUserMapper).typeUserResponse(typeUser1);
        verify(typeUserMapper).typeUserResponse(typeUser2);
    }

    @Test
    @DisplayName("Should return empty list when no type users exist")
    void shouldReturnEmptyListWhenNoTypeUsersExist() throws Exception {
        // Arrange
        when(typeUseCasePort.getAll()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/type-users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(typeUseCasePort).getAll();
    }

    @Test
    @DisplayName("Should get type user by id successfully")
    void shouldGetTypeUserByIdSuccessfully() throws Exception {
        // Arrange
        Long typeId = 1L;
        TypeUser mockTypeUser = new TypeUser(typeId, "ADMIN");
        TypeUserResponse mockResponse = new TypeUserResponse(typeId, "ADMIN");

        when(typeUseCasePort.getById(typeId)).thenReturn(Optional.of(mockTypeUser));
        when(typeUserMapper.typeUserResponse(any(TypeUser.class))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/type-users/{id}", typeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameType").value("ADMIN"));

        verify(typeUseCasePort).getById(typeId);
        verify(typeUserMapper).typeUserResponse(any(TypeUser.class));
    }

    @Test
    @DisplayName("Should return not found when getting type user by non-existent id")
    void shouldReturnNotFoundWhenGettingTypeUserByNonExistentId() throws Exception {
        // Arrange
        Long nonExistentId = 999L;

        when(typeUseCasePort.getById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/type-users/{id}", nonExistentId))
                .andExpect(status().isNotFound());

        verify(typeUseCasePort).getById(nonExistentId);
    }

    @Test
    @DisplayName("Should delete type user successfully")
    void shouldDeleteTypeUserSuccessfully() throws Exception {
        // Arrange
        Long typeId = 1L;
        TypeUser mockTypeUser = new TypeUser(typeId, "ADMIN");

        when(typeUseCasePort.deleteById(typeId)).thenReturn(Optional.of(mockTypeUser));

        // Act & Assert
        mockMvc.perform(delete("/type-users/{id}", typeId))
                .andExpect(status().isNoContent());

        verify(typeUseCasePort).deleteById(typeId);
    }

    @Test
    @DisplayName("Should return success when deleting non-existent type user")
    void shouldReturnSuccessWhenDeletingNonExistentTypeUser() throws Exception {
        // Arrange
        Long nonExistentId = 999L;

        when(typeUseCasePort.deleteById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/type-users/{id}", nonExistentId))
                .andExpect(status().isNoContent());

        verify(typeUseCasePort).deleteById(nonExistentId);
    }

    @Test
    @DisplayName("Should handle different user types successfully")
    void shouldHandleDifferentUserTypesSuccessfully() throws Exception {
        // Arrange
        String customTypeJson = objectMapper.writeValueAsString(new TypeUserRequestDto("FUNCIONARIO"));
        TypeUser mockTypeUser = new TypeUser(3L, "FUNCIONARIO");
        TypeUserResponse mockResponse = new TypeUserResponse(3L, "FUNCIONARIO");

        when(typeUserMapper.toTypeUserDomain(any(TypeUserRequestDto.class))).thenReturn(mockTypeUser);
        when(typeUseCasePort.create(any(TypeUser.class))).thenReturn(mockTypeUser);
        when(typeUserMapper.typeUserResponse(any(TypeUser.class))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/type-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customTypeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nameType").value("FUNCIONARIO"));

        verify(typeUserMapper).toTypeUserDomain(any(TypeUserRequestDto.class));
        verify(typeUseCasePort).create(any(TypeUser.class));
        verify(typeUserMapper).typeUserResponse(any(TypeUser.class));
    }

    @Test
    @DisplayName("Should return internal server error when use case fails")
    void shouldReturnInternalServerErrorWhenUseCaseFails() throws Exception {
        // Arrange
        String validTypeUserJson = objectMapper.writeValueAsString(typeUserRequest);

        when(typeUserMapper.toTypeUserDomain(any(TypeUserRequestDto.class))).thenThrow(new RuntimeException("Use case error"));

        // Act & Assert
        mockMvc.perform(post("/type-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validTypeUserJson))
                .andExpect(status().isInternalServerError());

        verify(typeUserMapper).toTypeUserDomain(any(TypeUserRequestDto.class));
    }
}
