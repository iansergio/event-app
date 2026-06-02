package com.events_service.infrastructure.swagger;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Events Service API",
        version = "1.0",
        description = "API para gerenciamento de eventos"
    )
)
public class OpenApiConfig {
}
