package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.application.usecase.user.CreateUserUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.user.DeleteUserUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.user.GetUserUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.user.UpdateUserUseCase;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
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
import java.util.Optional;

import static com.br.fiap.fortaleza.sabor.mock.MockUser.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @MockitoBean
    private UserMapper userMapper;
    @MockitoBean
    private UsersRepository usersRepository;
    @MockitoBean
    private CreateUserUseCase createUseCase;
    @MockitoBean
    private GetUserUseCase getUserUseCase;
    @MockitoBean
    private UpdateUserUseCase updateUseCase;
    @MockitoBean
    private DeleteUserUseCase deleteUseCase;

    @BeforeEach
    public void setUp() {
        userController = new UserController(createUseCase, getUserUseCase,updateUseCase, deleteUseCase, userMapper);
    }

    @Test
    @DisplayName("Should engrave object USER in database - return response HTTP 201 CREATE")
    void shouldCreateUser() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Arrange
        var request = "{\n  \"nome\": \"Lonnie Stanton II\",\n  \"email\": \"Malvina98@gmail.com\",\n  \"login\": \"Hardy_Rempel27\",\n  \"senha\": \"RlhllJJPM_sbW02\",\n  \"dataAlteracao\": \"2025-05-17\",\n  \"tipo\": {\n    \"type\": \"DONO\"\n  },\n  \"address\": [\n    {\n      \"rua\": \"Rua Alves Paulista\",\n      \"bairro\": \"Paulista Nova\",\n      \"complemento\": \"casa\",\n      \"numero\": 130,\n      \"estado\": \"São Paulo\",\n      \"cidade\": \"São Paulo\",\n      \"cep\": 85965000\n    }\n  ]\n}";
        var requestDto = objectMapper.readValue(request, UserRequestDto.class);
        var mapper = userMapper.toUserDomain(requestDto);

        // Act & Assert
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(createUseCase.save(mapper)).thenReturn(userMockOne());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());


        verify(createUseCase, times(1)).save(mapper);
    }

    @Test
    @DisplayName("Should return a list of users - return response HTTP 200 OK")
    void shouldGetAllUsers() throws Exception {
        // GIVEN
        var userOne = userMockOne();
        var userTwo = userMockTwo();

        // WHEN
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(userMapper.toUserResponseDto(userOne)).thenReturn(responseDtoMockOne());
        when(userMapper.toUserResponseDto(userTwo)).thenReturn(responseDtoMockTwo());
        when(usersRepository.getAll()).thenReturn(List.of(userOne, userTwo));
        when(getUserUseCase.getAll()).thenReturn(List.of(userOne, userTwo));

        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                [
                    { "nome": "João Silva" },
                    { "nome": "Maria Oliveira" }
                ]
            """));

        // THEN
        verify(getUserUseCase, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return user by ID - return response HTTP 201 CREATED")
    void shouldGetUserById() throws Exception {
        // GIVEN
        var user = userMockOne();
        var responseDto = responseDtoMockOne();

        // WHEN
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(getUserUseCase.getById(1L)).thenReturn(Optional.of(user));
        when(userMapper.getUserByIdToUserResponseDto(Optional.of(user))).thenReturn(responseDto);

        // THEN
        mockMvc.perform(get("/users/{idUsuario}", 1L)
                        .param("idUsuario", "1"))
                .andExpect(status().isOk());

        verify(getUserUseCase, times(1)).getById(1L);
    }


    @Test
    @DisplayName("Should delete user by ID - return response HTTP 204 NO CONTENT")
    void shouldDeleteUserById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(delete("/users/{idUsuario}", 1L)
                        .param("idUsuario", "1"))
                .andExpect(status().isNoContent());

        verify(deleteUseCase, times(1)).delete(1L);
    }

    @Test
    @DisplayName("Should update user successfully - return HTTP 202 response")
    void shouldUpdateUserSuccessfully() throws Exception {

        // GIVEN
        UpdateRequestDto dto = new UpdateRequestDto(
                "Nome Teste", "email@test.com", "loginTeste", new TypeUserRequestDto("CLIENTE"),
                List.of(new AddressDto("Rua A", "Bairro B", "Comp", 10, "Cidade C", "Estado E", "03565000"))
        );

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(userMapper.updateToUserDomain(dto)).thenReturn(userMockOne());

        // WHEN
        mockMvc.perform(put("/users/1")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // THEN
        verify(updateUseCase, times(1)).update(eq(1L), any(User.class));
    }

}



