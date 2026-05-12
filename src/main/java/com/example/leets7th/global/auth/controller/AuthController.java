package com.example.leets7th.global.auth.controller;

import com.example.leets7th.global.auth.dto.req.LoginRequestDTO;
import com.example.leets7th.global.auth.dto.req.SignUpRequestDTO;
import com.example.leets7th.global.auth.dto.res.AuthResponseDTO;
import com.example.leets7th.global.auth.exception.code.AuthSuccessCode;
import com.example.leets7th.global.auth.service.AuthService;
import com.example.leets7th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임으로 회원가입합니다.")
    @PostMapping("/signup")
    public ApiResponse<AuthResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO req) {
        return ApiResponse.onSuccess(AuthSuccessCode.SIGNUP_SUCCESS, authService.signUp(req));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다. 성공 시 세션 쿠키(JSESSIONID)가 발급됩니다.")
    @PostMapping("/login")
    public ApiResponse<AuthResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO req,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ApiResponse.onSuccess(AuthSuccessCode.LOGIN_SUCCESS, authService.login(req, request, response));
    }

    @Operation(summary = "로그아웃", description = "현재 세션을 무효화합니다.")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {
        authService.logout(request);
        return ApiResponse.onSuccess(AuthSuccessCode.LOGOUT_SUCCESS, null);
    }

    @Operation(summary = "회원탈퇴", description = "계정을 삭제하고 세션을 무효화합니다.")
    @DeleteMapping("/withdraw")
    public ApiResponse<Void> withdraw(HttpServletRequest request) {
        authService.withdraw(request);
        return ApiResponse.onSuccess(AuthSuccessCode.WITHDRAW_SUCCESS, null);
    }
}
