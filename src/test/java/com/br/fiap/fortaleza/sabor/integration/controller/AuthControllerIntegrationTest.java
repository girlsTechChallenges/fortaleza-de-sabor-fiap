package com.br.fiap.fortaleza.sabor.integration.controller;

import com.br.fiap.fortaleza.sabor.integration.config.BaseIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for AuthController endpoints.
 * Tests authentication functionality.
 */
@AutoConfigureWebMvc
@DisplayName("Testes de Integração - Controlador de Autenticação")
public class AuthControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return 401 when credentials are invalid")
    void shouldReturnUnauthorizedForInvalidCredentials() throws Exception {
        var invalidCredentials = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserCredentialsDto(
                "invalid@test.com", 
                "wrongpassword"
        );

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(invalidCredentials))
        .when()
                .post("/auth/login")
        .then()
                .statusCode(401)
                .time(lessThan(2000L));
    }

    @Test
    @DisplayName("Should return error when login request is malformed")
    void shouldReturnErrorForMalformedLoginRequest() throws Exception {
        var malformedRequest = "{\"email\": \"\", \"password\": \"\"}";

        given()
                .contentType(ContentType.JSON)
                .body(malformedRequest)
        .when()
                .post("/auth/login")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(401))) // Accept both status codes  
                .time(lessThan(2000L));
    }

    @Test
    @DisplayName("Should return error when request body is missing")
    void shouldReturnErrorForMissingRequestBody() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .post("/auth/login")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(500))) // Accept both status codes
                .time(lessThan(2000L));
    }

    @Test
    @DisplayName("Should return 404 when trying to update password for non-existent user")
    void shouldReturnNotFoundForNonExistentUserPasswordUpdate() throws Exception {
        var passwordUpdateRequest = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserCredentialsDto(
                "nonexistent@test.com", 
                "newpassword123"
        );

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(passwordUpdateRequest))
        .when()
                .patch("/auth/password")
        .then()
                .statusCode(404)
                .time(lessThan(2000L));
    }

    @Test
    @DisplayName("Should handle invalid email format in password update")
    void shouldHandleInvalidEmailInPasswordUpdate() throws Exception {
        var invalidEmailRequest = new com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserCredentialsDto(
                "invalid-email", 
                "newpassword123"
        );

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(invalidEmailRequest))
        .when()
                .patch("/auth/password")
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(404))) // Accept both status codes
                .time(lessThan(2000L));
    }
}
