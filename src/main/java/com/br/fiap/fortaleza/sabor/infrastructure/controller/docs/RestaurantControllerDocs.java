package com.br.fiap.fortaleza.sabor.infrastructure.controller.docs;

import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.ApiErrorMessage;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantFullDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantResponseDto;
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


@Tag(name = "Restaurant Management", description = "API endpoints for restaurant management operations")
public interface RestaurantControllerDocs {

    @Operation(summary = "Create a restaurant", description = "Register a new restaurant in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant created successfully.", 
                    content = @Content(schema = @Schema(implementation = RestaurantResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Restaurant already registered.", 
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", 
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<RestaurantResponseDto> createRestaurant(@Valid @RequestBody RestaurantRequestDto request);

    @Operation(summary = "Update a restaurant", description = "Update a restaurant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register a restaurant.",
                    content = @Content(schema = @Schema(implementation = RestaurantResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Restaurant already registered.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<RestaurantResponseDto> updateRestaurant(@PathVariable @NotNull Long idRestaurant,
                                                           @Valid @RequestBody RestaurantRequestDto restaurant);

    @Operation(summary = "List all restaurants", description = "Get a list of all registered restaurants\n.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = RestaurantFullDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<List<RestaurantFullDto>> getAllRestaurants();


    @Operation(summary = "Get restaurant by ID", description = "Retrieve a specific restaurant by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant found successfully.",
                    content = @Content(schema = @Schema(implementation = RestaurantFullDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<RestaurantFullDto> getById(@PathVariable @NotNull Long id);

    @Operation(summary = "Delete restaurant", description = "Delete a restaurant from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "restaurant deleted successfully."),
            @ApiResponse(responseCode = "404", description = "restaurant not found.",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
    })
    ResponseEntity<Void> delete(@PathVariable @NotNull Long id);

}
