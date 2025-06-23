package com.br.fiap.fortaleza.sabor.infrastructure.doc;

import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
public interface UserControllerDoc {
    
    @Operation(summary = "Create a user", description = "Register a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register a user.", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid field: mandatory criteria were not met.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "User already registered.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userRequestDto);

    @Operation(summary = "Rescue the user by Id", description = "Allows the retrieval of information from a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User successfully located", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<UserResponseDto> getUserByID(@PathVariable @NotNull Long idUser);

    @Operation(summary = "Search all users", description = "Returns a list of all users registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User list returned successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    List<UserResponseDto> getAll();

    @Operation(summary = "Update a user", description = "Allows the user to update their registered data from identification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Data structure error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<UserResponseDto> update(@PathVariable @NotNull Long idUser, @RequestBody @Valid UpdateRequestDto updateRequestDto);

    @Operation(summary = "Delete user by ID", description = "Allows deletion of a specific user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<Void> delete(@PathVariable @NotNull Long idUser);
}
