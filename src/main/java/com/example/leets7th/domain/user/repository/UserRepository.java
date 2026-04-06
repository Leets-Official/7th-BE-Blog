package com.example.leets7th.domain.user.repository;

import com.example.leets7th.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
