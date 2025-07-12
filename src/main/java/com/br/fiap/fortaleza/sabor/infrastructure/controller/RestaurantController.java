package com.br.fiap.fortaleza.sabor.infrastructure.controller;

import com.br.fiap.fortaleza.sabor.application.usecase.restaurant.CreateRestaurantUseCase;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.docs.RestaurantControllerDocs;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.RestaurantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController implements RestaurantControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase, RestaurantMapper restaurantMapper) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@RequestBody RestaurantRequestDto request) {
        log.info("Creating restaurant: {}", request);

        var restaurant = restaurantMapper.toRestaurantDomain(request);
        var createdRestaurant = createRestaurantUseCase.save(restaurant);
        var responseDto = restaurantMapper.toRestaurantResponseDto(createdRestaurant);

        return ResponseEntity.ok(responseDto);
    }
}
