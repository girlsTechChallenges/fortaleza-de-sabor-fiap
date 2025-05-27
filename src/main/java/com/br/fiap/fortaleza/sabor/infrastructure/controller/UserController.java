package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.CreateUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.DeleteUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.GetUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.UpdateUseCase;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final CreateUseCase createUseCase;
    private final GetUseCase getUseCase;
    private final UpdateUseCase updateUseCase;
    private final DeleteUseCase deleteUseCase;
    private final UserEntityMapper userEntityMapper;

    public UserController(CreateUseCase createUseCase, GetUseCase getAllUseCase, UpdateUseCase updateUseCase, DeleteUseCase deleteUseCase, UserEntityMapper userEntityMapper) {
        this.createUseCase = createUseCase;
        this.getUseCase = getAllUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.userEntityMapper = userEntityMapper;
    }

    @Operation(summary = "Cria um usuário", description = "Cadastrar um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastra um usuário.", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Preenchimento inválido: os critérios obrigatórios não foram satisfeitos.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody UserRequestDto userRequestDto) {

        log.info("POST USER REQUEST: {} ", userRequestDto);
        var resp = createUseCase.save(userEntityMapper.toUserDomain(userRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntityMapper.toUserResponseDto(resp));
    }

    @Operation(summary = "Resgata o usuario por Id", description = "Permite o resgate das informacoes de um usuario especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Usuário localizado com sucesso", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @GetMapping("/{idUser}")
    @PreAuthorize("hasAuthority('SCOPE_DONO')")
    public ResponseEntity getUserByID(
            @PathVariable @NotNull Long idUser
    ) {
        log.info("GET USER BY ID REQUEST {} ", idUser);
        Optional<User> user = getUseCase.getById(idUser);
        return new ResponseEntity<>(ResponseEntity.status(HttpStatus.ACCEPTED).body(userEntityMapper.getUserByIdToUserResponseDto(user)), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso."),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_DONO')")
    public List<UserResponseDto> getAll() {
        log.info("START GET ALL USERS");
        var resp = getUseCase.getAll();
        return resp.stream().map(userEntityMapper::toUserResponseDto).toList();
    }

    @Operation(summary = "Atualiza um usuário", description = "Permite que o usuário atualize seus dados cadastrados a partir da identificação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Usuário atualizado com sucesso", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Erro na estrutura dos dados", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })

    @PutMapping(value = "/{idUser}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable @NotNull Long idUser,
            @RequestBody @Valid UpdateRequestDto updateRequestDto
    ) {
        log.info("UPDATE USER REQUEST {} ", updateRequestDto);
        updateUseCase.update(idUser, userEntityMapper.updateToUserDomain(updateRequestDto));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deleta o usuário por ID", description = "Permite a exclusão das informações de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @DeleteMapping("/{idUser}")
    @PreAuthorize("hasAuthority('SCOPE_DONO')")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idUser) {
        log.info("DELETE USER BY ID REQUEST {}", idUser);
        deleteUseCase.delete(idUser);
        return ResponseEntity.noContent().build();
    }
}