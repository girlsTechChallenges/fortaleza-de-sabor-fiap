package com.br.fiap.fortaleza.sabor.cucumber.steps;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseSteps {
    
    protected static Response lastResponse;
    protected static String baseUrl = "http://localhost:8080";
    
    static {
        RestAssured.baseURI = baseUrl;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        System.out.println("✓ RestAssured configurado com base URL: " + baseUrl);
    }
    
    protected String createUniqueId() {
        return String.valueOf(System.currentTimeMillis());
    }
    
    protected String createUniqueEmail() {
        return "test" + createUniqueId() + "@example.com";
    }
}
