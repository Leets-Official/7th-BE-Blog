package com.leets.blog.domain.post.repository;

import com.leets.blog.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 삭제되지 않은 게시글만 조회
    List<Post> findAllByIsDeletedFalse();
}
