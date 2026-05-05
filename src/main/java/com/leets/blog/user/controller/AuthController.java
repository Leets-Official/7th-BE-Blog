package com.leets.blog.user.controller;

import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.user.dto.AuthRequest;
import com.leets.blog.user.dto.AuthResponse;
import com.leets.blog.user.service.AuthService;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "auth-controller", description = "회원가입/로그인 및 내 정보 조회 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(
            summary = "회원가입",
            description = "이메일/비밀번호/닉네임으로 회원가입합니다."
    )
    public ResponseEntity<BaseResponse<AuthResponse.UserInfo>> signUp(@Valid @RequestBody AuthRequest.SignUp request) {
        AuthResponse.UserInfo response = authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(response));
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "이메일/비밀번호로 로그인합니다. (현재는 토큰 대신 Swagger 임시 인증용 사용자 정보를 반환합니다)"
    )
    public ResponseEntity<BaseResponse<AuthResponse.Login>> login(@Valid @RequestBody AuthRequest.Login request) {
        AuthResponse.Login response = authService.login(request);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 정보 조회",
            description = "요청 헤더 `X-USER-ID` 기준으로 내 정보를 조회합니다."
    )
    public ResponseEntity<BaseResponse<AuthResponse.UserInfo>> me(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser
    ) {
        AuthResponse.UserInfo response = new AuthResponse.UserInfo(
                authUser.getUserId(),
                authUser.getEmail(),
                authUser.getNickname(),
                authUser.getRole()
        );
        return ResponseEntity.ok(BaseResponse.ok(response));
    }
}
