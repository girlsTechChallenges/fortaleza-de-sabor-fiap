package com.br.fiap.fortaleza.sabor.integration.util;

import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Test data factory for creating test objects used in integration tests.
 */
public class TestDataFactory {

    // User Test Data
    public static UserRequestDto createValidUserRequest() {
        return new UserRequestDto(
                "João Silva",
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                List.of(createValidAddressDto())
        );
    }

    public static UserRequestDto createValidOwnerUserRequest() {
        return new UserRequestDto(
                "Maria Proprietária",
                "maria.owner@test.com",
                "mariaowner",
                "password123",
                LocalDate.now(),
                "PROPRIETARIO",
                List.of(createValidAddressDto())
        );
    }

    public static UpdateRequestDto createValidUpdateRequest() {
        return new UpdateRequestDto(
                "João Silva Updated",
                "joao.updated@test.com",
                "password456",
                "CLIENTE",
                List.of(createValidAddressDto())
        );
    }

    public static UserCredentialsDto createValidCredentials() {
        return new UserCredentialsDto("joao.silva@test.com", "password123");
    }

    // Address Test Data
    public static AddressDto createValidAddressDto() {
        return new AddressDto(
                "Rua dos Testes",
                "Centro",
                "Apto 45",
                123,
                "SP",
                "São Paulo",
                "01234567"
        );
    }

    // Restaurant Test Data
    public static RestaurantRequestDto createValidRestaurantRequest() {
        return new RestaurantRequestDto(
                "Restaurante Teste",
                "ITALIANA",
                "maria.owner@test.com",
                List.of(createValidAddressDto()),
                List.of(createValidBusinessHours())
        );
    }

    public static BusinessHoursDto createValidBusinessHours() {
        return new BusinessHoursDto(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(22, 0),
                "Funcionamento normal"
        );
    }

    public static OwnerRequestDto createValidOwnerRequest() {
        return new OwnerRequestDto(
                "Novo Proprietário",
                "novo.owner@test.com"
        );
    }

    // Menu Item Test Data
    public static MenuItemRequestDto createValidMenuItemRequest() {
        return new MenuItemRequestDto(
                "Pizza Margherita",
                "Pizza clássica com molho de tomate, mozzarella e manjericão",
                "29.90",
                true,
                "pizza-margherita.jpg"
        );
    }

    public static UpdateMenuItemRequestDto createValidUpdateMenuItemRequest() {
        return new UpdateMenuItemRequestDto(
                "Pizza Margherita Premium",
                "Pizza premium com molho de tomate, mozzarella de búfala e manjericão fresco",
                "39.90",
                true,
                "pizza-margherita-premium.jpg"
        );
    }

    // Type User Test Data
    public static TypeUserRequestDto createValidTypeUserRequest() {
        return new TypeUserRequestDto("ADMINISTRADOR");
    }

    // Invalid data for negative tests
    public static UserRequestDto createInvalidUserRequest() {
        return new UserRequestDto(
                "", // Invalid empty name
                "invalid-email", // Invalid email format
                "ab", // Invalid login too short
                "123", // Invalid password too short
                LocalDate.now(),
                "CLIENTE",
                List.of() // Invalid empty address list
        );
    }

    public static RestaurantRequestDto createInvalidRestaurantRequest() {
        return new RestaurantRequestDto(
                "", // Invalid empty name
                "INVALID_TYPE", // Invalid cuisine type
                "invalid-email", // Invalid email format
                List.of(), // Invalid empty address
                List.of()  // Invalid empty business hours
        );
    }

    public static MenuItemRequestDto createInvalidMenuItemRequest() {
        return new MenuItemRequestDto(
                "", // Invalid empty name
                "", // Invalid empty description  
                "invalid-price", // Invalid price format
                null, // Invalid null availability
                "" // Invalid empty image
        );
    }
}
