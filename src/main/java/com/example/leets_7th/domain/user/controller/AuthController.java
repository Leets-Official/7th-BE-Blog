package com.example.leets_7th.domain.user.controller;

import com.example.leets_7th.common.response.ApiResponse;
import com.example.leets_7th.common.status.SuccessStatus;
import com.example.leets_7th.domain.user.controller.docs.AuthControllerDocs;
import com.example.leets_7th.domain.user.dto.request.LoginRequest;
import com.example.leets_7th.domain.user.dto.request.SignUpRequest;
import com.example.leets_7th.domain.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(
            @Valid @RequestBody SignUpRequest request
    ) {
        authService.signup(request);
        return ApiResponse.success(SuccessStatus.CREATE_USER_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        authService.login(request, response);
        return ApiResponse.success(SuccessStatus.LOGIN_SUCCESS);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        authService.logout(response);
        return ApiResponse.success(SuccessStatus.LOGOUT_SUCCESS);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<Void>> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authService.refresh(request, response);
        return ApiResponse.success(SuccessStatus.REISSUE_SUCCESS);
    }
}
