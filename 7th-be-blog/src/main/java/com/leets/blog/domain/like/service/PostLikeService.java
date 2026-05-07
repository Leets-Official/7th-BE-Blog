package com.leets.blog.domain.like.service;

import com.leets.blog.common.exception.BaseErrorCode;
import com.leets.blog.common.exception.GeneralException;
import com.leets.blog.domain.like.dto.CreatePostLikeRequest;
import com.leets.blog.domain.like.dto.PostLikeResponse;
import com.leets.blog.domain.like.entity.PostLike;
import com.leets.blog.domain.like.repository.PostLikeRepository;
import com.leets.blog.domain.post.entity.Post;
import com.leets.blog.domain.post.repository.PostRepository;
import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostLikeResponse createPostLike(Long postId, CreatePostLikeRequest request) {
        User user = getUser(request.userId());
        Post post = getPost(postId);

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new GeneralException(BaseErrorCode.DUPLICATE_POST_LIKE);
        }

        PostLike postLike = PostLike.create(user, post);
        PostLike savedPostLike = postLikeRepository.save(postLike);

        return PostLikeResponse.builder()
                .likeId(savedPostLike.getId())
                .postId(savedPostLike.getPost().getId())
                .userId(savedPostLike.getUser().getId())
                .createdAt(savedPostLike.getCreatedAt())
                .build();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new GeneralException(BaseErrorCode.USER_NOT_FOUND));
    }

    private Post getPost(Long postId) {
        return postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(() -> new GeneralException(BaseErrorCode.POST_NOT_FOUND));
    }
}
