package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.typeUser.*;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.TypeUserControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserEntityMapper;
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
public class TypeUserController implements TypeUserControllerDocs {

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody TypeUserRequestDto typeUserDto) {

        log.info("POST USER REQUEST: {} ", typeUserDto);
        TypeUser resp = createUseCase.save(typeUserEntityMapper.toTypeUserDomain(typeUserDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(typeUserEntityMapper.toTypeUserResponseDto(resp));
    }

    @GetMapping("/{idTypeUser}")
    public ResponseEntity getTypeUserByID(
            @PathVariable @NotNull Long idTypeUser
    ) {
        log.info("GET USER BY ID REQUEST {} ", idTypeUser);
        Optional<TypeUser> typeUser = getUseCase.getById(idTypeUser);
        return new ResponseEntity<>(ResponseEntity.status(HttpStatus.ACCEPTED).body(typeUser), HttpStatus.ACCEPTED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TypeUserResponseDto> getAll() {
        log.info("START GET ALL USERS");
        var resp = getUseCase.getAll();
        return resp.stream().map(typeUserEntityMapper::toTypeUserResponseDto).toList();
    }

    @PutMapping(value = "/{idTypeUser}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable @NotNull Long idTypeUser,
            @RequestBody @Valid TypeUserRequestDto typeUserDto
    ) {
        log.info("UPDATE USER REQUEST {} ", typeUserDto);
        updateUseCase.update(idTypeUser, typeUserEntityMapper.toTypeUserDomain(typeUserDto));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{idTypeUser}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idTypeUser) {
        log.info("DELETE USER BY ID REQUEST {}", idTypeUser);
        deleteUseCase.delete(idTypeUser);
        return ResponseEntity.noContent().build();
    }
}
