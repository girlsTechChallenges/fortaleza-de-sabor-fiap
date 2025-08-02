//package com.br.fiap.fortaleza.sabor.infrastructure.controller;
//
//import com.br.fiap.fortaleza.sabor.application.usecase.auth.AuthUserUseCase;
//import com.br.fiap.fortaleza.sabor.domain.token.Token;
//import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static com.br.fiap.fortaleza.sabor.mock.MockUser.userAuthDto;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = AuthController.class)
//@ExtendWith(MockitoExtension.class)
//class AuthControllerTest {
//
//    @InjectMocks
//    private AuthController authController;
//    @MockitoBean
//    private AuthUserUseCase authUserUseCase;
//    @MockitoBean
//    private UserMapper userMapper;
//
//    @BeforeEach
//    public void setUp() {
//        authController = new AuthController(authUserUseCase, userMapper);
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("Should return login user")
//    void shouldReturnLoginUser() {
//        // Arrange
//        var request = "{\n\t\"email\":\"email@email.com.br\",\n\t\"password\": \"password\"\n}";
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
//        when(userMapper.toTokenResponseDto(any(Token.class))).thenReturn(userAuthDto());
//
//        // Act & Assert
//        ResultActions result = mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(request))
//                .andExpect(status().isOk());
//
//        // THEN
//        assertNotNull(result);
//        result.andExpect(status().isOk());
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("Should return update password user")
//    void shouldReturnUpdatePasswordUser() {
//        // GIVEN
//        var request = "{\n\t\"email\":\"email@email.com.br\",\n\t\"password\": \"password\"\n}";
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
//
//        // WHEN
//        ResultActions result = mockMvc.perform(patch("/auth/password")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(request))
//                .andExpect(status().isNoContent());
//
//        // THEN
//        assertNotNull(result);
//        result.andExpect(status().isNoContent());
//    }
//}