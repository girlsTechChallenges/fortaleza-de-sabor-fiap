package com.br.fiap.fortaleza.sabor.e2e.config;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.config;

/**
 * Base configuration class for E2E tests.
 * Configures REST-assured and Spring Boot test environment for end-to-end testing.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class E2ETestConfiguration {

    @LocalServerPort
    protected int serverPort;

    @BeforeEach
    void setUpE2ETestEnvironment() {
        RestAssured.port = serverPort;
        RestAssured.baseURI = "http://localhost";
        
        // Configure REST-assured for better JSON handling and debugging
        RestAssured.config = config()
                .jsonConfig(JsonConfig.jsonConfig()
                        .numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        
        // Enable detailed logging for debugging test failures
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    
    /**
     * Helper method to create unique identifiers for test data
     */
    protected String createUniqueId() {
        return "e2e_test_" + System.currentTimeMillis();
    }
    
    /**
     * Helper method to create unique email addresses for testing
     */
    protected String createUniqueEmail() {
        return "e2e.test." + System.currentTimeMillis() + "@fortaleza-sabor.com";
    }
}
