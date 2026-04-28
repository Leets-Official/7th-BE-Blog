package com.example.week2.report.repository;

import com.example.week2.post.entity.Post;
import com.example.week2.report.entity.Report;
import com.example.week2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository
        extends JpaRepository<Report, Long> {

    boolean existsByPostAndUser(Post post, User user);
}
