package com.leets.blog.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(
                        "X-USER-ID",
                        new SecurityScheme()
                                .name("X-USER-ID")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                ))
                .addSecurityItem(new SecurityRequirement().addList("X-USER-ID"))
                .info(new Info()
                        .title("Leets Blog API")
                        .description("게시물/댓글/신고 상태 전이 API 문서")
                        .version("1.0.0"));
    }
}