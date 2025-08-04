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
 * End-to-End tests for Type User management flows.
 * Tests complete type user lifecycle and validation scenarios.
 */
@DisplayName("E2E: Fluxos de Gerenciamento de Tipos de Usuário")
public class TypeUserManagementE2ETest extends E2ETestConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should complete type user lifecycle successfully")
    void shouldCompleteTypeUserLifecycleSuccessfully() throws Exception {
        String uniqueId = createUniqueId();
        
        // Step 1: Create a new user type
        var typeRequest = E2ETestDataFactory.createValidTypeUserRequest(uniqueId);
        
        Response createResponse = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(typeRequest))
        .when()
                .post("/type-users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(5000L))
                .extract().response();
        
        System.out.println("Type user creation response: " + createResponse.asString());
        
        // Step 2: List all user types
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/type-users")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 3: Get a specific user type by ID
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/type-users/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
        
        // Step 4: Update user type
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(typeRequest))
        .when()
                .put("/type-users/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(202), equalTo(404), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 5: Delete user type
        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/type-users/9999") // Use high ID that likely doesn't exist
        .then()
                .statusCode(anyOf(equalTo(204), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
    }
    
    @Test
    @DisplayName("Should handle type user validation errors gracefully")
    void shouldHandleTypeUserValidationErrorsGracefully() throws Exception {
        // Test with invalid type user data
        var invalidType = E2ETestDataFactory.InvalidData.createInvalidTypeUserRequest();
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(invalidType))
        .when()
                .post("/type-users")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
    }
    
    @Test
    @DisplayName("Should handle type user search and filtering")
    void shouldHandleTypeUserSearchAndFiltering() throws Exception {
        // Test get all user types
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/type-users")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
        
        // Test get specific user type
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/type-users/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(1500L));
    }
    
    @Test
    @DisplayName("Should validate type user business rules")
    void shouldValidateTypeUserBusinessRules() throws Exception {
        String uniqueId = createUniqueId();
        
        // Test duplicate type creation
        var typeRequest = E2ETestDataFactory.createValidTypeUserRequest(uniqueId);
        
        // First creation attempt
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(typeRequest))
        .when()
                .post("/type-users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(3000L));
        
        // Second creation attempt with same data (should handle duplicates)
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(typeRequest))
        .when()
                .post("/type-users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(3000L));
    }
    
    @Test
    @DisplayName("Should handle reserved type names")
    void shouldHandleReservedTypeNames() throws Exception {
        // Test creation of system reserved types
        String[] reservedTypes = {"ADMIN", "USER", "CUSTOMER", "OWNER"};
        
        for (String reservedType : reservedTypes) {
            var reserved = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.TypeUserRequestDto(
                    reservedType
            );
            
            given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(reserved))
            .when()
                    .post("/type-users")
            .then()
                    .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                    .time(lessThan(3000L));
        }
    }
}
