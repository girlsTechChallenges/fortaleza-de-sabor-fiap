package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.CreateUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.GetAllUseCase;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final CreateUseCase createUseCase;
    private final GetAllUseCase getAllUseCase;
    private final UserEntityMapper userEntityMapper;

    public UserController(CreateUseCase createUseCase, GetAllUseCase getAllUseCase, UserEntityMapper userEntityMapper) {
        this.createUseCase = createUseCase;
        this.getAllUseCase = getAllUseCase;
        this.userEntityMapper = userEntityMapper;
    }

    @Operation(summary = "Busca todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponseDto> getAll() {
        log.info("START GET ALL USERS");
        var resp = getAllUseCase.getAll();
        return resp.stream().map(userEntityMapper::toUserResponseDto).toList();
    }

    @Operation(summary = "Cria um usuário", description = "Cadastrar um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastra um usuário.", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Preenchimento inválido: os critérios obrigatórios não foram satisfeitos.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody UserRequestDto userRequestDto) {

        log.info("POST USER REQUEST: {} ", userRequestDto);
        var resp = createUseCase.save(userEntityMapper.toUserDomain(userRequestDto));
        return new ResponseEntity<>(ResponseEntity.status(HttpStatus.CREATED).body(userEntityMapper.toUserResponseDto(resp)), HttpStatus.CREATED);
    }
}