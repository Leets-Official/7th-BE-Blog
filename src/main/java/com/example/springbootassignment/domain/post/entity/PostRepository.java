package com.example.springbootassignment.domain.post.entity;

import com.example.springbootassignment.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
