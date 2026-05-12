package com.example.leets_exercise1.repository;

import com.example.leets_exercise1.domain.auth.RefreshToken;
import com.example.leets_exercise1.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Optional<RefreshToken> findByUser(User user);
}
