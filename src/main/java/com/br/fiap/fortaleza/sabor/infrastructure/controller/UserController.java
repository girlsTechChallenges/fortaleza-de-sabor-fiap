package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.CreateUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.DeleteUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.GetUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.UpdateUseCase;
import com.br.fiap.fortaleza.sabor.domain.user.User;

import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.doc.UserControllerDoc;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;

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
@RequestMapping("/users")
public class UserController implements UserControllerDoc {    private static final Logger log = LoggerFactory.getLogger(UserController.class);
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
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userRequestDto) {

        log.info("POST USER REQUEST: {} ", userRequestDto);
        var resp = createUseCase.save(userEntityMapper.toUserDomain(userRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntityMapper.toUserResponseDto(resp));
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UserResponseDto> getUserByID(
            @PathVariable @NotNull Long idUser
    ) {
        log.info("GET USER BY ID REQUEST {} ", idUser);
        Optional<User> user = getUseCase.getById(idUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userEntityMapper.getUserByIdToUserResponseDto(user));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponseDto> getAll() {
        log.info("START GET ALL USERS");
        var resp = getUseCase.getAll();
        return resp.stream().map(userEntityMapper::toUserResponseDto).toList();
    }

    @PutMapping(value = "/{idUser}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> update(
            @PathVariable @NotNull Long idUser,
            @RequestBody @Valid UpdateRequestDto updateRequestDto
    ) {
        log.info("UPDATE USER REQUEST {} ", updateRequestDto);
        updateUseCase.update(idUser, userEntityMapper.updateToUserDomain(updateRequestDto));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }   
    
    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idUser) {
        log.info("DELETE USER BY ID REQUEST {}", idUser);
        deleteUseCase.delete(idUser);
        return ResponseEntity.noContent().build();
    }
}