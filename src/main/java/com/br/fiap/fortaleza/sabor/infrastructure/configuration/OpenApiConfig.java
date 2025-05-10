package com.br.fiap.fortaleza.sabor.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fortaleza de Sabor API")
                        .version("1.0.0")
                        .description("API para gerenciamento do restaurante Fortaleza de Sabor")
                        .contact(new Contact()
                                .name("Equipe Fortaleza de Sabor")
                                .email("contato@fortalezadesabor.com")
                        )
                );
    }
}
