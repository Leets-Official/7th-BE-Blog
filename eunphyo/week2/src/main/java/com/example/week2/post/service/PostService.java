package com.example.week2.post.service;

import com.example.week2.global.exception.PostNotFoundException;
import com.example.week2.post.dto.PostCreateRequest;
import com.example.week2.post.dto.PostResponse;
import com.example.week2.post.entity.Post;
import com.example.week2.post.repository.PostRepository;
import com.example.week2.user.entity.User;
import com.example.week2.user.exception.UserNotFoundException;
import com.example.week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.example.week2.post.dto.PostUpdateRequest;
import com.example.week2.global.exception.ForbiddenPostAccessException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse createPost(Long userId,PostCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenPostAccessException();
        }

        post.update(request.getTitle(), request.getContent());

        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenPostAccessException();
        }

        postRepository.delete(post);
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.from(post);
    }

    public List<PostResponse> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::from)
                .toList();
    }
}