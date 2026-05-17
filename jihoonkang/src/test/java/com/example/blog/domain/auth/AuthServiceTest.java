package com.example.blog.domain.auth;

import com.example.blog.domain.auth.dto.LoginRequest;
import com.example.blog.domain.auth.dto.SignUpRequest;
import com.example.blog.domain.auth.entity.RefreshToken;
import com.example.blog.domain.auth.repository.RefreshTokenRepository;
import com.example.blog.domain.auth.service.AuthService;
import com.example.blog.domain.user.entity.Role;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
import com.example.blog.global.exception.BusinessException;
import com.example.blog.global.exception.ErrorCode;
import com.example.blog.global.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    void signUp_이메일_중복_예외() {
        given(userRepository.existsByEmail("dup@example.com")).willReturn(true);

        SignUpRequest request = new SignUpRequest("dup@example.com", "pass123", "닉네임", null);

        assertThatThrownBy(() -> authService.signUp(request))
            .isInstanceOf(BusinessException.class)
            .satisfies(e -> assertThat(((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.EMAIL_ALREADY_EXISTS));
    }

    @Test
    void signUp_닉네임_중복_예외() {
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        given(userRepository.existsByUsername("중복닉네임")).willReturn(true);

        SignUpRequest request = new SignUpRequest("new@example.com", "pass123", "중복닉네임", null);

        assertThatThrownBy(() -> authService.signUp(request))
            .isInstanceOf(BusinessException.class)
            .satisfies(e -> assertThat(((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.USERNAME_ALREADY_EXISTS));
    }

    @Test
    void login_사용자_없음_예외() {
        given(userRepository.findByEmail("no@example.com")).willReturn(Optional.empty());

        LoginRequest request = new LoginRequest("no@example.com", "pass123");

        assertThatThrownBy(() -> authService.login(request, mock(HttpServletResponse.class)))
            .isInstanceOf(BusinessException.class)
            .satisfies(e -> assertThat(((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.USER_NOT_FOUND));
    }

    @Test
    void login_비밀번호_불일치_예외() {
        User user = User.of("지훈", "jihoon@example.com", "hashedPw", null);
        given(userRepository.findByEmail("jihoon@example.com")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("wrongPw", "hashedPw")).willReturn(false);

        LoginRequest request = new LoginRequest("jihoon@example.com", "wrongPw");

        assertThatThrownBy(() -> authService.login(request, mock(HttpServletResponse.class)))
            .isInstanceOf(BusinessException.class)
            .satisfies(e -> assertThat(((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_PASSWORD));
    }

    @Test
    void reissue_RT_없음_예외() {
        jakarta.servlet.http.HttpServletRequest request = mock(jakarta.servlet.http.HttpServletRequest.class);
        given(request.getCookies()).willReturn(null);

        assertThatThrownBy(() -> authService.reissue(request))
            .isInstanceOf(BusinessException.class)
            .satisfies(e -> assertThat(((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_REFRESH_TOKEN));
    }

    @Test
    void reissue_DB에_없는_RT_예외() {
        jakarta.servlet.http.Cookie cookie =
            new jakarta.servlet.http.Cookie("refreshToken", "validToken");
        jakarta.servlet.http.HttpServletRequest request = mock(jakarta.servlet.http.HttpServletRequest.class);
        given(request.getCookies()).willReturn(new jakarta.servlet.http.Cookie[]{cookie});
        given(jwtUtil.validate("validToken")).willReturn(true);
        given(jwtUtil.getTokenType("validToken")).willReturn("REFRESH");
        given(refreshTokenRepository.findByToken("validToken")).willReturn(Optional.empty());

        assertThatThrownBy(() -> authService.reissue(request))
            .isInstanceOf(BusinessException.class)
            .satisfies(e -> assertThat(((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }

    @Test
    void reissue_만료된_RT_예외() {
        jakarta.servlet.http.Cookie cookie =
            new jakarta.servlet.http.Cookie("refreshToken", "expiredToken");
        jakarta.servlet.http.HttpServletRequest request = mock(jakarta.servlet.http.HttpServletRequest.class);
        given(request.getCookies()).willReturn(new jakarta.servlet.http.Cookie[]{cookie});
        given(jwtUtil.validate("expiredToken")).willReturn(true);
        given(jwtUtil.getTokenType("expiredToken")).willReturn("REFRESH");

        RefreshToken stored = RefreshToken.of(1L, "expiredToken", LocalDateTime.now().minusDays(1));
        given(refreshTokenRepository.findByToken("expiredToken")).willReturn(Optional.of(stored));

        assertThatThrownBy(() -> authService.reissue(request))
            .isInstanceOf(BusinessException.class)
            .satisfies(e -> assertThat(((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_REFRESH_TOKEN));
    }
}
