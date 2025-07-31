package com.br.fiap.fortaleza.sabor.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Constantes para testes unitários
 * Centraliza valores comuns utilizados nos testes
 */
public final class TestConstants {
    
    // User Constants
    public static final String VALID_USER_NAME = "João Silva";
    public static final String VALID_USER_EMAIL = "joao.silva@email.com";
    public static final String VALID_USER_LOGIN = "joao.silva";
    public static final String VALID_USER_PASSWORD = "senha123";
    public static final LocalDate VALID_USER_DATE = LocalDate.of(2025, 1, 15);
    
    // Aliases for common usage
    public static final String VALID_EMAIL = VALID_USER_EMAIL;
    public static final String VALID_PASSWORD = VALID_USER_PASSWORD;
    
    // Type User Constants
    public static final Long OWNER_TYPE_ID = 1L;
    public static final String OWNER_TYPE_NAME = "DONO";
    public static final Long CLIENT_TYPE_ID = 2L;
    public static final String CLIENT_TYPE_NAME = "CLIENTE";
    
    // Address Constants
    public static final String VALID_STREET = "Rua Teste";
    public static final String VALID_NEIGHBORHOOD = "Centro";
    public static final String VALID_COMPLEMENT = "Apto 101";
    public static final Integer VALID_NUMBER = 123;
    public static final String VALID_STATE = "São Paulo";
    public static final String VALID_CITY = "São Paulo";
    public static final String VALID_CEP = "01234567";
    
    // Menu Item Constants
    public static final String VALID_MENU_NAME = "Pizza Margherita";
    public static final String VALID_MENU_DESCRIPTION = "Deliciosa pizza com molho de tomate, queijo mussarela e manjericão";
    public static final String VALID_MENU_PRICE = "29.90";
    public static final Boolean VALID_MENU_AVAILABILITY = true;
    public static final String VALID_MENU_IMAGE = "https://example.com/pizza-margherita.jpg";
    
    // Restaurant Constants
    public static final Long VALID_RESTAURANT_ID = 1L;
    public static final String VALID_RESTAURANT_NAME = "Restaurante Teste";
    public static final String VALID_RESTAURANT_KITCHEN_TYPE = "Italiana";
    public static final String VALID_RESTAURANT_EMAIL = "restaurante@teste.com";
    public static final String VALID_RESTAURANT_OWNER = "Proprietário Teste";
    
    // Business Hours Constants
    public static final DayOfWeek VALID_DAY_OF_WEEK = DayOfWeek.MONDAY;
    public static final LocalTime VALID_OPENING_TIME = LocalTime.of(8, 0);
    public static final LocalTime VALID_CLOSING_TIME = LocalTime.of(22, 0);
    public static final String VALID_BUSINESS_HOURS_OBSERVATION = "Funcionamento normal";
    
    // Database IDs
    public static final Long VALID_ID = 1L;
    public static final Long INVALID_ID = 999L;
    
    // Error Messages
    public static final String USER_NOT_FOUND_MESSAGE = "Usuário não encontrado";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "Restaurante não encontrado";
    public static final String MENU_ITEM_NOT_FOUND_MESSAGE = "Item do cardápio não encontrado";
    public static final String TYPE_USER_NOT_FOUND_MESSAGE = "Tipo de usuário não encontrado";
    
    private TestConstants() {
        // Utility class
    }
}
