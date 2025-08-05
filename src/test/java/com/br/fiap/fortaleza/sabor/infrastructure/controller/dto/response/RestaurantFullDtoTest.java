package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.BusinessHoursDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RestaurantFullDto Tests")
class RestaurantFullDtoTest {

    @Test
    @DisplayName("Should create RestaurantFullDto with all fields")
    void shouldCreateRestaurantFullDtoWithAllFields() {
        // Arrange
        List<AddressDto> addresses = List.of(
            new AddressDto("Street das Flores", "Centro", "Apt 123", 123, "SP", "São Paulo", "01234567")
        );
        
        List<BusinessHoursDto> businessHours = List.of(
            new BusinessHoursDto(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), null)
        );

        // Act
        RestaurantFullDto restaurantDto = new RestaurantFullDto(
            1L,
            "Restaurant Completo",
            "Italiana",
            "restaurant@email.com",
            "João Silva",
            addresses,
            businessHours
        );

        // Assert
        assertThat(restaurantDto.id()).isEqualTo(1L);
        assertThat(restaurantDto.name()).isEqualTo("Restaurant Completo");
        assertThat(restaurantDto.kitchenType()).isEqualTo("Italiana");
        assertThat(restaurantDto.email()).isEqualTo("restaurant@email.com");
        assertThat(restaurantDto.owner()).isEqualTo("João Silva");
        assertThat(restaurantDto.address()).isEqualTo(addresses);
        assertThat(restaurantDto.businessHours()).isEqualTo(businessHours);
    }

    @Test
    @DisplayName("Should create RestaurantFullDto with null fields")
    void shouldCreateRestaurantFullDtoWithNullFields() {
        // Arrange & Act
        RestaurantFullDto restaurantDto = new RestaurantFullDto(
            null, null, null, null, null, null, null
        );

        // Assert
        assertThat(restaurantDto.id()).isNull();
        assertThat(restaurantDto.name()).isNull();
        assertThat(restaurantDto.kitchenType()).isNull();
        assertThat(restaurantDto.email()).isNull();
        assertThat(restaurantDto.owner()).isNull();
        assertThat(restaurantDto.address()).isNull();
        assertThat(restaurantDto.businessHours()).isNull();
    }

    @Test
    @DisplayName("Should create RestaurantFullDto with empty lists")
    void shouldCreateRestaurantFullDtoWithEmptyLists() {
        // Arrange & Act
        RestaurantFullDto restaurantDto = new RestaurantFullDto(
            2L,
            "Restaurant Vazio",
            "Japonesa",
            "vazio@email.com",
            "Maria Santos",
            List.of(),
            List.of()
        );

        // Assert
        assertThat(restaurantDto.address()).isEmpty();
        assertThat(restaurantDto.businessHours()).isEmpty();
        assertThat(restaurantDto.name()).isEqualTo("Restaurant Vazio");
    }

    @Test
    @DisplayName("Should validate record equality")
    void shouldValidateRecordEquality() {
        // Arrange
        List<AddressDto> addresses = List.of(
            new AddressDto("Street A", "Centro", "Apt 1", 100, "SP", "São Paulo", "01234567")
        );
        
        List<BusinessHoursDto> businessHours = List.of(
            new BusinessHoursDto(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(20, 0), null)
        );

        RestaurantFullDto restaurant1 = new RestaurantFullDto(
            1L, "Restaurant A", "Brasileira", "a@email.com", "Proprietário A", addresses, businessHours
        );
        
        RestaurantFullDto restaurant2 = new RestaurantFullDto(
            1L, "Restaurant A", "Brasileira", "a@email.com", "Proprietário A", addresses, businessHours
        );
        
        RestaurantFullDto restaurant3 = new RestaurantFullDto(
            2L, "Restaurant B", "Chinesa", "b@email.com", "Proprietário B", addresses, businessHours
        );

        // Act & Assert
        assertThat(restaurant1).isEqualTo(restaurant2);
        assertThat(restaurant1).isNotEqualTo(restaurant3);
        assertThat(restaurant1.hashCode()).isEqualTo(restaurant2.hashCode());
    }

    @Test
    @DisplayName("Should generate proper toString")
    void shouldGenerateProperToString() {
        // Arrange
        List<AddressDto> addresses = List.of(
            new AddressDto("Street Principal", "Centro", null, 500, "RJ", "Rio de Janeiro", "20000000")
        );
        
        List<BusinessHoursDto> businessHours = List.of(
            new BusinessHoursDto(DayOfWeek.WEDNESDAY, LocalTime.of(11, 0), LocalTime.of(22, 0), "Horário especial")
        );

        RestaurantFullDto restaurantDto = new RestaurantFullDto(
            3L, "Pizzaria Central", "Italiana", "pizza@email.com", "Luigi", addresses, businessHours
        );

        // Act
        String toString = restaurantDto.toString();

        // Assert
        assertThat(toString).contains("3");
        assertThat(toString).contains("Pizzaria Central");
        assertThat(toString).contains("Italiana");
        assertThat(toString).contains("pizza@email.com");
        assertThat(toString).contains("Luigi");
    }

    @Test
    @DisplayName("Should handle multiple addresses and business hours")
    void shouldHandleMultipleAddressesAndBusinessHours() {
        // Arrange
        List<AddressDto> multipleAddresses = List.of(
            new AddressDto("Street A", "Centro", "Loja 1", 100, "SP", "São Paulo", "01000000"),
            new AddressDto("Street B", "Vila", "Loja 2", 200, "SP", "São Paulo", "02000000"),
            new AddressDto("Street C", "Jardim", "Loja 3", 300, "RJ", "Rio de Janeiro", "20000000")
        );
        
        List<BusinessHoursDto> multipleHours = List.of(
            new BusinessHoursDto(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(18, 0), "Segunda-feira"),
            new BusinessHoursDto(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(18, 0), "Terça-feira"),
            new BusinessHoursDto(DayOfWeek.SATURDAY, LocalTime.of(10, 0), LocalTime.of(16, 0), "Sábado - horário reduzido"),
            new BusinessHoursDto(DayOfWeek.SUNDAY, LocalTime.of(12, 0), LocalTime.of(20, 0), "Domingo")
        );

        // Act
        RestaurantFullDto restaurantDto = new RestaurantFullDto(
            10L,
            "Rede de Restaurantes",
            "Variada",
            "rede@email.com",
            "Grupo Empresarial",
            multipleAddresses,
            multipleHours
        );

        // Assert
        assertThat(restaurantDto.address()).hasSize(3);
        assertThat(restaurantDto.businessHours()).hasSize(4);
        assertThat(restaurantDto.address()).containsExactlyElementsOf(multipleAddresses);
        assertThat(restaurantDto.businessHours()).containsExactlyElementsOf(multipleHours);
    }

    @Test
    @DisplayName("Should handle empty strings")
    void shouldHandleEmptyStrings() {
        // Arrange & Act
        RestaurantFullDto restaurantDto = new RestaurantFullDto(
            0L, "", "", "", "", List.of(), List.of()
        );

        // Assert
        assertThat(restaurantDto.id()).isEqualTo(0L);
        assertThat(restaurantDto.name()).isEmpty();
        assertThat(restaurantDto.kitchenType()).isEmpty();
        assertThat(restaurantDto.email()).isEmpty();
        assertThat(restaurantDto.owner()).isEmpty();
        assertThat(restaurantDto.address()).isEmpty();
        assertThat(restaurantDto.businessHours()).isEmpty();
    }
}
