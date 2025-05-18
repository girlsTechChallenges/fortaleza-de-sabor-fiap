package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.application.usecase.CreateUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.GetAllUseCase;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import com.br.fiap.fortaleza.sabor.mock.MockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @MockitoBean
    private UserEntityMapper userEntityMapper;
    @MockitoBean
    private UsersRepository usersRepository;
    @MockitoBean
    private CreateUseCase createUseCase;
    @MockitoBean
    private GetAllUseCase getAllUseCase;

    @BeforeEach
    public void setUp() {
        userController = new UserController(createUseCase,getAllUseCase,userEntityMapper);
    }

    @Test
    @DisplayName("Should engrave object USER in database - return response HTTP 201 CREATE")
    void create() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //GIVEN
        var request = "{ \"nome\": \"string\", \"email\": \"string\", \"login\": \"string\", \"senha\": \"string\", \"dataAlteracao\": \"2025-05-17\", \"tipo\": \"DONO\", \"address\": [{ \"rua\": \"string\", \"bairro\": \"string\", \"complemento\": \"string\", \"numero\": 0, \"estado\": \"string\", \"cidade\": \"string\", \"cep\": 0 }] }";
        var requestDto = objectMapper.readValue(request, UserRequestDto.class);
        var mapper = userEntityMapper.toUserDomain(requestDto);

        //WHEN
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(createUseCase.save(mapper)).thenReturn(MockUser.userMockOne());

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        //THEN
        verify(createUseCase, times(1)).save(mapper);
    }

    @Test
    @DisplayName("Should return a list of users - return response HTTP 200 OK")
    void getAll() throws Exception {
        // GIVEN
        var userOne = MockUser.userMockOne();
        var userTwo = MockUser.userMockTwo();

        // WHEN
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(userEntityMapper.toUserResponseDto(userOne)).thenReturn(MockUser.responseDtoMockOne());
        when(userEntityMapper.toUserResponseDto(userTwo)).thenReturn(MockUser.responseDtoMockTwo());
        when(usersRepository.getAll()).thenReturn(List.of(userOne, userTwo));
        when(getAllUseCase.getAll()).thenReturn(List.of(userOne, userTwo));

        mockMvc.perform(get("/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                [
                    { "nome": "João Silva" },
                    { "nome": "Maria Oliveira" }
                ]
            """));

        // THEN
        verify(getAllUseCase, times(1)).getAll();
    }
}



