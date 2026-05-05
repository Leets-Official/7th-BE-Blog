package com.example.leets_7th.domain.post.repository;

import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.post.entity.PostReport;
import com.example.leets_7th.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

    // 중복 신고 방지
    boolean existsByUserAndPost(User user, Post post);

    int countByPost(Post post);
}
