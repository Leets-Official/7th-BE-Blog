package com.example.blog.domain.auth.client;

import com.example.blog.domain.auth.dto.KakaoTokenResponse;
import com.example.blog.domain.auth.dto.KakaoUserInfo;
import com.example.blog.global.exception.BusinessException;
import com.example.blog.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    public KakaoTokenResponse getToken(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        return webClientBuilder.build()
            .post()
            .uri(tokenUri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                response -> Mono.error(new BusinessException(ErrorCode.KAKAO_AUTH_FAILED))
            )
            .bodyToMono(KakaoTokenResponse.class)
            .block();
    }

    public KakaoUserInfo getUserInfo(String accessToken) {
        return webClientBuilder.build()
            .get()
            .uri(userInfoUri)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                response -> Mono.error(new BusinessException(ErrorCode.KAKAO_USER_INFO_FAILED))
            )
            .bodyToMono(KakaoUserInfo.class)
            .block();
    }
}
