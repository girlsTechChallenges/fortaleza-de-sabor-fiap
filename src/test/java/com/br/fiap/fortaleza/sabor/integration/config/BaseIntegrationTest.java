package com.br.fiap.fortaleza.sabor.integration.config;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.config;

/**
 * Base class for integration tests.
 * Provides common configuration for REST-assured and Spring Boot test setup.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = serverPort;
        RestAssured.baseURI = "http://localhost";
        
        // Configure REST-assured for better JSON handling
        RestAssured.config = config()
                .jsonConfig(JsonConfig.jsonConfig()
                        .numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        
        // Set default timeout
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
