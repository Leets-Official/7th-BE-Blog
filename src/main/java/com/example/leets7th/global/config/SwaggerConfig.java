package com.example.leets7th.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        Info info = new Info().title("Leets 7th BE Blog").description("Leets Study Test Swagger").version("0.0.1");

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"));
    }
}
