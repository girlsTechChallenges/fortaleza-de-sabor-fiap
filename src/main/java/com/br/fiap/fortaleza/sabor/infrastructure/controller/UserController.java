package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.*;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final CreateUseCase createUseCase;
    private final GetAllUseCase getAllUseCase;
    private final UpdateUseCase updateUseCase;
    private final GetByIdUseCase getByIdUseCase;
    private final DeleteUseCase deleteUseCase;
    private final UserEntityMapper userEntityMapper;

    public UserController(CreateUseCase createUseCase, GetAllUseCase getAllUseCase, UpdateUseCase updateUseCase, GetByIdUseCase getByIdUseCase, DeleteUseCase deleteUseCase, UserEntityMapper userEntityMapper) {
        this.createUseCase = createUseCase;
        this.getAllUseCase = getAllUseCase;
        this.updateUseCase = updateUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.deleteUseCase = deleteUseCase;
        this.userEntityMapper = userEntityMapper;
    }

    @Operation(summary = "Busca todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return getAllUseCase.getAll();
    }

    @Operation(summary = "Cria um usuário", description = "Cadastrar um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastra um usuário"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody UserRequestDto userRequestDto) {

        log.info("POST USER REQUEST {} ", userRequestDto);
        var rep = createUseCase.save(userEntityMapper.toUserDomain(userRequestDto));

        return new ResponseEntity<>(ResponseEntity.status(HttpStatus.CREATED).body(rep), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um usuário", description = "Permite que o usuário atualize seus dados cadastrados a partir da identificação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na estrutura dos dados"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @RequestParam @NotNull Long idUsuario,
            @RequestBody @Valid User user
    ) {
        log.info("UPDATE USER REQUEST {} ", user);
        Optional<User> updatedUser = updateUseCase.update(idUsuario, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }


    @Operation(summary = "Resgata o usuario por Id", description = "Permite o resgate das informacoes de um usuario especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Usuário localizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity update(
            @RequestParam @NotNull Long idUsuario
    ) {
        log.info("GET USER BY ID REQUEST {} ", idUsuario);
        Optional<User> user = getByIdUseCase.getById(idUsuario);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Deleta o usuário por ID", description = "Permite a exclusão das informações de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> delete(@RequestParam @NotNull Long idUsuario) {
        log.info("DELETE USER BY ID REQUEST {}", idUsuario);

        deleteUseCase.delete(idUsuario);

        return ResponseEntity.noContent().build();
    }


}