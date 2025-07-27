package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.restaurant.CreateRestaurantUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.restaurant.DeleteRestaurantUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.restaurant.GetRestaurantUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.restaurant.UpdateRestaurantUseCase;
import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.RestaurantControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.OwnerRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantFullDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.RestaurantMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController implements RestaurantControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase, GetRestaurantUseCase getRestaurantUseCase, UpdateRestaurantUseCase updateRestaurantUseCase, DeleteRestaurantUseCase deleteRestaurantUseCase, RestaurantMapper restaurantMapper) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getRestaurantUseCase = getRestaurantUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@Valid @RequestBody RestaurantRequestDto request) {
        log.info("Received request to create restaurant: {}", request);

        var restaurant = restaurantMapper.toRestaurantDomain(request);
        var createdRestaurant = createRestaurantUseCase.save(restaurant);
        var responseDto = restaurantMapper.toRestaurantResponseDto(createdRestaurant);

        URI location = URI.create("/restaurants/" + createdRestaurant.getId());

        log.info("Restaurant created successfully with ID: {}", createdRestaurant.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @PutMapping(value = "/{idRestaurant}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@PathVariable @NotNull Long idRestaurant,
                                                                  @Valid @RequestBody RestaurantRequestDto restaurant) {

        log.info("Received request to update restaurant with ID: {}", idRestaurant);

        var restaurantDomain = restaurantMapper.toRestaurantDomain(restaurant);
        var updatedRestaurant = updateRestaurantUseCase.update(idRestaurant, restaurantDomain);
        var responseDto = restaurantMapper.toRestaurantResponseDto(updatedRestaurant);

        log.info("Restaurant with ID: {} updated successfully", idRestaurant);
        return ResponseEntity.accepted().body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantFullDto>> getAllRestaurants() {
        log.info("Received request to get all restaurants");

       return ResponseEntity.ok(getRestaurantUseCase.getAll()
                .stream()
                .map(restaurantMapper::toRestaurantFullDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantFullDto> getById(Long id) {
        log.info("Received request to get restaurant by ID: {}", id);

        Optional<Restaurant> restaurantOptional = getRestaurantUseCase.getById(id);
        log.info("Restaurant with ID: {} found", id);

        return ResponseEntity.ok(restaurantMapper.toRestaurantFullByIdDto(restaurantOptional.orElse(null)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Long id) {
        log.info("Received request to delete restaurant with ID: {}", id);

        deleteRestaurantUseCase.delete(id);
        log.info("Restaurant with ID: {} deleted successfully", id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "owner/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantResponseDto> updateOwnerRestaurant(Long id, OwnerRequestDto ownerRequestDto) {
        log.info("Received request to update owner - restaurant ID: {}", id);

        var updatedRestaurant =
                updateRestaurantUseCase.updateOwner(id, ownerRequestDto.owner(), ownerRequestDto.email());

        return ResponseEntity.ok(restaurantMapper.toRestaurantResponseDto(updatedRestaurant));
    }

}
