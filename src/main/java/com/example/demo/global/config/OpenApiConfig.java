package com.example.demo.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo API")
                        .description("게시글, 댓글, 신고 도메인을 포함한 데모 서버 API 문서입니다.")
                        .version("v1")
                        .contact(new Contact().name("Byun Seunghyun"))
                        .license(new License().name("Internal Study Project")));
    }
}
