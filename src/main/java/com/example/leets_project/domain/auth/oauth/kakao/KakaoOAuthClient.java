package com.example.leets_project.domain.auth.oauth.kakao;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.domain.auth.oauth.kakao.dto.KakaoTokenResponse;
import com.example.leets_project.domain.auth.oauth.kakao.dto.KakaoUserInfo;
import com.example.leets_project.domain.auth.oauth.kakao.dto.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {

    @Qualifier("kakaoAuthWebClient")
    private final WebClient kakaoAuthWebClient;

    @Qualifier("kakaoApiWebClient")
    private final WebClient kakaoApiWebClient;

    private final KakaoOAuthProperties properties;

    // 인가 코드를 카카오 토큰 인증 서버에 전송 -> Access Token 획득
    public KakaoTokenResponse requestToken(String code) {
        try {
            BodyInserters.FormInserter<String> formData = BodyInserters
                    .fromFormData("grant_type", "authorization_code")
                    .with("client_id", properties.clientId())
                    .with("redirect_uri", properties.redirectUri())
                    .with("code", code);

            if (properties.clientSecret() != null && !properties.clientSecret().isBlank()) {
                formData.with("client_secret", properties.clientSecret());
            }

            return kakaoAuthWebClient.post()
                    .uri("/oauth/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .bodyToMono(KakaoTokenResponse.class)
                    .block();
        // 소셜 로그인 실패시 상세 로그 띄우기
        } catch (WebClientResponseException e) {
            log.error("카카오 토큰 요청 실패 status={}, body={}",
                    e.getStatusCode(),
                    e.getResponseBodyAsString()
            );
            throw new GeneralException(ErrorCode.KAKAO_TOKEN_REQUEST_FAILED);
        } catch (Exception e) {
            log.error("카카오 토큰 요청 실패", e);
            throw new GeneralException(ErrorCode.KAKAO_TOKEN_REQUEST_FAILED);
        }
    }

    // 카카오 액세스 토큰 Bearer 헤더에 실어 카카오 서버로부터 유저 개인정보 상세 조회
    public KakaoUserInfo requestUserInfo(String accessToken) {
        try {
            KakaoUserResponse response = kakaoApiWebClient.get()
                    .uri("/v2/user/me")
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .retrieve()
                    .bodyToMono(KakaoUserResponse.class)
                    .block();

            if (response == null || response.id() == null) {
                throw new GeneralException(ErrorCode.KAKAO_USER_INFO_REQUEST_FAILED);
            }

            return KakaoUserInfo.from(response);

        } catch (GeneralException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.KAKAO_USER_INFO_REQUEST_FAILED);
        }
    }
}
