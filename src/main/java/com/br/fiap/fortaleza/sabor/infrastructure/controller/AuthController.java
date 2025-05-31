package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.AuthUseCase;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserCredentialsDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthUseCase authUseCase;
    private final UserEntityMapper userEntityMapper;

    public AuthController(AuthUseCase authUseCase, UserEntityMapper userEntityMapper) {
        this.authUseCase = authUseCase;
        this.userEntityMapper = userEntityMapper;
    }

    @Operation(summary = "Login", description = "Allows a user to authenticate by logging in with email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Invalid email or password", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAuthDto> login(@Valid @RequestBody UserCredentialsDto loginRequest) {
        var response = authUseCase.validateLogin(loginRequest.email(), loginRequest.password());
        return ResponseEntity.status(HttpStatus.OK).body(userEntityMapper.toTokenResponseDto(response));
    }

    @Operation(summary = "Change password", description = "Allows a user to change an already registered password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password changed successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody UserCredentialsDto request) {
        log.info("UPDATE USER PASSWORD {}", request);
        authUseCase.updatePassword(request.email(), request.password());
        return ResponseEntity.noContent().build();
    }
}

