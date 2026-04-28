package com.example.leets_7th.domain.post.repository;

import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.post.entity.PostLike;
import com.example.leets_7th.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // 중복 좋아요 체크
    boolean existsByUserAndPost(User user, Post post);

    // 좋아요 취소 시 조회
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
