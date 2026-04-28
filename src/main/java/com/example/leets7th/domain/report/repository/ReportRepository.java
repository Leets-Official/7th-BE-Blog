package com.example.leets7th.domain.report.repository;

import com.example.leets7th.domain.post.entity.Post;
import com.example.leets7th.domain.report.entity.Report;
import com.example.leets7th.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByUserAndPost(User user, Post post);
}
