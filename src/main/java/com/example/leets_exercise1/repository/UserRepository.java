package com.example.leets_exercise1.repository;

import com.example.leets_exercise1.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}