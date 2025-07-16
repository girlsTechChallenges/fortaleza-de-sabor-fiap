package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.menu.CreateMenuItemUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.menu.DeleteMenuItemUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.menu.GetMenuItemUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.menu.UpdateMenuItemUseCase;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateMenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.MenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.MenuItemResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cardapio")
public class MenuItemsController {
    private static final Logger log = LoggerFactory.getLogger(MenuItemsController.class);
    private final CreateMenuItemUseCase createMenuUseCase;
    private final GetMenuItemUseCase getMenuItemUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;
    private final MenuEntityMapper menuEntityMapper;

    public MenuItemsController(CreateMenuItemUseCase createMenuUseCase, GetMenuItemUseCase getMenuItemUseCase, UpdateMenuItemUseCase updateMenuItemUseCase, DeleteMenuItemUseCase deleteMenuItemUseCase, MenuEntityMapper menuEntityMapper) {
        this.createMenuUseCase = createMenuUseCase;
        this.getMenuItemUseCase = getMenuItemUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
        this.menuEntityMapper = menuEntityMapper;
    }

    @Operation(summary = "Create a menu", description = "Create a new menu.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register a menu.", content = @Content(schema = @Schema(implementation = MenuItemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid field: mandatory criteria were not met.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Menu already registered.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody MenuItemRequestDto menuItemRequestDto) {
        log.info("POST MENU REQUEST: {} ", menuItemRequestDto);
        var resp = createMenuUseCase.save(menuEntityMapper.toMenuDomain(menuItemRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(menuEntityMapper.toMenuItemResponseDto(resp));
    }

    @Operation(summary = "Rescue the menu by Id", description = "Allows the retrieval of information from a specific menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Menu successfully located", content = @Content(schema = @Schema(implementation = MenuItemResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @GetMapping("/{idMenu}")
    public ResponseEntity getMenuByID(
            @PathVariable @NotNull Long idMenu
    ) {
        log.info("GET USER BY ID REQUEST {} ", idMenu);
        Optional<MenuItem> menu = getMenuItemUseCase.getById(idMenu);
        return new ResponseEntity<>(ResponseEntity.status(HttpStatus.ACCEPTED).body(menuEntityMapper.getMenuByIdToMenuResponseDto(menu)), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Search all menus", description = "Returns a list of all menus registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu list returned successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItemResponseDto> getAll() {
        log.info("START GET ALL USERS");
        var resp = getMenuItemUseCase.getAll();
        return resp.stream().map(menuEntityMapper::toMenuItemResponseDto).toList();
    }

    @Operation(summary = "Update a menu", description = "Allows the menu to update their registered data from identification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Menu updated successfully", content = @Content(schema = @Schema(implementation = MenuItemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Data structure error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })

    @PutMapping(value = "/{idMenu}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable @NotNull Long idMenu,
            @RequestBody @Valid UpdateMenuItemRequestDto updateMenuItemRequestDto
    ) {
        log.info("UPDATE USER REQUEST {} ", updateMenuItemRequestDto);
        updateMenuItemUseCase.update(idMenu, menuEntityMapper.updateToMenuDomain(updateMenuItemRequestDto));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete menu by ID", description = "Allows deletion of a specific menu's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @DeleteMapping("/{idMenu}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idMenu) {
        log.info("DELETE USER BY ID REQUEST {}", idMenu);
        deleteMenuItemUseCase.delete(idMenu);
        return ResponseEntity.noContent().build();
    }

}
