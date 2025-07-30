package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.application.usecase.usuario.CreateUserUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.usuario.DeleteUserUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.usuario.GetUserUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.usuario.UpdateUserUseCase;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
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
    private UserEntityMapper userEntityMapper;
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
        userController = new UserController(createUseCase, getUserUseCase,updateUseCase, deleteUseCase,userEntityMapper);
    }

    @Test
    @DisplayName("Should engrave object USER in database - return response HTTP 201 CREATE")
    void shouldCreateUser() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Arrange
        var request = "{\n\t\"name\": \"Lonnie Stanton II\",\n\t\"email\": \"Malvina98@gmail.com\",\n\t\"login\": \"Hardy_Rempel27\",\n\t\"password\": \"RlhllJJPM_sbW02\",\n\t\"changeDate\": \"2025-05-17\",\n\t\"type\": \"DONO\",\n\t\"address\": [\n\t\t{\n\t\t\t\"street\": \"Rua Alves Paulista\",\n\t\t\t\"district\": \"Paulista Nova\",\n\t\t\t\"complement\": \"casa\",\n\t\t\t\"number\": 130,\n\t\t\t\"state\": \"São Paulo\",\n\t\t\t\"city\": \"São Paulo\",\n\t\t\t\"postCode\": 85965000\n\t\t}\n\t]\n}";
        var requestDto = objectMapper.readValue(request, UserRequestDto.class);
        var mapper = userEntityMapper.toUserDomain(requestDto);

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
        when(userEntityMapper.toUserResponseDto(userOne)).thenReturn(responseDtoMockOne());
        when(userEntityMapper.toUserResponseDto(userTwo)).thenReturn(responseDtoMockTwo());
        when(usersRepository.getAll()).thenReturn(List.of(userOne, userTwo));
        when(getUserUseCase.getAll()).thenReturn(List.of(userOne, userTwo));

        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                [
                    { "name": "João Silva" },
                    { "name": "Maria Oliveira" }
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
        when(userEntityMapper.getUserByIdToUserResponseDto(Optional.of(user))).thenReturn(responseDto);

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
                "Name Teste", "email@test.com", "loginTeste", TypeEnum.DONO,
                List.of(new AddressDto("Rua A", "Bairro B", "Comp", 10, "Cidade C", "Estado E", "03565000"))
        );

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(userEntityMapper.updateToUserDomain(dto)).thenReturn(userMockOne());

        // WHEN
        mockMvc.perform(put("/users/1")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // THEN
        verify(updateUseCase, times(1)).update(eq(1L), any(User.class));
    }

}



