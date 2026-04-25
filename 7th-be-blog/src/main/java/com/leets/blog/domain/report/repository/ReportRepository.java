package com.leets.blog.domain.report.repository;

import com.leets.blog.domain.post.entity.Post;
import com.leets.blog.domain.report.entity.Report;
import com.leets.blog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByUserAndPost(User user, Post post);
}
