package com.leets.blog.user.oauth;

import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.security.KakaoOAuthProperties;
import com.leets.blog.user.dto.KakaoOAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {

    private final KakaoOAuthProperties kakaoOAuthProperties;
    private final RestClient restClient;

    public KakaoOAuthResponse.UserInfo getUserInfo(String code) {
        try {
            KakaoOAuthResponse.Token token = requestToken(code);
            return requestUserInfo(token.getAccessToken());
        } catch (RestClientException | IllegalArgumentException ex) {
            throw new BusinessException(ErrorCode.KAKAO_LOGIN_FAILED);
        }
    }

    private KakaoOAuthResponse.Token requestToken(String code) {
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoOAuthProperties.getClientId());
        body.add("redirect_uri", kakaoOAuthProperties.getRedirectUri());
        body.add("code", code);

        if (StringUtils.hasText(kakaoOAuthProperties.getClientSecret())) {
            body.add("client_secret", kakaoOAuthProperties.getClientSecret());
        }

        KakaoOAuthResponse.Token token = restClient.post()
                .uri(kakaoOAuthProperties.getTokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(KakaoOAuthResponse.Token.class);

        if (token == null || !StringUtils.hasText(token.getAccessToken())) {
            throw new BusinessException(ErrorCode.KAKAO_LOGIN_FAILED);
        }

        return token;
    }

    private KakaoOAuthResponse.UserInfo requestUserInfo(String accessToken) {
        KakaoOAuthResponse.UserInfo userInfo = restClient.get()
                .uri(kakaoOAuthProperties.getUserInfoUri())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(KakaoOAuthResponse.UserInfo.class);

        if (userInfo == null || userInfo.getId() == null) {
            throw new BusinessException(ErrorCode.KAKAO_LOGIN_FAILED);
        }

        return userInfo;
    }
}
