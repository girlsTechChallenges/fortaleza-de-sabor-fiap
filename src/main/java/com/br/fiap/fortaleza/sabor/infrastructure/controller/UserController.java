package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.ports.in.UserUseCasePort;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.UserControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
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
    private final UserUseCasePort userUseCasePort;
    private final UserMapper userMapper;

    public UserController(UserUseCasePort userUseCasePort, UserMapper userMapper) {
        this.userUseCasePort = userUseCasePort;
        this.userMapper = userMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("POST USER REQUEST: {} ", userRequestDto);
        var resp = userUseCasePort.save(userMapper.toUserDomain(userRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponseDto(resp));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseDto>> getAll() {
        log.info("START GET ALL USERS");
        var resp = userUseCasePort.getAll();
        return ResponseEntity.ok(resp.stream().map(userMapper::toUserResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable @NotNull Long id) {
        log.info("GET USER BY ID REQUEST {} ", id);
        Optional<User> user = userUseCasePort.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.getUserByIdToUserResponseDto(user));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid UpdateRequestDto updateRequestDto) {
        log.info("UPDATE USER REQUEST {} ", updateRequestDto);
        var updatedUser = userUseCasePort.update(id, userMapper.updateToUserDomain(updateRequestDto));
        return ResponseEntity.ok(userMapper.getUserByIdToUserResponseDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        log.info("DELETE USER BY ID REQUEST {}", id);
        userUseCasePort.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}