package com.br.fiap.fortaleza.sabor.infrastructure.controller.docs;

import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserTypeRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserTypeResponseDto;
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

@Tag(name = "UserType Management", description = "API endpoints for type users management operations")
public interface UserTypeControllerDocs {

    @Operation(summary = "Create a userType", description = "Register a userType.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register a userType.",
                    content = @Content(schema = @Schema(implementation = UserTypeRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid field: mandatory criteria were not met.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "UserType already registered.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity create(@Valid @RequestBody UserTypeRequestDto userTypeDto);

    @Operation(summary = "Rescue the userType by Id", description = "Allows the retrieval of information from a specific userType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "UserType successfully located", content = @Content(schema = @Schema(implementation = UserTypeRequestDto.class))),
            @ApiResponse(responseCode = "404", description = "UserType not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity getUserTypeByID(@PathVariable @NotNull Long idUserType);

    @Operation(summary = "Search all userTypes", description = "Returns a list of all userTypes registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserType list returned successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    List<UserTypeResponseDto> getAll();

    @Operation(summary = "Update a userType", description = "Allows the userType to update their registered data from identification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "UserType updated successfully", content = @Content(schema = @Schema(implementation = UserTypeRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Data structure error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "UserType not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity update(@PathVariable @NotNull Long idUserType,
            @RequestBody @Valid UserTypeRequestDto userTypeDto);

    @Operation(summary = "Delete userType by ID", description = "Allows deletion of a specific userType's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "UserType deleted successfully"),
            @ApiResponse(responseCode = "404", description = "UserType not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<Void> delete(@PathVariable @NotNull Long idUserType);

}
