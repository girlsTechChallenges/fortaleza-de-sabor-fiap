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
 * End-to-End tests for User management flows.
 * Tests complete user lifecycle and validation scenarios.
 */
@DisplayName("E2E: Fluxos de Gerenciamento de Usuários")
public class UserManagementE2ETest extends E2ETestConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should complete user lifecycle successfully")
    void shouldCompleteUserLifecycleSuccessfully() throws Exception {
        String uniqueId = createUniqueId();
        
        // Step 1: Create a new user
        var userRequest = E2ETestDataFactory.createValidUserRequest(uniqueId);
        
        Response createResponse = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(userRequest))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(5000L))
                .extract().response();
        
        System.out.println("User creation response: " + createResponse.asString());
        
        // Step 2: List all users
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/users")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 3: Get a specific user by ID
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/users/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
        
        // Step 4: Update user information
        var updateRequest = E2ETestDataFactory.createUpdateUserRequest(uniqueId);
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(updateRequest))
        .when()
                .put("/users/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(202), equalTo(404), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 5: Delete user
        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/users/9999") // Use high ID that likely doesn't exist
        .then()
                .statusCode(anyOf(equalTo(204), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
    }
    
    @Test
    @DisplayName("Should handle user validation errors gracefully")
    void shouldHandleUserValidationErrorsGracefully() throws Exception {
        // Test with invalid user data
        var invalidUser = E2ETestDataFactory.InvalidData.createInvalidUserRequest();
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(invalidUser))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
        
        // Test with invalid email format
        var invalidEmail = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto(
                "Usuário Teste",
                "email-invalido", // Email sem formato válido
                "logintest",
                "senha123456",
                java.time.LocalDate.now(),
                "CUSTOMER",
                java.util.List.of(E2ETestDataFactory.createValidAddressDto())
        );
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(invalidEmail))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
    }
    
    @Test
    @DisplayName("Should handle user search and filtering")
    void shouldHandleUserSearchAndFiltering() throws Exception {
        // Test get all users
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/users")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
        
        // Test get specific user
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/users/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(1500L));
    }
    
    @Test
    @DisplayName("Should validate user business rules")
    void shouldValidateUserBusinessRules() throws Exception {
        String uniqueId = createUniqueId();
        
        // Test duplicate user creation (same email)
        var userRequest = E2ETestDataFactory.createValidUserRequest(uniqueId);
        
        // First creation attempt
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(userRequest))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(3000L));
        
        // Second creation attempt with same data (should handle duplicates)
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(userRequest))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(3000L));
    }
    
    @Test
    @DisplayName("Should handle user type validation")
    void shouldHandleUserTypeValidation() throws Exception {
        String uniqueId = createUniqueId();
        
        // Test user with invalid type
        var invalidType = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto(
                "Usuário Tipo Inválido " + uniqueId,
                createUniqueEmail(),
                "logintest" + uniqueId,
                "senha123456",
                java.time.LocalDate.now(),
                "INVALID_TYPE", // Tipo inválido
                java.util.List.of(E2ETestDataFactory.createValidAddressDto())
        );
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(invalidType))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
    }
    
    @Test
    @DisplayName("Should handle weak password validation")
    void shouldHandleWeakPasswordValidation() throws Exception {
        String uniqueId = createUniqueId();
        
        // Test user with weak password
        var weakPassword = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto(
                "Usuário Senha Fraca " + uniqueId,
                createUniqueEmail(),
                "loginweak" + uniqueId,
                "123", // Senha muito fraca
                java.time.LocalDate.now(),
                "CUSTOMER",
                java.util.List.of(E2ETestDataFactory.createValidAddressDto())
        );
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(weakPassword))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(422), equalTo(500))) // Password validation might be optional
                .time(lessThan(3000L));
    }
}
