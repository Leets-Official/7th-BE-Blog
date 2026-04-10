package com.example.leets_7th.domain.post.repository;

import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"user", "images"})
    Optional<Post> findById(Long postId);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findAllByUserAndDeletedAtIsNull(User user, Pageable pageable);
}
