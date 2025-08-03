package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.ports.in.AuthUseCasePort;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.AuthControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserCredentialsDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;
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

    private final AuthUseCasePort authUseCasePort;
    private final UserMapper userMapper;

    public AuthController(AuthUseCasePort authUseCasePort, UserMapper userMapper) {
        this.authUseCasePort = authUseCasePort;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAuthDto> login(@Valid @RequestBody UserCredentialsDto loginRequest) {
        var response = authUseCasePort.validateLogin(loginRequest.email(), loginRequest.password());
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toTokenResponseDto(response));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UserCredentialsDto request) {
        log.info("UPDATE USER PASSWORD {}", request);
        authUseCasePort.updatePassword(request.email(), request.password());
        return ResponseEntity.noContent().build();
    }
}

