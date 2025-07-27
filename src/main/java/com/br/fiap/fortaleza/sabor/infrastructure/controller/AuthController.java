package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.user.AuthUserUseCase;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.AuthControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserCredentialsDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthUserUseCase authUserUseCase;
    private final UserEntityMapper userEntityMapper;

    public AuthController(AuthUserUseCase authUserUseCase, UserEntityMapper userEntityMapper) {
        this.authUserUseCase = authUserUseCase;
        this.userEntityMapper = userEntityMapper;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAuthDto> login(@Valid @RequestBody UserCredentialsDto loginRequest) {
        var response = authUserUseCase.validateLogin(loginRequest.email(), loginRequest.password());
        return ResponseEntity.status(HttpStatus.OK).body(userEntityMapper.toTokenResponseDto(response));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UserCredentialsDto request) {
        log.info("UPDATE USER PASSWORD {}", request);
        authUserUseCase.updatePassword(request.email(), request.password());
        return ResponseEntity.noContent().build();
    }
}

