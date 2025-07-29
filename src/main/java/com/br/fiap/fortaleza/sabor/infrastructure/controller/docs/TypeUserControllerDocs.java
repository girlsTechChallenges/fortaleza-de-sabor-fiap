package com.br.fiap.fortaleza.sabor.infrastructure.controller.docs;

import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "User Type Management", description = "Endpoints for managing user types.")
public interface TypeUserControllerDocs {

    @Operation(summary = "Create User Type", description = "Registers a new user type in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User type successfully created.",
                    content = @Content(schema = @Schema(implementation = TypeUserResponse.class))),
            @ApiResponse(responseCode = "409", description = "User type already exists.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<TypeUserResponse> create(@Valid @RequestBody TypeUserRequestDto request);

    @Operation(summary = "Update User Type", description = "Modifies an existing user type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User type successfully updated.",
                    content = @Content(schema = @Schema(implementation = TypeUserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User type not found.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "User type already exists.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<TypeUserResponse> update(@PathVariable @NotNull Long id,
                                            @Valid @RequestBody TypeUserRequestDto request);

    @Operation(summary = "List User Types", description = "Retrieves all registered user types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User types successfully retrieved.",
                    content = @Content(schema = @Schema(implementation = TypeUserResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<List<TypeUserResponse>> getAll();

    @Operation(summary = "Get User Type by ID", description = "Fetches a user type by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User type successfully retrieved.",
                    content = @Content(schema = @Schema(implementation = TypeUserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User type not found.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<TypeUserResponse> getById(@PathVariable @NotNull Long id);

    @Operation(summary = "Delete User Type", description = "Removes a user type from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User type successfully deleted."),
            @ApiResponse(responseCode = "404", description = "User type not found.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<Void> delete(@PathVariable @NotNull Long id);
}