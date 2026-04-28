package com.example.leets7th.global.init;

import com.example.leets7th.domain.category.entity.Category;
import com.example.leets7th.domain.category.repository.CategoryRepository;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() == 0) {
            userRepository.save(User.create("Unknown"));
        }

        if (categoryRepository.count() == 0) {
            categoryRepository.saveAll(List.of(
                    Category.create("자유게시판"),
                    Category.create("공지사항"),
                    Category.create("질문게시판")
            ));
        }
    }
}
