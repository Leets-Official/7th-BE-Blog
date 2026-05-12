package com.example.leets7th.global.auth.service;

import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import com.example.leets7th.global.auth.CustomUserDetails;
import com.example.leets7th.global.auth.dto.req.LoginRequestDTO;
import com.example.leets7th.global.auth.dto.req.SignUpRequestDTO;
import com.example.leets7th.global.auth.dto.res.AuthResponseDTO;
import com.example.leets7th.global.auth.exception.AuthException;
import com.example.leets7th.global.auth.exception.code.AuthErrorCode;
import com.example.leets7th.global.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final HttpSessionSecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    // 회원가입: 이메일 중복 확인 후 비밀번호 암호화 저장
    @Transactional
    public AuthResponseDTO signUp(SignUpRequestDTO req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .nickname(req.nickname())
                .build();

        return AuthResponseDTO.from(userRepository.save(user));
    }

    // 로그인: 이메일/비밀번호 검증 후 세션에 인증 정보 저장
    public AuthResponseDTO login(LoginRequestDTO req, HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(req.email(), req.password());
            Authentication auth = authenticationManager.authenticate(authToken);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);

            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            return AuthResponseDTO.from(userDetails.getUser());

        } catch (AuthenticationException e) {
            throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS);
        }
    }

    // 로그아웃: 세션 무효화 및 SecurityContext 초기화
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }

    // 회원탈퇴: 계정 소프트 삭제 후 세션 무효화
    @Transactional
    public void withdraw(HttpServletRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        user.delete();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }
}
