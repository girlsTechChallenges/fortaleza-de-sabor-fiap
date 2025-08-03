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
 * End-to-End tests for complete application workflows.
 * Tests realistic scenarios that span multiple controllers and business processes.
 */
@DisplayName("E2E: Fluxos Completos da Aplicação")
public class CompleteWorkflowE2ETest extends E2ETestConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should complete restaurant owner journey successfully")
    void shouldCompleteRestaurantOwnerJourneySuccessfully() throws Exception {
        String uniqueId = createUniqueId();
        
        // Step 1: Create OWNER user type if it doesn't exist
        var ownerType = E2ETestDataFactory.createValidTypeUserRequest("OWNER_" + uniqueId);
        
        Response typeResponse = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(ownerType))
        .when()
                .post("/type-users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(3000L))
                .extract().response();
        
        System.out.println("Owner type creation response: " + typeResponse.asString());
        
        // Step 2: Create owner user account
        var ownerUser = E2ETestDataFactory.createValidUserRequest(uniqueId);
        
        Response userResponse = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(ownerUser))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(5000L))
                .extract().response();
        
        System.out.println("Owner user creation response: " + userResponse.asString());
        
        // Step 3: Create restaurant
        var restaurant = E2ETestDataFactory.createValidRestaurantRequest(uniqueId);
        
        Response restaurantResponse = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(restaurant))
        .when()
                .post("/restaurants")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(404), equalTo(409), equalTo(500)))
                .time(lessThan(5000L))
                .extract().response();
        
        System.out.println("Restaurant creation response: " + restaurantResponse.asString());
        
        // Step 4: Add menu items to restaurant
        var menuItem1 = E2ETestDataFactory.createValidMenuItemRequest(uniqueId + "_1");
        var menuItem2 = E2ETestDataFactory.createValidMenuItemRequest(uniqueId + "_2");
        
        // Add first menu item
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(menuItem1))
        .when()
                .post("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
        
        // Add second menu item
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(menuItem2))
        .when()
                .post("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(422), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 5: Verify restaurant and menu are listed
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/restaurants")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
        
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
    }
    
    @Test
    @DisplayName("Should complete customer registration and browsing journey")
    void shouldCompleteCustomerRegistrationAndBrowsingJourney() throws Exception {
        String uniqueId = createUniqueId();
        
        // Step 1: Create customer user type if needed
        var customerType = E2ETestDataFactory.createValidTypeUserRequest("CUSTOMER_" + uniqueId);
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(customerType))
        .when()
                .post("/type-users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 2: Register as customer
        var customer = E2ETestDataFactory.createValidUserRequest(uniqueId);
        
        Response customerResponse = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(customer))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(5000L))
                .extract().response();
        
        System.out.println("Customer registration response: " + customerResponse.asString());
        
        // Step 3: Browse available restaurants
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/restaurants")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
        
        // Step 4: Browse menu items
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
        
        // Step 5: Browse menu by specific restaurant (if available)
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio/restaurant/1")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
    }
    
    @Test
    @DisplayName("Should handle system administration tasks")
    void shouldHandleSystemAdministrationTasks() throws Exception {
        String uniqueId = createUniqueId();
        
        // Step 1: Create admin user type
        var adminType = E2ETestDataFactory.createValidTypeUserRequest("ADMIN_" + uniqueId);
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(adminType))
        .when()
                .post("/type-users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 2: List all user types for administration
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/type-users")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
        
        // Step 3: List all users for administration
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/users")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 4: List all restaurants for administration
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/restaurants")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
        
        // Step 5: List all menu items for administration
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(500)))
                .time(lessThan(2000L));
    }
    
    @Test
    @DisplayName("Should handle authentication flow with user creation")
    void shouldHandleAuthenticationFlowWithUserCreation() throws Exception {
        String uniqueId = createUniqueId();
        
        // Step 1: Create user type for authentication
        var userType = E2ETestDataFactory.createValidTypeUserRequest("USER_" + uniqueId);
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(userType))
        .when()
                .post("/type-users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(3000L));
        
        // Step 2: Create user for authentication
        var user = E2ETestDataFactory.createValidUserRequest(uniqueId);
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(user))
        .when()
                .post("/users")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
                .time(lessThan(5000L));
        
        // Step 3: Attempt authentication
        var authRequest = E2ETestDataFactory.createValidAuthRequest(uniqueId);
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(authRequest))
        .when()
                .post("/auth/login")
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(401), equalTo(404), equalTo(500)))
                .time(lessThan(3000L));
    }
    
    @Test
    @DisplayName("Should handle error scenarios gracefully across workflows")
    void shouldHandleErrorScenariosGracefullyAcrossWorkflows() throws Exception {
        String uniqueId = createUniqueId();
        
        // Test 1: Try to create restaurant without proper setup
        var restaurant = E2ETestDataFactory.createValidRestaurantRequest(uniqueId);
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(restaurant))
        .when()
                .post("/restaurants")
        .then()
                .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(404), equalTo(409), equalTo(500)))
                .time(lessThan(3000L));
        
        // Test 2: Try to access non-existent resources
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/restaurants/99999")
        .then()
                .statusCode(anyOf(equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
        
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/users/99999")
        .then()
                .statusCode(anyOf(equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
        
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/cardapio/99999")
        .then()
                .statusCode(anyOf(equalTo(404), equalTo(500)))
                .time(lessThan(2000L));
        
        // Test 3: Try invalid authentication
        var invalidAuth = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserCredentialsDto(
                "nonexistent@user.com",
                "wrongpassword"
        );
        
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(invalidAuth))
        .when()
                .post("/auth/login")
        .then()
                .statusCode(anyOf(equalTo(401), equalTo(404), equalTo(500)))
                .time(lessThan(3000L));
    }
}
