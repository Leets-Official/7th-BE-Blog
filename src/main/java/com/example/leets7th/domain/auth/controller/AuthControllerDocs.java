package com.example.leets7th.domain.auth.controller;

import com.example.leets7th.domain.auth.dto.LoginRequest;
import com.example.leets7th.domain.auth.dto.SignUpRequest;
import com.example.leets7th.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthControllerDocs {

    @Operation(summary = "이메일 회원가입", description = "이메일, 비밀번호, 닉네임으로 회원가입합니다. 이메일과 닉네임은 중복될 수 없습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이메일 또는 닉네임 중복")
    })
    ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody SignUpRequest request);

    @Operation(summary = "이메일 로그인", description = "이메일과 비밀번호로 로그인합니다. 성공 시 accessToken(1시간)과 refreshToken(14일)이 HttpOnly 쿠키로 발급됩니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치")
    })
    ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody LoginRequest request,
                                             HttpServletResponse response);

    @Operation(summary = "토큰 재발급", description = "refreshToken 쿠키를 검증하여 새로운 accessToken 쿠키를 발급합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "재발급 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "유효하지 않은 리프레시 토큰")
    })
    ResponseEntity<ApiResponse<Void>> refresh(@CookieValue(name = "refreshToken") String refreshToken,
                                               HttpServletResponse response);

    @Operation(summary = "로그아웃", description = "accessToken, refreshToken 쿠키를 삭제합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그아웃 성공")
    ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response);
}
