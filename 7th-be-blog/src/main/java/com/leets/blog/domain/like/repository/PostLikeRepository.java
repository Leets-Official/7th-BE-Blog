package com.leets.blog.domain.like.repository;

import com.leets.blog.domain.like.entity.PostLike;
import com.leets.blog.domain.post.entity.Post;
import com.leets.blog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserAndPost(User user, Post post);
}
