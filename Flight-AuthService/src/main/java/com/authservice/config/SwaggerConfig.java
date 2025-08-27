package com.authservice.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI authServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Authentication Service API")
                    .description("API Documentation for Authentication Service")
                    .version("1.0"));
    }
}
