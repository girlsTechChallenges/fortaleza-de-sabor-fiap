package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.restaurant.CreateRestaurantUseCase;
import com.br.fiap.fortaleza.sabor.application.usecase.restaurant.UpdateRestaurantUseCase;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.RestaurantControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantRequestDto;
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

@RestController
@RequestMapping("/restaurants")
public class RestaurantController implements RestaurantControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase, UpdateRestaurantUseCase updateRestaurantUseCase, RestaurantMapper restaurantMapper) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
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

}
