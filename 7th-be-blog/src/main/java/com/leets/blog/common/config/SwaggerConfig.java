package com.leets.blog.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "7th BE Blog API",
                description = "블로그 게시글, 좋아요, 신고 기능을 제공하는 API 문서입니다.",
                version = "v1",
                contact = @Contact(name = "7th BE Team")
        ),
        servers = {
                @Server(url = "/", description = "Default Server")
        }
)
public class SwaggerConfig {
}
