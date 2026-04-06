package com.example.leets_7th.domain.post.service;

import com.example.leets_7th.common.exception.GeneralException;
import com.example.leets_7th.common.status.ErrorStatus;
import com.example.leets_7th.domain.post.dto.request.CreatePostRequest;
import com.example.leets_7th.domain.post.dto.request.UpdatePostRequest;
import com.example.leets_7th.domain.post.dto.response.CreatePostResponse;
import com.example.leets_7th.domain.post.dto.response.UpdatePostResponse;
import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.post.repository.PostRepository;
import com.example.leets_7th.domain.user.entity.User;
import com.example.leets_7th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CreatePostResponse createPost(Long userId, CreatePostRequest request) {

        User user = getUser(userId);

        Post post = Post.builder()
                .user(user)
                .title(request.title())
                .content(request.content())
                .thumbnailImageUrl(null)
                .build();

        postRepository.save(post);

        return new CreatePostResponse(post.getId(), null);
    }

    public UpdatePostResponse updatePost(Long userId, Long postId, UpdatePostRequest request) {

        Post post = getPost(postId);

        validateOwner(post, userId);
        validateNotDeleted(post);

        post.update(request.title(), request.content());

        return new UpdatePostResponse(
                post.getId(),
                post.getUpdatedAt()
        );
    }

    public void deletePost(Long userId, Long postId) {

        Post post = getPost(postId);

        validateOwner(post, userId);
        validateNotDeleted(post);

        post.delete();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    }

    private void validateOwner(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new GeneralException(ErrorStatus.FORBIDDEN);
        }
    }

    private void validateNotDeleted(Post post) {
        if (post.getDeletedAt() != null) {
            throw new GeneralException(ErrorStatus.POST_ALREADY_DELETED);
        }
    }
}
