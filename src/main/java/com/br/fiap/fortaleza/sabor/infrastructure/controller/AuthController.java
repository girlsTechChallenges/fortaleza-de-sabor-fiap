package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.AuthUseCase;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserCredentialsDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.doc.AuthControllerDoc;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserMapper;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController implements AuthControllerDoc {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthUseCase authUseCase;    private final UserMapper userMapper;

    public AuthController(AuthUseCase authUseCase, UserMapper userMapper) {
        this.authUseCase = authUseCase;
        this.userMapper = userMapper;
    }   
    
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAuthDto> login(@Valid @RequestBody UserCredentialsDto loginRequest) {
        var response = authUseCase.validateLogin(loginRequest.email(), loginRequest.password());
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toTokenResponseDto(response));
    }

    @PatchMapping("/password")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody UserCredentialsDto request) {
        log.info("UPDATE USER PASSWORD {}", request);
        authUseCase.updatePassword(request.email(), request.password());
        return ResponseEntity.noContent().build();
    }
}

