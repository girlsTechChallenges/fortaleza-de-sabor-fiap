package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.ports.in.UserUseCasePort;
import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("UserController Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserUseCasePort userUseCasePort;

    @MockitoBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;
    private UpdateRequestDto updateRequestDto;

    @BeforeEach
    void setUp() {
        Address address = new Address("Rua Teste", "Centro", "Apto 123", 123, "SP", "São Paulo", "12345678");
        user = new User("João Silva", "joao@email.com", "password123", "ADMIN", Arrays.asList(address));
        user.setLogin("joao123");
        user.setDataAlteracao(LocalDate.now());

        AddressDto addressDto = new AddressDto("Rua Teste", "Centro", "Apto 123", 123, "SP", "São Paulo", "12345678");
        userRequestDto = new UserRequestDto("João Silva", "joao@email.com", "joao123", "password123", 
                                          LocalDate.now(), "ADMIN", Arrays.asList(addressDto));

        userResponseDto = new UserResponseDto("João Silva", "joao123", "joao@email.com", "ADMIN", Arrays.asList(addressDto));

        updateRequestDto = new UpdateRequestDto("João Silva Updated", "joao.updated@email.com", "password123", 
                                              "ADMIN", Arrays.asList(addressDto));
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() throws Exception {
        // Arrange
        when(userMapper.toUserDomain(any(UserRequestDto.class))).thenReturn(user);
        when(userUseCasePort.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        // Act & Assert
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.login").value("joao123"));

        verify(userMapper).toUserDomain(any(UserRequestDto.class));
        verify(userUseCasePort).save(any(User.class));
        verify(userMapper).toUserResponseDto(any(User.class));
    }

    @Test
    @DisplayName("Should return validation error when creating user with invalid data")
    void shouldReturnValidationErrorWhenCreatingUserWithInvalidData() throws Exception {
        // Arrange
        UserRequestDto invalidUserDto = new UserRequestDto("", "invalid-email", "", "", 
                                                          null, "", Arrays.asList());

        // Act & Assert
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get all users successfully")
    void shouldGetAllUsersSuccessfully() throws Exception {
        // Arrange
        List<User> users = Arrays.asList(user);
        List<UserResponseDto> userResponseDtos = Arrays.asList(userResponseDto);
        
        when(userUseCasePort.getAll()).thenReturn(users);
        when(userMapper.toUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        // Act & Assert
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("João Silva"))
                .andExpect(jsonPath("$[0].email").value("joao@email.com"));

        verify(userUseCasePort).getAll();
        verify(userMapper).toUserResponseDto(any(User.class));
    }

    @Test
    @DisplayName("Should get user by id successfully")
    void shouldGetUserByIdSuccessfully() throws Exception {
        // Arrange
        when(userUseCasePort.getById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.getUserByIdToUserResponseDto(any(Optional.class))).thenReturn(userResponseDto);

        // Act & Assert
        mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(userUseCasePort).getById(1L);
        verify(userMapper).getUserByIdToUserResponseDto(any(Optional.class));
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() throws Exception {
        // Arrange
        when(userMapper.updateToUserDomain(any(UpdateRequestDto.class))).thenReturn(user);
        when(userUseCasePort.update(anyLong(), any(User.class))).thenReturn(Optional.of(user));
        when(userMapper.getUserByIdToUserResponseDto(any(Optional.class))).thenReturn(userResponseDto);

        // Act & Assert
        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(userMapper).updateToUserDomain(any(UpdateRequestDto.class));
        verify(userUseCasePort).update(anyLong(), any(User.class));
        verify(userMapper).getUserByIdToUserResponseDto(any(Optional.class));
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userUseCasePort).deleteById(1L);
    }

    @Test
    @DisplayName("Should return bad request when creating user without required fields")
    void shouldReturnBadRequestWhenCreatingUserWithoutRequiredFields() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return internal server error when updating user with invalid id format")
    void shouldReturnInternalServerErrorWhenUpdatingUserWithInvalidIdFormat() throws Exception {
        // Act & Assert
        mockMvc.perform(put("/users/invalid-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto)))
                .andExpect(status().isInternalServerError());
    }
}
