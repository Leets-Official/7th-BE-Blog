package com.example.leets_exercise1.repository;

import com.example.leets_exercise1.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}