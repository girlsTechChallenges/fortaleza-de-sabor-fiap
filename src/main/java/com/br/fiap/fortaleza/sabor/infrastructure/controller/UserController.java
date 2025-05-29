package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.CreateUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.DeleteUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.GetUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.UpdateUseCase;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.UserEntityMapper;
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
@RequestMapping("/usuarios")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final CreateUseCase createUseCase;
    private final GetUseCase getUseCase;
    private final UpdateUseCase updateUseCase;
    private final DeleteUseCase deleteUseCase;
    private final UserEntityMapper userEntityMapper;

    public UserController(CreateUseCase createUseCase, GetUseCase getAllUseCase, UpdateUseCase updateUseCase, DeleteUseCase deleteUseCase, UserEntityMapper userEntityMapper) {
        this.createUseCase = createUseCase;
        this.getUseCase = getAllUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.userEntityMapper = userEntityMapper;
    }

    @Operation(summary = "Create a user", description = "Register a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register a user.", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid field: mandatory criteria were not met.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "User already registered.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody UserRequestDto userRequestDto) {

        log.info("POST USER REQUEST: {} ", userRequestDto);
        var resp = createUseCase.save(userEntityMapper.toUserDomain(userRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntityMapper.toUserResponseDto(resp));
    }

    @Operation(summary = "Rescue the user by Id", description = "Allows the retrieval of information from a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User successfully located", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @GetMapping("/{idUser}")
    public ResponseEntity getUserByID(
            @PathVariable @NotNull Long idUser
    ) {
        log.info("GET USER BY ID REQUEST {} ", idUser);
        Optional<User> user = getUseCase.getById(idUser);
        return new ResponseEntity<>(ResponseEntity.status(HttpStatus.ACCEPTED).body(userEntityMapper.getUserByIdToUserResponseDto(user)), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Search all users", description = "Returns a list of all users registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User list returned successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponseDto> getAll() {
        log.info("START GET ALL USERS");
        var resp = getUseCase.getAll();
        return resp.stream().map(userEntityMapper::toUserResponseDto).toList();
    }

    @Operation(summary = "Update a user", description = "Allows the user to update their registered data from identification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Data structure error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })

    @PutMapping(value = "/{idUser}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable @NotNull Long idUser,
            @RequestBody @Valid UpdateRequestDto updateRequestDto
    ) {
        log.info("UPDATE USER REQUEST {} ", updateRequestDto);
        updateUseCase.update(idUser, userEntityMapper.updateToUserDomain(updateRequestDto));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete user by ID", description = "Allows deletion of a specific user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idUser) {
        log.info("DELETE USER BY ID REQUEST {}", idUser);
        deleteUseCase.delete(idUser);
        return ResponseEntity.noContent().build();
    }
}