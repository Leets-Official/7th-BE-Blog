package com.leets.blog.domain.post.service;

import com.leets.blog.common.exception.BaseErrorCode;
import com.leets.blog.common.exception.GeneralException;
import com.leets.blog.domain.post.converter.PostConverter;
import com.leets.blog.domain.post.dto.CreatePostRequest;
import com.leets.blog.domain.post.dto.PostResponse;
import com.leets.blog.domain.post.dto.UpdatePostRequest;
import com.leets.blog.domain.post.entity.Post;
import com.leets.blog.domain.post.repository.PostRepository;
import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        User user = getUser(request.userId());
        Post post = Post.create(request.title(), request.content(), user);
        return PostConverter.toPostResponse(postRepository.save(post));
    }

    public List<PostResponse> getPostList() {
        return postRepository.findAllByIsDeletedFalse().stream()
                .map(PostConverter::toPostResponse)
                .toList();
    }

    public PostResponse getPostDetail(Long postId) {
        return PostConverter.toPostResponse(getActivePost(postId));
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = getActivePost(postId);
        post.update(request.title(), request.content());
        return PostConverter.toPostResponse(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = getActivePost(postId);
        post.softDelete();
    }

    private Post getActivePost(Long postId) {
        return postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(() -> new GeneralException(BaseErrorCode.POST_NOT_FOUND));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new GeneralException(BaseErrorCode.USER_NOT_FOUND));
    }
}
