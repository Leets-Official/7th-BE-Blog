package com.example.leets_exercise1.repository;

import com.example.leets_exercise1.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByDeletedAtIsNullAndActiveTrue(Pageable pageable);

    Optional<Post> findByIdAndDeletedAtIsNullAndActiveTrue(Long postId);
}