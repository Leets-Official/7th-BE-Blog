package com.example.leets_project.domain.user.repository;

import com.example.leets_project.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    // 이메일 중복 확인
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    // 닉네임 중복 확인
    boolean existsByNickname(String nickname);
}
