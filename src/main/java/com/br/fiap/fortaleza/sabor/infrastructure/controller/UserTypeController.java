package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.userType.*;
import com.br.fiap.fortaleza.sabor.domain.userType.UserType;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.UserTypeControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserTypeRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserTypeResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserTypeEntityMapper;
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
@RequestMapping("/userTypes")
public class UserTypeController implements UserTypeControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(UserTypeController.class);
    private final CreateUserTypeUseCase createUseCase;
    private final GetUserTypeUseCase getUseCase;
    private final UpdateUserTypeUseCase updateUseCase;
    private final DeleteUserTypeUseCase deleteUseCase;
    private final UserTypeEntityMapper userTypeEntityMapper;

    public UserTypeController(CreateUserTypeUseCase createUseCase, GetUserTypeUseCase getAllUseCase, UpdateUserTypeUseCase updateUseCase, DeleteUserTypeUseCase deleteUseCase, UserTypeEntityMapper userTypeEntityMapper) {
        this.createUseCase = createUseCase;
        this.getUseCase = getAllUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.userTypeEntityMapper = userTypeEntityMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody UserTypeRequestDto userTypeDto) {

        log.info("POST USER REQUEST: {} ", userTypeDto);
        UserType resp = createUseCase.save(userTypeEntityMapper.toUserTypeDomain(userTypeDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userTypeEntityMapper.toUserTypeResponseDto(resp));
    }

    @GetMapping("/{idUserType}")
    public ResponseEntity getUserTypeByID(
            @PathVariable @NotNull Long idUserType
    ) {
        log.info("GET USER BY ID REQUEST {} ", idUserType);
        Optional<UserType> userType = getUseCase.getById(idUserType);
        return new ResponseEntity<>(ResponseEntity.status(HttpStatus.ACCEPTED).body(userType), HttpStatus.ACCEPTED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserTypeResponseDto> getAll() {
        log.info("START GET ALL USERS");
        var resp = getUseCase.getAll();
        return resp.stream().map(userTypeEntityMapper::toUserTypeResponseDto).toList();
    }

    @PutMapping(value = "/{idUserType}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable @NotNull Long idUserType,
            @RequestBody @Valid UserTypeRequestDto userTypeDto
    ) {
        log.info("UPDATE USER REQUEST {} ", userTypeDto);
        updateUseCase.update(idUserType, userTypeEntityMapper.toUserTypeDomain(userTypeDto));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{idUserType}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idUserType) {
        log.info("DELETE USER BY ID REQUEST {}", idUserType);
        deleteUseCase.delete(idUserType);
        return ResponseEntity.noContent().build();
    }
}
