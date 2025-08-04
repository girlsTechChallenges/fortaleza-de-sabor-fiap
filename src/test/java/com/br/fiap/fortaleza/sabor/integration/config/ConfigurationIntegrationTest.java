package com.br.fiap.fortaleza.sabor.integration.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Simple test to verify integration test configuration is working.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Integration Test Configuration Verification")
public class ConfigurationIntegrationTest {

    @Test
    @DisplayName("Should load Spring Boot context successfully")
    void shouldLoadSpringBootContext() {
        // This test verifies that the Spring Boot application context loads correctly
        // with the test configuration
        assertTrue(true, "Spring Boot context loaded successfully");
    }
}
