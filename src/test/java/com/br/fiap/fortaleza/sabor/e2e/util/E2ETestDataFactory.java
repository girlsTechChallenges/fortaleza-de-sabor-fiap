package com.br.fiap.fortaleza.sabor.e2e.util;

import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Factory class for creating test data for E2E tests.
 * Provides realistic test data that can be used in end-to-end scenarios.
 */
public class E2ETestDataFactory {
    
    /**
     * Creates a valid user request with unique data
     */
    public static UserRequestDto createValidUserRequest(String uniqueId) {
        return new UserRequestDto(
                "João Silva " + uniqueId,
                createUniqueEmail(uniqueId),
                "joao.silva." + uniqueId,
                "senhaSegura123",
                LocalDate.now(),
                "CUSTOMER",
                List.of(createValidAddress())
        );
    }
    
    /**
     * Creates a valid admin user request
     */
    public static UserRequestDto createValidAdminUserRequest(String uniqueId) {
        return new UserRequestDto(
                "Admin " + uniqueId,
                createUniqueEmail(uniqueId),
                "admin." + uniqueId,
                "adminPass123",
                LocalDate.now(),
                "ADMIN",
                List.of(createValidAddress())
        );
    }
    
    /**
     * Creates a valid restaurant request with unique data
     */
    public static RestaurantRequestDto createValidRestaurantRequest(String uniqueId) {
        return new RestaurantRequestDto(
                "Fortaleza de Sabor " + uniqueId,
                "Brasileira",
                createUniqueEmail(uniqueId),
                List.of(createValidAddress()),
                List.of(createValidBusinessHours())
        );
    }
    
    /**
     * Creates a valid menu item request with unique data
     */
    public static MenuItemRequestDto createValidMenuItemRequest(String uniqueId) {
        return new MenuItemRequestDto(
                "Pizza Margherita " + uniqueId,
                "Deliciosa pizza tradicional italiana com molho de tomate, mussarela e manjericão fresco",
                "29.90",
                true,
                "pizza-margherita-" + uniqueId + ".jpg"
        );
    }
    
    /**
     * Creates a valid type user request
     */
    public static TypeUserRequestDto createValidTypeUserRequest(String uniqueId) {
        return new TypeUserRequestDto("TYPE_" + uniqueId.toUpperCase());
    }
    
    /**
     * Creates valid user credentials for authentication
     */
    public static UserCredentialsDto createValidCredentials(String email, String password) {
        return new UserCredentialsDto(email, password);
    }
    
    /**
     * Creates authentication request for testing
     */
    public static UserCredentialsDto createValidAuthRequest(String uniqueId) {
        return new UserCredentialsDto(
                createUniqueEmail(uniqueId),
                "senha123456"
        );
    }
    
    /**
     * Creates an update request for menu items
     */
    public static UpdateMenuItemRequestDto createUpdateMenuItemRequest(String uniqueId) {
        return new UpdateMenuItemRequestDto(
                "Pizza Atualizada " + uniqueId,
                "Descrição atualizada da pizza",
                "35.90",
                true,
                "pizza-updated-" + uniqueId + ".jpg"
        );
    }
    
    /**
     * Creates an update request for users
     */
    public static UpdateRequestDto createUpdateUserRequest(String uniqueId) {
        return new UpdateRequestDto(
                "Nome Atualizado " + uniqueId,
                createUniqueEmail(uniqueId),
                "novaSenha123",
                "CUSTOMER",
                List.of(createValidAddress())
        );
    }
    
    /**
     * Creates a valid address for testing
     */
    private static AddressDto createValidAddress() {
        return new AddressDto(
                "Rua das Flores",
                "Centro",
                "Apartamento 123",
                456,
                "SP", 
                "São Paulo",
                "01234567"
        );
    }
    
    /**
     * Creates a valid address DTO for public use
     */
    public static AddressDto createValidAddressDto() {
        return createValidAddress();
    }
    
    /**
     * Creates valid business hours for restaurants
     */
    private static BusinessHoursDto createValidBusinessHours() {
        return new BusinessHoursDto(
                DayOfWeek.MONDAY, 
                LocalTime.of(8, 0), 
                LocalTime.of(22, 0),
                null
        );
    }
    
    /**
     * Creates a unique email address for testing
     */
    private static String createUniqueEmail(String uniqueId) {
        return "e2e.test." + uniqueId + "@fortaleza-sabor.com";
    }
    
    /**
     * Creates invalid data for negative testing scenarios
     */
    public static class InvalidData {
        
        public static UserRequestDto createInvalidUserRequest() {
            return new UserRequestDto(
                    "", // Nome vazio
                    "email-invalido", // Email inválido
                    "a", // Login muito curto
                    "123", // Senha muito curta
                    LocalDate.now(),
                    "", // Tipo vazio
                    List.of() // Lista de endereços vazia
            );
        }
        
        public static MenuItemRequestDto createInvalidMenuItemRequest() {
            return new MenuItemRequestDto(
                    "", // Nome vazio
                    "", // Descrição vazia
                    "preco-invalido", // Preço inválido
                    null, // Disponibilidade nula
                    "" // Imagem vazia
            );
        }
        
        public static TypeUserRequestDto createInvalidTypeUserRequest() {
            return new TypeUserRequestDto(""); // Empty type name
        }
        
        public static RestaurantRequestDto createInvalidRestaurantRequest() {
            return new RestaurantRequestDto(
                    "", // Nome vazio
                    "", // Tipo de cozinha vazio
                    "email-invalido", // Email inválido
                    List.of(), // Lista de endereços vazia
                    List.of() // Lista de horários vazia
            );
        }
    }
}
