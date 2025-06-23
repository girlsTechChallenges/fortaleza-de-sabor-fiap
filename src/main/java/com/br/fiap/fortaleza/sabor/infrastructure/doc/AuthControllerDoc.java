package com.br.fiap.fortaleza.sabor.infrastructure.doc;

import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserCredentialsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface AuthControllerDoc {

    @Operation(summary = "Login", description = "Allows a user to authenticate by logging in with email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Invalid email or password", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<UserAuthDto> login(UserCredentialsDto loginRequest);

    @Operation(summary = "Change password", description = "Allows a user to change an already registered password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password changed successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<ApiResponse> updatePassword(UserCredentialsDto request);
}
