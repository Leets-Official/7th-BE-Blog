package com.example.demo.domain.auth.service;

import com.example.demo.domain.auth.dto.LoginResponse;
import com.example.demo.domain.auth.dto.LoginRequest;
import com.example.demo.domain.auth.dto.SignUpRequest;
import com.example.demo.domain.auth.dto.SignUpResponse;
import com.example.demo.domain.auth.dto.TokenRefreshRequest;
import com.example.demo.domain.auth.dto.TokenRefreshResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.security.CustomUserPrincipal;
import com.example.demo.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(HttpStatus.CONFLICT, "EMAIL_ALREADY_EXISTS", "이미 등록된 이메일입니다.");
        }
        if (userRepository.existsByName(request.nickname())) {
            throw new CustomException(HttpStatus.CONFLICT, "NICKNAME_ALREADY_EXISTS", "이미 등록된 닉네임입니다.");
        }

        User savedUser = userRepository.save(
                User.register(
                        request.email(),
                        request.nickname(),
                        passwordEncoder.encode(request.password())
                )
        );

        return new SignUpResponse(savedUser.getEmail(), savedUser.getNickname());
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
            User user = findUser(principal.getId());
            return issueLoginTokens(user, principal);
        } catch (AuthenticationException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "INVALID_LOGIN", "이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    @Transactional
    public TokenRefreshResponse refresh(TokenRefreshRequest request) {
        jwtTokenProvider.validateToken(request.refreshToken());

        Long userId = jwtTokenProvider.getUserId(request.refreshToken());
        User user = findUser(userId);

        if (user.getRefreshToken() == null || !user.getRefreshToken().equals(request.refreshToken())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN", "유효하지 않은 리프레시 토큰입니다.");
        }

        CustomUserPrincipal principal = CustomUserPrincipal.from(user);
        return issueRefreshTokens(user, principal);
    }

    @Transactional
    public void logout(Long userId) {
        User user = findUser(userId);
        user.clearRefreshToken();
    }

    private LoginResponse issueLoginTokens(User user, CustomUserPrincipal principal) {
        String accessToken = jwtTokenProvider.createAccessToken(principal);
        String refreshToken = jwtTokenProvider.createRefreshToken(principal);

        user.updateRefreshToken(refreshToken);

        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
    }

    private TokenRefreshResponse issueRefreshTokens(User user, CustomUserPrincipal principal) {
        String accessToken = jwtTokenProvider.createAccessToken(principal);
        String refreshToken = jwtTokenProvider.createRefreshToken(principal);

        user.updateRefreshToken(refreshToken);

        return new TokenRefreshResponse(
                accessToken,
                refreshToken,
                "Bearer"
        );
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."));
    }
}
