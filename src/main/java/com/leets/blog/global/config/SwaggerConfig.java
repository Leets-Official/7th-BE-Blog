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
                        "ACCESS_TOKEN",
                        new SecurityScheme()
                                .name("ACCESS_TOKEN")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                ))
                .addSecurityItem(new SecurityRequirement().addList("ACCESS_TOKEN"))
                .info(new Info()
                        .title("Leets Blog API")
                        .description("게시물/댓글/신고 상태 전이 API 문서\n\n인증: 로그인 후 발급되는 ACCESS_TOKEN 쿠키를 사용합니다.")
                        .version("1.0.0"));
    }
}
