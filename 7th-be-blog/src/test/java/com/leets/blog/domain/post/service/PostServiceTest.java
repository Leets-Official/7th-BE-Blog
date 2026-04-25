package com.leets.blog.domain.post.service;

import com.leets.blog.common.exception.BaseErrorCode;
import com.leets.blog.common.exception.GeneralException;
import com.leets.blog.domain.post.dto.CreatePostRequest;
import com.leets.blog.domain.post.dto.PostResponse;
import com.leets.blog.domain.post.dto.UpdatePostRequest;
import com.leets.blog.domain.post.entity.Post;
import com.leets.blog.domain.post.repository.PostRepository;
import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("tester")
                .email("tester@example.com")
                .password("password")
                .build();
        ReflectionTestUtils.setField(user, "id", 1L);

        post = Post.create("title", "content", user);
        ReflectionTestUtils.setField(post, "id", 10L);
        ReflectionTestUtils.setField(post, "createdAt", LocalDateTime.of(2026, 4, 25, 12, 0));
    }

    @Test
    void createPost_createsAndReturnsResponse() {
        CreatePostRequest request = new CreatePostRequest("new title", "new content", 1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            ReflectionTestUtils.setField(savedPost, "id", 11L);
            ReflectionTestUtils.setField(savedPost, "createdAt", LocalDateTime.of(2026, 4, 25, 12, 30));
            return savedPost;
        });

        PostResponse response = postService.createPost(request);

        assertThat(response.getId()).isEqualTo(11L);
        assertThat(response.getTitle()).isEqualTo("new title");
        assertThat(response.getContent()).isEqualTo("new content");
        assertThat(response.getAuthorName()).isEqualTo("tester");
    }

    @Test
    void updatePost_updatesOnlyProvidedFields() {
        when(postRepository.findByIdAndIsDeletedFalse(10L)).thenReturn(Optional.of(post));

        PostResponse response = postService.updatePost(10L, new UpdatePostRequest("updated", null));

        assertThat(response.getTitle()).isEqualTo("updated");
        assertThat(response.getContent()).isEqualTo("content");
    }

    @Test
    void deletePost_softDeletesPost() {
        when(postRepository.findByIdAndIsDeletedFalse(10L)).thenReturn(Optional.of(post));

        postService.deletePost(10L);

        assertThat(post.isDeleted()).isTrue();
    }

    @Test
    void getPostDetail_throwsWhenPostMissing() {
        when(postRepository.findByIdAndIsDeletedFalse(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.getPostDetail(99L))
                .isInstanceOf(GeneralException.class)
                .extracting("errorCode")
                .isEqualTo(BaseErrorCode.POST_NOT_FOUND);
    }
}
