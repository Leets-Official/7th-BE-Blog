package com.example.leets_7th.domain.post.repository;

import com.example.leets_7th.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
