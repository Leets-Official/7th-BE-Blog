package com.example.leets_7th.domain.post.validator;

import com.example.leets_7th.common.exception.GeneralException;
import com.example.leets_7th.common.status.ErrorStatus;
import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.post.repository.PostRepository;
import com.example.leets_7th.domain.user.entity.User;
import com.example.leets_7th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    public Post validatePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        if (post.getDeletedAt() != null) {
            throw new GeneralException(ErrorStatus.POST_ALREADY_DELETED);
        }

        return post;
    }
}
