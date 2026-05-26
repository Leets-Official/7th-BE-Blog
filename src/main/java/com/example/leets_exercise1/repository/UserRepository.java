package com.example.leets_exercise1.repository;

import com.example.leets_exercise1.domain.user.User;
import com.example.leets_exercise1.domain.user.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
