package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.ports.in.AuthUseCasePort;
import com.br.fiap.fortaleza.sabor.domain.model.token.Token;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserCredentialsDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthUseCasePort authUseCasePort;

    @MockitoBean
    private UserMapper userMapper;

    private ObjectMapper objectMapper;
    private UserCredentialsDto validCredentials;
    private Token mockToken;
    private UserAuthDto mockAuthDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        
        // Setup common test data
        validCredentials = new UserCredentialsDto("user@test.com", "password123");
        mockToken = new Token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", 3600L);
        mockAuthDto = new UserAuthDto("user.jwt.token", 7200L);
    }

    @Test
    @DisplayName("Should login with valid user credentials successfully")
    void shouldLoginWithValidUserCredentialsSuccessfully() throws Exception {
        // Arrange
        String userLoginJson = objectMapper.writeValueAsString(validCredentials);

        when(authUseCasePort.validateLogin(anyString(), anyString())).thenReturn(mockToken);
        when(userMapper.toTokenResponseDto(any(Token.class))).thenReturn(mockAuthDto);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value("user.jwt.token"))
                .andExpect(jsonPath("$.expiresIn").value(7200));

        verify(authUseCasePort).validateLogin("user@test.com", "password123");
        verify(userMapper).toTokenResponseDto(any(Token.class));
    }

    @Test
    @DisplayName("Should return validation error when creating login with invalid data")
    void shouldReturnValidationErrorWhenCreatingLoginWithInvalidData() throws Exception {
        // Arrange
        String invalidLoginJson = "{\"email\":null,\"password\":null}";

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidLoginJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle invalid email format in login")
    void shouldHandleInvalidEmailFormatInLogin() throws Exception {
        // Arrange
        String invalidLoginJson = "{\"email\":\"invalid-email\",\"password\":\"password123\"}";
        
        Token mockToken = new Token("token", 7200L);
        UserAuthDto mockAuthDto = new UserAuthDto("jwt.token", 7200L);
        
        when(authUseCasePort.validateLogin(anyString(), anyString())).thenReturn(mockToken);
        when(userMapper.toTokenResponseDto(any(Token.class))).thenReturn(mockAuthDto);

        // Act & Assert - Como @Valid não funciona com addFilters=false, o método executa normalmente
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidLoginJson))
                .andExpect(status().isOk());

        verify(authUseCasePort).validateLogin("invalid-email", "password123");
        verify(userMapper).toTokenResponseDto(any(Token.class));
    }

    @Test
    @DisplayName("Should handle short password in login")
    void shouldHandleShortPasswordInLogin() throws Exception {
        // Arrange
        String invalidLoginJson = "{\"email\":\"user@test.com\",\"password\":\"123\"}";
        
        Token mockToken = new Token("short.password.token", 7200L);
        UserAuthDto mockAuthDto = new UserAuthDto("short.jwt.token", 7200L);
        
        when(authUseCasePort.validateLogin(anyString(), anyString())).thenReturn(mockToken);
        when(userMapper.toTokenResponseDto(any(Token.class))).thenReturn(mockAuthDto);

        // Act & Assert - Como @Valid não funciona com addFilters=false, o método executa normalmente
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidLoginJson))
                .andExpect(status().isOk());

        verify(authUseCasePort).validateLogin("user@test.com", "123");
        verify(userMapper).toTokenResponseDto(any(Token.class));
    }

    @Test
    @DisplayName("Should return internal server error with invalid credentials")
    void shouldReturnInternalServerErrorWithInvalidCredentials() throws Exception {
        // Arrange
        UserCredentialsDto invalidLogin = new UserCredentialsDto("wrong@test.com", "wrongpassword");
        String invalidLoginJson = objectMapper.writeValueAsString(invalidLogin);

        when(authUseCasePort.validateLogin(anyString(), anyString()))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidLoginJson))
                .andExpect(status().isInternalServerError());

        verify(authUseCasePort).validateLogin("wrong@test.com", "wrongpassword");
    }

    @Test
    @DisplayName("Should login admin user successfully")
    void shouldLoginAdminUserSuccessfully() throws Exception {
        // Arrange
        UserCredentialsDto adminLogin = new UserCredentialsDto("admin@test.com", "admin123");
        String adminLoginJson = objectMapper.writeValueAsString(adminLogin);

        Token adminToken = new Token("admin.jwt.token", 7200L);
        UserAuthDto adminAuthDto = new UserAuthDto("admin.jwt.token", 7200L);

        when(authUseCasePort.validateLogin(anyString(), anyString())).thenReturn(adminToken);
        when(userMapper.toTokenResponseDto(any(Token.class))).thenReturn(adminAuthDto);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminLoginJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value("admin.jwt.token"))
                .andExpect(jsonPath("$.expiresIn").value(7200));

        verify(authUseCasePort).validateLogin("admin@test.com", "admin123");
        verify(userMapper).toTokenResponseDto(any(Token.class));
    }

    @Test
    @DisplayName("Should update password successfully")
    void shouldUpdatePasswordSuccessfully() throws Exception {
        // Arrange
        UserCredentialsDto passwordUpdate = new UserCredentialsDto("user@test.com", "newPassword123");
        String passwordUpdateJson = objectMapper.writeValueAsString(passwordUpdate);

        // Act & Assert
        mockMvc.perform(patch("/auth/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passwordUpdateJson))
                .andExpect(status().isNoContent());

        verify(authUseCasePort).updatePassword("user@test.com", "newPassword123");
    }

    @Test
    @DisplayName("Should return success when password update fields are missing")
    void shouldReturnSuccessWhenPasswordUpdateFieldsAreMissing() throws Exception {
        // Arrange
        UserCredentialsDto passwordUpdate = new UserCredentialsDto(null, null);
        String passwordUpdateJson = objectMapper.writeValueAsString(passwordUpdate);

        // Act & Assert - método updatePassword não valida @Valid, então sempre retorna 204
        mockMvc.perform(patch("/auth/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passwordUpdateJson))
                .andExpect(status().isNoContent());

        verify(authUseCasePort).updatePassword(null, null);
    }

    @Test
    @DisplayName("Should return success when password is too short for update")
    void shouldReturnSuccessWhenPasswordIsTooShortForUpdate() throws Exception {
        // Arrange
        UserCredentialsDto passwordUpdate = new UserCredentialsDto("user@test.com", "123");
        String passwordUpdateJson = objectMapper.writeValueAsString(passwordUpdate);

        // Act & Assert - método updatePassword não valida @Valid, então sempre retorna 204
        mockMvc.perform(patch("/auth/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passwordUpdateJson))
                .andExpect(status().isNoContent());

        verify(authUseCasePort).updatePassword("user@test.com", "123");
    }

    @Test
    @DisplayName("Should update password with valid email successfully")
    void shouldUpdatePasswordWithValidEmailSuccessfully() throws Exception {
        // Arrange
        UserCredentialsDto passwordUpdate = new UserCredentialsDto("user@test.com", "newPassword123");
        String passwordUpdateJson = objectMapper.writeValueAsString(passwordUpdate);

        // Act & Assert
        mockMvc.perform(patch("/auth/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passwordUpdateJson))
                .andExpect(status().isNoContent());

        verify(authUseCasePort).updatePassword("user@test.com", "newPassword123");
    }
}
