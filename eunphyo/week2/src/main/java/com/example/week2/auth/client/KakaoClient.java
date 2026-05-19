package com.example.week2.auth.client;

import com.example.week2.auth.dto.KakaoResponse;
import com.example.week2.global.response.CustomException;
import com.example.week2.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoClient {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret:}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    @Value("${kakao.authorization-uri}")
    private String authorizationUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromUriString(authorizationUri)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .build()
                .encode()
                .toUriString();
    }

    public String getAccessToken(String code) {

        String url = tokenUri;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        if (StringUtils.hasText(clientSecret)) {
            body.add("client_secret", clientSecret);
        }

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<KakaoResponse.TokenResponse> response;
        try {
            response = restTemplate.postForEntity(
                    url,
                    request,
                    KakaoResponse.TokenResponse.class
            );
        } catch (HttpClientErrorException e) {
            log.warn("Kakao token request failed. status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new IllegalArgumentException("카카오 토큰 요청 실패: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
        } catch (RestClientException e) {
            log.warn("Kakao token request failed.", e);
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        KakaoResponse.TokenResponse tokenBody = response.getBody();
        if (tokenBody == null || tokenBody.accessToken() == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        return tokenBody.accessToken();
    }

    public KakaoResponse.KakaoUserInfo getUserInfo(String accessToken) {

        String url = userInfoUri;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoResponse.KakaoUserResponse> response;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    KakaoResponse.KakaoUserResponse.class
            );
        } catch (HttpClientErrorException e) {
            log.warn("Kakao user info request failed. status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new IllegalArgumentException("카카오 사용자 정보 요청 실패: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
        } catch (RestClientException e) {
            log.warn("Kakao user info request failed.", e);
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        KakaoResponse.KakaoUserResponse body = response.getBody();
        if (body == null || body.id() == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        KakaoResponse.KakaoAccount kakaoAccount = body.kakaoAccount();
        String email = kakaoAccount == null || kakaoAccount.email() == null
                ? null
                : kakaoAccount.email();

        String nickname = kakaoAccount == null || kakaoAccount.profile() == null || kakaoAccount.profile().nickname() == null
                ? "kakao_" + body.id()
                : kakaoAccount.profile().nickname();

        return new KakaoResponse.KakaoUserInfo(
                String.valueOf(body.id()),
                email,
                nickname
        );
    }
}
