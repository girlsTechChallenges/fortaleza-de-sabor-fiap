package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.typeUser.*;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserEntityMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/typeUser")
public class TypeUserController {

    private static final Logger log = LoggerFactory.getLogger(TypeUserController.class);
    private final CreateTypeUserUseCase createUseCase;
    private final GetTypeUserUseCase getUseCase;
    private final UpdateTypeUserUseCase updateUseCase;
    private final DeleteTypeUserUseCase deleteUseCase;
    private final TypeUserEntityMapper typeUserEntityMapper;

    public TypeUserController(CreateTypeUserUseCase createUseCase, GetTypeUserUseCase getAllUseCase, UpdateTypeUserUseCase updateUseCase, DeleteTypeUserUseCase deleteUseCase, TypeUserEntityMapper typeUserEntityMapper) {
        this.createUseCase = createUseCase;
        this.getUseCase = getAllUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.typeUserEntityMapper = typeUserEntityMapper;
    }

    @Operation(summary = "Create a typeUser", description = "Register a typeUser.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register a typeUser.", content = @Content(schema = @Schema(implementation = TypeUserRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid field: mandatory criteria were not met.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "TypeUser already registered.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody TypeUserRequestDto typeUserDto) {

        log.info("POST USER REQUEST: {} ", typeUserDto);
        TypeUser resp = createUseCase.save(typeUserEntityMapper.toTypeUserDomain(typeUserDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(typeUserEntityMapper.toTypeUserResponseDto(resp));
    }

    @Operation(summary = "Rescue the typeUser by Id", description = "Allows the retrieval of information from a specific typeUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "TypeUser successfully located", content = @Content(schema = @Schema(implementation = TypeUserRequestDto.class))),
            @ApiResponse(responseCode = "404", description = "TypeUser not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @GetMapping("/{idTypeUser}")
    public ResponseEntity getTypeUserByID(
            @PathVariable @NotNull Long idTypeUser
    ) {
        log.info("GET USER BY ID REQUEST {} ", idTypeUser);
        Optional<TypeUser> typeUser = getUseCase.getById(idTypeUser);
        return new ResponseEntity<>(ResponseEntity.status(HttpStatus.ACCEPTED).body(typeUser), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Search all typeUsers", description = "Returns a list of all typeUsers registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TypeUser list returned successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TypeUserResponseDto> getAll() {
        log.info("START GET ALL USERS");
        var resp = getUseCase.getAll();
        return resp.stream().map(typeUserEntityMapper::toTypeUserResponseDto).toList();
    }

    @Operation(summary = "Update a typeUser", description = "Allows the typeUser to update their registered data from identification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "TypeUser updated successfully", content = @Content(schema = @Schema(implementation = TypeUserRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Data structure error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "TypeUser not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })

    @PutMapping(value = "/{idTypeUser}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable @NotNull Long idTypeUser,
            @RequestBody @Valid TypeUserRequestDto typeUserDto
    ) {
        log.info("UPDATE USER REQUEST {} ", typeUserDto);
        updateUseCase.update(idTypeUser, typeUserEntityMapper.toTypeUserDomain(typeUserDto));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete typeUser by ID", description = "Allows deletion of a specific typeUser's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TypeUser deleted successfully"),
            @ApiResponse(responseCode = "404", description = "TypeUser not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @DeleteMapping("/{idTypeUser}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idTypeUser) {
        log.info("DELETE USER BY ID REQUEST {}", idTypeUser);
        deleteUseCase.delete(idTypeUser);
        return ResponseEntity.noContent().build();
    }
}
