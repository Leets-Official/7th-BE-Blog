package com.leets.blog.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private long accessTokenExpirationSeconds = 900;
    private long refreshTokenExpirationSeconds = 1209600;
    private String issuer = "leets-blog";
    private String accessCookieName = "ACCESS_TOKEN";
    private String refreshCookieName = "REFRESH_TOKEN";
    private boolean secure = false;
    private boolean httpOnly = true;
    private String sameSite = "Lax";
}
