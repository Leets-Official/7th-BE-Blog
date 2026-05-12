package com.leets.blog.domain.auth.service;

import com.leets.blog.common.exception.BaseErrorCode;
import com.leets.blog.common.exception.GeneralException;
import com.leets.blog.domain.auth.dto.SignupRequest;
import com.leets.blog.domain.auth.dto.SignupResponse;
import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.user.repository.UserRepository;
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

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        validateSignupRequest(request);

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .build();

        return SignupResponse.from(userRepository.save(user));
    }

    private void validateSignupRequest(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new GeneralException(BaseErrorCode.DUPLICATE_EMAIL);
        }
        if (userRepository.existsByName(request.name())) {
            throw new GeneralException(BaseErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
