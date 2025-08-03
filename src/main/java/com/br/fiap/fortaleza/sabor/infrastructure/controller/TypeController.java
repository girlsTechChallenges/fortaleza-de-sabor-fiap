package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.ports.in.TypeUseCasePort;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.TypeUserControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponse;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.TypeUserMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/type-users")
public class TypeController implements TypeUserControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(TypeController.class);

    private final TypeUseCasePort typeUseCasePort;
    private final TypeUserMapper typeUserMapper;

    public TypeController(TypeUseCasePort typeUseCasePort, TypeUserMapper typeUserMapper) {
        this.typeUseCasePort = typeUseCasePort;
        this.typeUserMapper = typeUserMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypeUserResponse> create(@Valid @RequestBody TypeUserRequestDto request) {
        log.info("Received request to create type user: {}", request);

        var rep = typeUserMapper.toTypeUserDomain(request);
        var response = typeUseCasePort.create(rep);

        URI location = URI.create("/type-users/" + response.getId());

        log.info("Created type user: {}", response);
        return ResponseEntity.created(location).body(typeUserMapper.typeUserResponse(response));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypeUserResponse> update(@PathVariable @NotNull Long id, @Valid @RequestBody TypeUserRequestDto request) {
        log.info("Received request to update type user: {}", request);

        var rep = typeUserMapper.toTypeUserDomain(request);
        var response = typeUseCasePort.update(id, rep);
        var responseDto = typeUserMapper.typeUserResponse(response);

        log.info("Type user with ID: {} updated successfully", id);
        return ResponseEntity.accepted().body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TypeUserResponse>> getAll() {
        log.info("Received request to get all type users");

        var typeUsers = typeUseCasePort.getAll();
        var response = typeUsers.stream()
                .map(typeUserMapper::typeUserResponse)
                .toList();

        log.info("Returning {} type users", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeUserResponse> getById(@PathVariable @NotNull Long id) {
        log.info("Received request to get type user by ID: {}", id);

        var typeUser = typeUseCasePort.getById(id);
        if (typeUser.isPresent()) {
            log.info("Type user found: {}", typeUser.get());
            return ResponseEntity.ok(typeUserMapper.typeUserResponse(typeUser.get()));
        }
        log.warn("Type user with ID: {} not found", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        log.info("Received request to delete type user with ID: {}", id);

        typeUseCasePort.deleteById(id);
        log.info("Type user with ID: {} deleted successfully", id);

        return ResponseEntity.noContent().build();
    }
}
