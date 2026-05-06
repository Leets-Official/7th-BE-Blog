package com.example.leets_exercise1.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Leets Exercise API")
                        .description("게시글 / 댓글 / 신고 도메인 기반 API 문서입니다.")
                        .version("v1.0.0")
                        .contact(new Contact().name("조연준")))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Local Server")
                ));
    }
}