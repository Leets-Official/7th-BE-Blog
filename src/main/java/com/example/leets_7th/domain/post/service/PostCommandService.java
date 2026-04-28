package com.example.leets_7th.domain.post.service;

import com.example.leets_7th.common.exception.GeneralException;
import com.example.leets_7th.common.status.ErrorStatus;
import com.example.leets_7th.domain.post.dto.request.CreatePostRequest;
import com.example.leets_7th.domain.post.dto.request.ReportPostRequest;
import com.example.leets_7th.domain.post.dto.request.UpdatePostRequest;
import com.example.leets_7th.domain.post.dto.response.CreatePostResponse;
import com.example.leets_7th.domain.post.dto.response.UpdatePostResponse;
import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.post.entity.PostLike;
import com.example.leets_7th.domain.post.entity.PostReport;
import com.example.leets_7th.domain.post.repository.PostLikeRepository;
import com.example.leets_7th.domain.post.repository.PostReportRepository;
import com.example.leets_7th.domain.post.repository.PostRepository;
import com.example.leets_7th.domain.post.validator.PostValidator;
import com.example.leets_7th.domain.user.entity.User;
import com.example.leets_7th.domain.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandService {

    private final PostRepository postRepository;
    private final PostValidator postValidator;
    private final UserValidator userValidator;
    private final PostLikeRepository postLikeRepository;
    private final PostReportRepository postReportRepository;

    public CreatePostResponse createPost(Long userId, CreatePostRequest request) {

        User user = userValidator.validateUser(userId);

        Post post = Post.create(
                user,
                request.title(),
                request.content(),
                null,
                request.postVisibility()
        );

        postRepository.save(post);

        return new CreatePostResponse(
                post.getId(),
                null,
                post.getPostVisibility()
        );
    }

    public UpdatePostResponse updatePost(Long userId, Long postId, UpdatePostRequest request) {

        Post post = validatePostOwner(userId, postId);
        post.update(request.title(), request.content());

        return new UpdatePostResponse(
                post.getId(),
                post.getPostVisibility(),
                post.getUpdatedAt()
        );
    }

    public void deletePost(Long userId, Long postId) {

        Post post = validatePostOwner(userId, postId);
        post.delete();
    }

    public void likePost(Long userId, Long postId) {

        UserAndPost userAndPost = validateUserAndPost(userId, postId);

        if (postLikeRepository.existsByUserAndPost(userAndPost.user(), userAndPost.post())) {
            throw new GeneralException(ErrorStatus.ALREADY_LIKED_POST);
        }

        postLikeRepository.save(PostLike.of(userAndPost.user(), userAndPost.post()));
        userAndPost.post().increaseLikeCount();
    }

    public void unlikePost(Long userId, Long postId) {

        UserAndPost userAndPost = validateUserAndPost(userId, postId);

        PostLike postLike = postLikeRepository
                .findByUserAndPost(userAndPost.user(), userAndPost.post())
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_LIKE_NOT_FOUND));

        postLikeRepository.delete(postLike);
        userAndPost.post().decreaseLikeCount();
    }

    public void reportPost(Long userId, Long postId, ReportPostRequest request) {

        UserAndPost userAndPost = validateUserAndPost(userId, postId);

        if (postReportRepository.existsByUserAndPost(userAndPost.user(), userAndPost.post())) {
            throw new GeneralException(ErrorStatus.ALREADY_REPORTED_POST);
        }

        PostReport report = PostReport.of(
                userAndPost.user(),
                userAndPost.post(),
                request.reason(),
                request.content()
        );

        postReportRepository.save(report);

        if (postReportRepository.countByPost(userAndPost.post()) >= 10) {
            userAndPost.post().hide();
        }
    }

    private UserAndPost validateUserAndPost(Long userId, Long postId) {
        User user = userValidator.validateUser(userId);
        Post post = postValidator.validatePost(postId);
        return new UserAndPost(user, post);
    }

    private Post validatePostOwner(Long userId, Long postId) {
        Post post = postValidator.validatePost(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new GeneralException(ErrorStatus.FORBIDDEN);
        }

        return post;
    }

    private record UserAndPost(User user, Post post) {
    }
}
