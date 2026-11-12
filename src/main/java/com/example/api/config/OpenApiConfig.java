package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        final String esquema = "bearerAuth";
        return new OpenAPI()
            .info(new Info()
                .title("API REST E-commerce - Jorge Caruajulca")
                .description("Backend de productos con Spring Boot, JPA y seguridad JWT")
                .version("1.0.0")
                .contact(new Contact().name("Jorge Caruajulca")))
            .addSecurityItem(new SecurityRequirement().addList(esquema))
            .components(new Components().addSecuritySchemes(esquema,
                new SecurityScheme()
                    .name(esquema)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }
}
