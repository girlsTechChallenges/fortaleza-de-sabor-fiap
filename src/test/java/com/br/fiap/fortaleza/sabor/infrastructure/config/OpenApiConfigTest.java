package com.br.fiap.fortaleza.sabor.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void shouldCreateCustomOpenApiBean() {
        OpenApiConfig config = new OpenApiConfig();

        OpenAPI openAPI = config.customOpenAPI();

        assertNotNull(openAPI);

        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("Fortaleza de Sabor API", info.getTitle());
        assertEquals("1.0.0", info.getVersion());
        assertEquals("API para gerenciamento do restaurante Fortaleza de Sabor", info.getDescription());

        Contact contact = info.getContact();
        assertNotNull(contact);
        assertEquals("Equipe Fortaleza de Sabor", contact.getName());
        assertEquals("contato@fortalezadesabor.com", contact.getEmail());

        Components components = openAPI.getComponents();
        assertNotNull(components);
        assertTrue(components.getResponses().containsKey("200"));
        assertEquals("Success", components.getResponses().get("200").getDescription());
        assertEquals("Accepted", components.getResponses().get("202").getDescription());
        assertEquals("No Content", components.getResponses().get("204").getDescription());
        assertEquals("Bad Request", components.getResponses().get("400").getDescription());
        assertEquals("Not Found", components.getResponses().get("404").getDescription());
        assertEquals("Internal Server Error", components.getResponses().get("500").getDescription());
    }
}
