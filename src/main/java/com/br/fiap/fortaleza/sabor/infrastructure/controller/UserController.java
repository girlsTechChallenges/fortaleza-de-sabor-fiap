package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.user.CreateUserUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.user.DeleteUserUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.user.GetUserUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.user.UpdateUserUseCase;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.UserControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.*;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.*;
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
public class UserController implements UserControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserEntityMapper userEntityMapper;

    public UserController(CreateUserUseCase createUserUseCase, GetUserUseCase getAllUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase, UserEntityMapper userEntityMapper) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getAllUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.userEntityMapper = userEntityMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("POST USER REQUEST: {} ", userRequestDto);
        var resp = createUserUseCase.save(userEntityMapper.toUserDomain(userRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntityMapper.toUserResponseDto(resp));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseDto>> getAll() {
        log.info("START GET ALL USERS");
        var resp = getUserUseCase.getAll();
        return ResponseEntity.ok(resp.stream().map(userEntityMapper::toUserResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable @NotNull Long id) {
        log.info("GET USER BY ID REQUEST {} ", id);
        Optional<User> user = getUserUseCase.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userEntityMapper.getUserByIdToUserResponseDto(user));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid UpdateRequestDto updateRequestDto) {
        log.info("UPDATE USER REQUEST {} ", updateRequestDto);
        var updatedUser = updateUserUseCase.update(id, userEntityMapper.updateToUserDomain(updateRequestDto));
        return ResponseEntity.ok(userEntityMapper.getUserByIdToUserResponseDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        log.info("DELETE USER BY ID REQUEST {}", id);
        deleteUserUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}