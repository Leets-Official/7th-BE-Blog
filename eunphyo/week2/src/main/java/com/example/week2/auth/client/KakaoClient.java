package com.example.week2.auth.client;

import com.example.week2.auth.dto.KakaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoClient {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken(String code) {

        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<KakaoResponse.TokenResponse> response =
                restTemplate.postForEntity(
                        url,
                        request,
                        KakaoResponse.TokenResponse.class
                );

        return response.getBody().accessToken();
    }

    public KakaoResponse.KakaoUserInfo getUserInfo(String accessToken) {

        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoResponse.KakaoUserResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        request,
                        KakaoResponse.KakaoUserResponse.class
                );

        KakaoResponse.KakaoUserResponse body = response.getBody();

        return new KakaoResponse.KakaoUserInfo(
                body.kakao_account().email(),
                body.kakao_account().profile().nickname()
        );
    }
}