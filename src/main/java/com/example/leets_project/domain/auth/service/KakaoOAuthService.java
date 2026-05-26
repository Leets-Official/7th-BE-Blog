package com.example.leets_project.domain.auth.service;

import com.example.leets_project.domain.auth.oauth.kakao.KakaoOAuthClient;
import com.example.leets_project.domain.auth.oauth.kakao.dto.KakaoTokenResponse;
import com.example.leets_project.domain.auth.oauth.kakao.dto.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    private final KakaoOAuthClient kakaoOAuthClient;

    public KakaoUserInfo getUserInfo(String code) {
        KakaoTokenResponse token = kakaoOAuthClient.requestToken(code);
        return kakaoOAuthClient.requestUserInfo(token.accessToken());
    }
}