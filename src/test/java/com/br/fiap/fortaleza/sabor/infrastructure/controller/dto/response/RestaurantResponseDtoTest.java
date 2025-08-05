package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RestaurantResponseDto Tests")
class RestaurantResponseDtoTest {

    @Test
    @DisplayName("Should create RestaurantResponseDto with all fields")
    void shouldCreateRestaurantResponseDtoWithAllFields() {
        // Arrange & Act
        RestaurantResponseDto restaurantDto = new RestaurantResponseDto(
            1L,
            "Restaurant Exemplo",
            "João Silva"
        );

        // Assert
        assertThat(restaurantDto.id()).isEqualTo(1L);
        assertThat(restaurantDto.name()).isEqualTo("Restaurant Exemplo");
        assertThat(restaurantDto.owner()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("Should create RestaurantResponseDto with null fields")
    void shouldCreateRestaurantResponseDtoWithNullFields() {
        // Arrange & Act
        RestaurantResponseDto restaurantDto = new RestaurantResponseDto(null, null, null);

        // Assert
        assertThat(restaurantDto.id()).isNull();
        assertThat(restaurantDto.name()).isNull();
        assertThat(restaurantDto.owner()).isNull();
    }

    @Test
    @DisplayName("Should validate record equality")
    void shouldValidateRecordEquality() {
        // Arrange
        RestaurantResponseDto restaurant1 = new RestaurantResponseDto(1L, "Restaurant A", "João");
        RestaurantResponseDto restaurant2 = new RestaurantResponseDto(1L, "Restaurant A", "João");
        RestaurantResponseDto restaurant3 = new RestaurantResponseDto(2L, "Restaurant B", "Maria");

        // Act & Assert
        assertThat(restaurant1).isEqualTo(restaurant2);
        assertThat(restaurant1).isNotEqualTo(restaurant3);
        assertThat(restaurant1.hashCode()).isEqualTo(restaurant2.hashCode());
    }

    @Test
    @DisplayName("Should generate proper toString")
    void shouldGenerateProperToString() {
        // Arrange
        RestaurantResponseDto restaurantDto = new RestaurantResponseDto(
            1L, "Pizzaria Bella", "Maria Santos"
        );

        // Act
        String toString = restaurantDto.toString();

        // Assert
        assertThat(toString).contains("1");
        assertThat(toString).contains("Pizzaria Bella");
        assertThat(toString).contains("Maria Santos");
    }

    @Test
    @DisplayName("Should handle empty strings")
    void shouldHandleEmptyStrings() {
        // Arrange & Act
        RestaurantResponseDto restaurantDto = new RestaurantResponseDto(0L, "", "");

        // Assert
        assertThat(restaurantDto.id()).isEqualTo(0L);
        assertThat(restaurantDto.name()).isEmpty();
        assertThat(restaurantDto.owner()).isEmpty();
    }

    @Test
    @DisplayName("Should handle negative id")
    void shouldHandleNegativeId() {
        // Arrange & Act
        RestaurantResponseDto restaurantDto = new RestaurantResponseDto(
            -1L, "Restaurant Teste", "Proprietário Teste"
        );

        // Assert
        assertThat(restaurantDto.id()).isEqualTo(-1L);
        assertThat(restaurantDto.name()).isEqualTo("Restaurant Teste");
        assertThat(restaurantDto.owner()).isEqualTo("Proprietário Teste");
    }

    @Test
    @DisplayName("Should handle very large id")
    void shouldHandleVeryLargeId() {
        // Arrange & Act
        RestaurantResponseDto restaurantDto = new RestaurantResponseDto(
            Long.MAX_VALUE, "Restaurant Grande", "Proprietário"
        );

        // Assert
        assertThat(restaurantDto.id()).isEqualTo(Long.MAX_VALUE);
        assertThat(restaurantDto.name()).isEqualTo("Restaurant Grande");
        assertThat(restaurantDto.owner()).isEqualTo("Proprietário");
    }
}
