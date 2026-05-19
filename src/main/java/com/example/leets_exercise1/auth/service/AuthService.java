package com.example.leets_exercise1.auth.service;

import com.example.leets_exercise1.auth.dto.request.LoginRequest;
import com.example.leets_exercise1.auth.dto.request.KakaoLoginRequest;
import com.example.leets_exercise1.auth.dto.request.SignUpRequest;
import com.example.leets_exercise1.auth.dto.request.TokenReissueRequest;
import com.example.leets_exercise1.auth.dto.response.KakaoLoginUrlResponse;
import com.example.leets_exercise1.auth.dto.response.LoginResponse;
import com.example.leets_exercise1.auth.dto.response.SignUpResponse;
import com.example.leets_exercise1.auth.dto.response.TokenReissueResponse;

public interface AuthService {
    SignUpResponse signUp(SignUpRequest request);

    LoginResponse login(LoginRequest request);

    KakaoLoginUrlResponse getKakaoLoginUrl();

    LoginResponse kakaoLogin(KakaoLoginRequest request);

    TokenReissueResponse reissue(TokenReissueRequest request);
}
