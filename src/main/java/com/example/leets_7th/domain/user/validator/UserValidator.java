package com.example.leets_7th.domain.user.validator;

import com.example.leets_7th.common.exception.GeneralException;
import com.example.leets_7th.common.status.ErrorStatus;
import com.example.leets_7th.domain.user.entity.User;
import com.example.leets_7th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class UserValidator {

    private final UserRepository userRepository;

    public User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }
}
