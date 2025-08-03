package com.br.fiap.fortaleza.sabor.infrastructure.controller.docs;

import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateMenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.MenuItemResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.ApiErrorMessage;
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

@Tag(name = "Menu", description = "API endpoints for Menu restaurant")
public interface MenuItemControllerDocs {

    @Operation(summary = "Create a menu", description = "Create a new menu.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register a menu.", content = @Content(schema = @Schema(implementation = MenuItemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid field: mandatory criteria were not met.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Menu already registered.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<MenuItemResponseDto> create(@Valid @RequestBody MenuItemRequestDto menuItemRequestDto);

    @Operation(summary = "Rescue the menu by Id", description = "Allows the retrieval of information from a specific menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Menu successfully located", content = @Content(schema = @Schema(implementation = MenuItemResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<MenuItemResponseDto> getMenuByID(@PathVariable @NotNull Long idMenu);

    @Operation(summary = "Search all menus", description = "Returns a list of all menus registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu list returned successfully."),
            @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    List<MenuItemResponseDto> getAll();

    @Operation(summary = "Update a menu", description = "Allows the menu to update their registered data from identification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Menu updated successfully", content = @Content(schema = @Schema(implementation = MenuItemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Data structure error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<MenuItemResponseDto> update(@PathVariable @NotNull Long idMenu, @RequestBody @Valid UpdateMenuItemRequestDto updateMenuItemRequestDto
    );

    @Operation(summary = "Delete menu by ID", description = "Allows deletion of a specific menu's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<Void> delete(@PathVariable @NotNull Long idMenu);
}
