package com.openclassrooms.ycyw.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuration class for OpenAPI documentation.
 * <p>
 * This class sets up the OpenAPI specification for the API, defining metadata such as title,
 * version, description, and security scheme.
 * </p>
 */
@Configuration
public class OpenAPIConfiguration {

    /**
     * Defines the OpenAPI specification with metadata and security configurations.
     *
     * @return an {@link OpenAPI} instance with the API details and security settings.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact().name("Axel").email("axelker@outlook.fr");
        Info information = new Info()
                .title("Your car your way System API")
                .version("1.0")
                .description("This API exposes endpoints to manage rentals car.")
                .contact(contact);

        return new OpenAPI()
                .info(information)
                .addSecurityItem(new SecurityRequirement().addList("JWT-Cookie"))
                .components(new Components()
                        .addSecuritySchemes("JWT-Cookie", new SecurityScheme()
                                .name("jwt")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)));

    }
}