package com.example.leets7th.domain.post.service;

import com.example.leets7th.domain.category.entity.Category;
import com.example.leets7th.domain.category.repository.CategoryRepository;
import com.example.leets7th.domain.post.dto.request.PostCreateRequest;
import com.example.leets7th.domain.post.dto.request.PostUpdateRequest;
import com.example.leets7th.domain.post.dto.response.PostDetailResponse;
import com.example.leets7th.domain.post.dto.response.PostListResponse;
import com.example.leets7th.domain.post.entity.Post;
import com.example.leets7th.domain.post.entity.PostStatus;
import com.example.leets7th.domain.post.repository.PostRepository;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import com.example.leets7th.global.exception.AlreadyHiddenException;
import com.example.leets7th.global.exception.CategoryNotFoundException;
import com.example.leets7th.global.exception.ForbiddenException;
import com.example.leets7th.global.exception.PostNotFoundException;
import com.example.leets7th.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostListResponse getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postPage = postRepository.findAll(pageable);
        return PostListResponse.from(postPage);
    }

    public PostDetailResponse getPost(Long postId) {
        return PostDetailResponse.from(findPostById(postId));
    }

    @Transactional
    public Long createPost(PostCreateRequest request, Long userId) {
        User user = findUserById(userId);
        Category category = findCategoryById(request.categoryId());
        Post post = Post.create(request.title(), request.content(), null, user, category);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequest request, Long userId) {
        Post post = findPostById(postId);
        validateAuthor(post, userId, "작성자만 수정할 수 있습니다.");
        post.update(request.title(), request.content());
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = findPostById(postId);
        validateAuthor(post, userId, "삭제 권한이 없습니다.");
        postRepository.delete(post);
    }

    @Transactional
    public void hidePost(Long postId, Long userId) {
        Post post = findPostById(postId);
        validateAuthor(post, userId, "숨김 권한이 없습니다.");

        if (post.getStatus() == PostStatus.HIDDEN) {
            throw new AlreadyHiddenException();
        }

        post.hide();
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    private void validateAuthor(Post post, Long userId, String message) {
        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException(message);
        }
    }
}
