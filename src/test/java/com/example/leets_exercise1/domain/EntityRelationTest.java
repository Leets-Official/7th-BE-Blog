package com.example.leets_exercise1.domain;

import com.example.leets_exercise1.domain.comment.Comment;
import com.example.leets_exercise1.domain.post.Post;
import com.example.leets_exercise1.domain.user.User;
import com.example.leets_exercise1.repository.CommentRepository;
import com.example.leets_exercise1.repository.PostRepository;
import com.example.leets_exercise1.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EntityRelationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("User - Post - Comment 연관관계 저장 테스트")
    void saveEntityRelationTest() {
        User user = User.builder()
                .age(24)
                .name("조연준")
                .email("test@test.com")
                .nickname("yeonjun")
                .build();

        User savedUser = userRepository.save(user);

        Post post = Post.builder()
                .user(savedUser)
                .title("첫 게시글")
                .description("게시글 설명")
                .active(true)
                .build();

        Post savedPost = postRepository.save(post);

        Comment comment = Comment.builder()
                .user(savedUser)
                .post(savedPost)
                .content("첫 댓글")
                .active(true)
                .build();

        Comment savedComment = commentRepository.save(comment);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedComment.getId()).isNotNull();

        assertThat(savedPost.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(savedComment.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(savedComment.getPost().getId()).isEqualTo(savedPost.getId());
    }
    @Test
    @DisplayName("Comment 자기참조(대댓글) 저장 테스트")
    void commentReplyTest() {
        User user = userRepository.save(
                User.builder()
                        .age(24)
                        .name("조연준")
                        .email("reply@test.com")
                        .nickname("replyUser")
                        .build()
        );

        Post post = postRepository.save(
                Post.builder()
                        .user(user)
                        .title("게시글")
                        .description("설명")
                        .active(true)
                        .build()
        );

        Comment parent = commentRepository.save(
                Comment.builder()
                        .user(user)
                        .post(post)
                        .content("부모 댓글")
                        .active(true)
                        .build()
        );

        Comment child = commentRepository.save(
                Comment.builder()
                        .user(user)
                        .post(post)
                        .parent(parent)
                        .content("대댓글")
                        .active(true)
                        .build()
        );

        assertThat(child.getParent().getId()).isEqualTo(parent.getId());
    }
}