package com.leets.blog.user.service;

import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.security.JwtTokenProvider;
import com.leets.blog.user.domain.User;
import com.leets.blog.user.domain.UserRole;
import com.leets.blog.user.dto.AuthRequest;
import com.leets.blog.user.dto.AuthResponse;
import com.leets.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse.UserInfo signUp(AuthRequest.SignUp request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                UserRole.USER
        );

        User savedUser = userRepository.save(user);
        return new AuthResponse.UserInfo(savedUser);
    }

    public AuthResponse.Login login(AuthRequest.Login request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }

        String accessToken = jwtTokenProvider.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole().name()
        );
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        return new AuthResponse.Login(user, accessToken, refreshToken);
    }

    public String reissueAccessToken(String refreshToken) {
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken) || !jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        Long userId = jwtTokenProvider.getUserId(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return jwtTokenProvider.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole().name()
        );
    }

    public AuthResponse.UserInfo findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return new AuthResponse.UserInfo(user);
    }
}
