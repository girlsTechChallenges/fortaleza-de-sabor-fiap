package com.br.fiap.fortaleza.sabor.infrastructure.controller;


import com.br.fiap.fortaleza.sabor.application.usecase.menu.CreateMenuItemUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.menu.DeleteMenuItemUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.menu.GetMenuItemUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.menu.UpdateMenuItemUseCase;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.MenuItemControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateMenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.MenuItemResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuEntityMapper;
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
public class MenuItemsController implements MenuItemControllerDocs {
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody MenuItemRequestDto menuItemRequestDto) {
        log.info("POST MENU REQUEST: {} ", menuItemRequestDto);
        var resp = createMenuUseCase.save(menuEntityMapper.toMenuDomain(menuItemRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(menuEntityMapper.toMenuItemResponseDto(resp));
    }

    @GetMapping("/{idMenu}")
    public ResponseEntity getMenuByID(
            @PathVariable @NotNull Long idMenu
    ) {
        log.info("GET USER BY ID REQUEST {} ", idMenu);
        Optional<MenuItem> menu = getMenuItemUseCase.getById(idMenu);
        return new ResponseEntity<>(ResponseEntity.status(HttpStatus.ACCEPTED).body(menuEntityMapper.getMenuByIdToMenuResponseDto(menu)), HttpStatus.ACCEPTED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItemResponseDto> getAll() {
        log.info("START GET ALL USERS");
        var resp = getMenuItemUseCase.getAll();
        return resp.stream().map(menuEntityMapper::toMenuItemResponseDto).toList();
    }

    @PutMapping(value = "/{idMenu}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable @NotNull Long idMenu,
            @RequestBody @Valid UpdateMenuItemRequestDto updateMenuItemRequestDto
    ) {
        log.info("UPDATE USER REQUEST {} ", updateMenuItemRequestDto);
        updateMenuItemUseCase.update(idMenu, menuEntityMapper.updateToMenuDomain(updateMenuItemRequestDto));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{idMenu}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idMenu) {
        log.info("DELETE USER BY ID REQUEST {}", idMenu);
        deleteMenuItemUseCase.delete(idMenu);
        return ResponseEntity.noContent().build();
    }

}
