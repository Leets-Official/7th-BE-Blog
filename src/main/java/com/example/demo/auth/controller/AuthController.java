package com.example.demo.auth.controller;

import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.ReissueRequest;
import com.example.demo.auth.dto.SignupRequest;
import com.example.demo.auth.dto.TokenResponse;
import com.example.demo.auth.service.AuthService;
import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ErrorResponse;
import com.example.demo.global.exception.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Auth", description = "회원가입 및 로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "회원가입 API",
            description = "이메일, 닉네임, 비밀번호를 입력받아 회원가입을 진행합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "회원가입 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "유효성 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "이미 존재하는 이메일 또는 닉네임",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/signup")
    public ApiResponse<Map<String, Long>> signup(
            @RequestBody @Valid SignupRequest request) {

        Long userId = authService.signup(request);

        return ResponseUtil.success(
                BaseCode.USER_CREATE_SUCCESS,
                Map.of("userId", userId)
        );
    }

    @Operation(
            summary = "로그인 API",
            description = "이메일과 비밀번호로 로그인하고 accessToken, refreshToken을 발급받습니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 로그인 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "이메일 또는 비밀번호 불일치",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(
            @RequestBody @Valid LoginRequest request) {

        return ResponseUtil.success(
                BaseCode.SUCCESS,
                authService.login(request)
        );
    }

    @Operation(
            summary = "토큰 재발급 API",
            description = "refreshToken을 이용해 새로운 accessToken과 refreshToken을 발급받습니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "토큰 재발급 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 refreshToken",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/reissue")
    public ApiResponse<TokenResponse> reissue(
            @RequestBody @Valid ReissueRequest request) {

        return ResponseUtil.success(
                BaseCode.SUCCESS,
                authService.reissue(request)
        );
    }
}
