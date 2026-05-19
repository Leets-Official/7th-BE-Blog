package com.leets.blog.domain.auth.client;

import com.leets.blog.common.exception.BaseErrorCode;
import com.leets.blog.common.exception.GeneralException;
import com.leets.blog.domain.auth.dto.KakaoTokenResponse;
import com.leets.blog.domain.auth.dto.KakaoUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KakaoOAuthClient {

    private static final String AUTHORIZATION_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String AUTHORIZATION_CODE_GRANT_TYPE = "authorization_code";
    private static final String CODE_RESPONSE_TYPE = "code";

    private final RestClient restClient = RestClient.create();

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromUriString(AUTHORIZATION_URL)
                .queryParam("response_type", CODE_RESPONSE_TYPE)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .build()
                .encode()
                .toUriString();
    }

    public KakaoTokenResponse requestToken(String code) {
        try {
            KakaoTokenResponse response = restClient.post()
                    .uri(TOKEN_URL)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(createTokenRequestBody(code))
                    .retrieve()
                    .body(KakaoTokenResponse.class);

            if (response == null || !StringUtils.hasText(response.accessToken())) {
                throw new GeneralException(BaseErrorCode.KAKAO_LOGIN_FAILED);
            }
            return response;
        } catch (RestClientException exception) {
            throw new GeneralException(BaseErrorCode.KAKAO_LOGIN_FAILED);
        }
    }

    public KakaoUserResponse requestUserInfo(String accessToken) {
        try {
            KakaoUserResponse response = restClient.get()
                    .uri(USER_INFO_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .body(KakaoUserResponse.class);

            if (response == null || response.id() == null) {
                throw new GeneralException(BaseErrorCode.KAKAO_LOGIN_FAILED);
            }
            return response;
        } catch (RestClientException exception) {
            throw new GeneralException(BaseErrorCode.KAKAO_LOGIN_FAILED);
        }
    }

    private LinkedMultiValueMap<String, String> createTokenRequestBody(String code) {
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", AUTHORIZATION_CODE_GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        if (StringUtils.hasText(clientSecret)) {
            body.add("client_secret", clientSecret);
        }

        return body;
    }
}
