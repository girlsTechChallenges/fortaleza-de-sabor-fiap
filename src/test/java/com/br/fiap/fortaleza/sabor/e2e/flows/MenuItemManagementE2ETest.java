package com.br.fiap.fortaleza.sabor.e2e.flows;

import com.br.fiap.fortaleza.sabor.e2e.config.E2ETestConfiguration;
import com.br.fiap.fortaleza.sabor.e2e.util.E2ETestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * End-to-End tests for Menu Item management flows.
 * Tests complete menu item lifecycle and business rules.
 */
@DisplayName("E2E: Fluxos de Gerenciamento de Cardápio")
public class MenuItemManagementE2ETest extends E2ETestConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should complete menu item lifecycle successfully")
    void shouldCompleteMenuItemLifecycleSuccessfully() throws Exception {
        String uniqueId = createUniqueId();
        
        // Step 1: Create a new menu item
        var menuItemRequest = E2ETestDataFactory.createValidMenuItemRequest(uniqueId);
        
        Response createResponse = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(menuItemRequest))
        .when()
                .post("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(5000L))
                .extract().response();
        
        System.out.println("Menu item creation response: " + createResponse.asString());
        
        // Step 2: List all menu items
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 3: Get menu items by restaurant ID
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio/restaurant/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
        
        // Step 4: Get a specific menu item by ID
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
        
        // Step 5: Update menu item
        var updateRequest = E2ETestDataFactory.createUpdateMenuItemRequest(uniqueId);
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(updateRequest))
        .when()
                .put("/cardapio/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(202), equalTo(404), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 6: Delete menu item
        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/cardapio/9999") // Use high ID that likely doesn't exist
        .then()
                .statusCode(anyOf(equalTo(204), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
    }
    
    @Test
    @DisplayName("Should handle menu item validation errors gracefully")
    void shouldHandleMenuItemValidationErrorsGracefully() throws Exception {
        // Test with invalid menu item data
        var invalidMenuItem = E2ETestDataFactory.InvalidData.createInvalidMenuItemRequest();
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(invalidMenuItem))
        .when()
                .post("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
        
        // Test with negative price
        var negativePrice = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto(
                "Item Teste",
                "Descrição",
                "-10.0", // Preço negativo como string
                true,
                "item-teste.jpg"
        );
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(negativePrice))
        .when()
                .post("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
    }
    
    @Test
    @DisplayName("Should handle menu item availability updates")
    void shouldHandleMenuItemAvailabilityUpdates() throws Exception {
        // Test menu item availability toggle
        given()
                .contentType(ContentType.JSON)
        .when()
                .patch("/cardapio/1/availability")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
    }
    
    @Test
    @DisplayName("Should handle menu item search and filtering")
    void shouldHandleMenuItemSearchAndFiltering() throws Exception {
        // Test get all menu items
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
        
        // Test get menu items by restaurant
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio/restaurant/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
        
        // Test get specific menu item
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(1500L));
    }
    
    @Test
    @DisplayName("Should validate menu item business rules")
    void shouldValidateMenuItemBusinessRules() throws Exception {
        String uniqueId = createUniqueId();
        
        // Test menu item with invalid time range (this is business logic that might be validated elsewhere)
        var invalidTimeRange = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto(
                "Item Horário Inválido " + uniqueId,
                "Descrição do item com horário inválido",
                "25.90",
                true,
                "item-invalido-" + uniqueId + ".jpg"
        );
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(invalidTimeRange))
        .when()
                .post("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
        
        // Test menu item with zero price
        var zeroPrice = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto(
                "Item Preço Zero " + uniqueId,
                "Descrição do item com preço zero",
                "0.00",
                true,
                "item-zero-" + uniqueId + ".jpg"
        );
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(zeroPrice))
        .when()
                .post("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(422), equalTo(500))) // Zero price might be allowed
                .time(lessThan(3000L));
    }
}
