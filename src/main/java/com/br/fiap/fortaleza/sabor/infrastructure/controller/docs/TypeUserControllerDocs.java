package com.br.fiap.fortaleza.sabor.infrastructure.controller.docs;

import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TypeUser Management", description = "API endpoints for type users management operations")
public interface TypeUserControllerDocs {

    @Operation(summary = "Create a typeUser", description = "Register a typeUser.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register a typeUser.",
                    content = @Content(schema = @Schema(implementation = TypeUserRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid field: mandatory criteria were not met.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "TypeUser already registered.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity create(@Valid @RequestBody TypeUserRequestDto typeUserDto);

    @Operation(summary = "Rescue the typeUser by Id", description = "Allows the retrieval of information from a specific typeUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "TypeUser successfully located", content = @Content(schema = @Schema(implementation = TypeUserRequestDto.class))),
            @ApiResponse(responseCode = "404", description = "TypeUser not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity getTypeUserByID(@PathVariable @NotNull Long idTypeUser);

    @Operation(summary = "Search all typeUsers", description = "Returns a list of all typeUsers registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TypeUser list returned successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    List<TypeUserResponseDto> getAll();

    @Operation(summary = "Update a typeUser", description = "Allows the typeUser to update their registered data from identification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "TypeUser updated successfully", content = @Content(schema = @Schema(implementation = TypeUserRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Data structure error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "TypeUser not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity update(@PathVariable @NotNull Long idTypeUser,
            @RequestBody @Valid TypeUserRequestDto typeUserDto);

    @Operation(summary = "Delete typeUser by ID", description = "Allows deletion of a specific typeUser's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TypeUser deleted successfully"),
            @ApiResponse(responseCode = "404", description = "TypeUser not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<Void> delete(@PathVariable @NotNull Long idTypeUser);

}
