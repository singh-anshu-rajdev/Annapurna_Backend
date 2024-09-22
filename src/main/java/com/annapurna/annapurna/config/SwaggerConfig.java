package com.annapurna.annapurna.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Annapurna",
                description = "Open API Documentation for Annapurna",
                summary = "An application to fulfill your stomach hunger. Directly from the kitchen to your table",
                termsOfService = "Always have good Food",
                contact = @Contact(
                        name = "Anshu Singh",
                        email = "anshu0007singh@gmail.com"
                ),
                license = @License(
                        name = "Annapurna 1.0"
                ),
                version = "v1"
        ),
        servers = {
                @Server(
                    description = "Dev",
                    url = "http://localhost:8080"
                )
        },
        security = @SecurityRequirement(
                name = "auth"
        )
)
@SecurityScheme(
        name = "auth",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer",
        description = "Jwt Security"
)
public class SwaggerConfig {
}
