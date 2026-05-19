    package com.example.leets_project.domain.user.web.controller;

    import com.example.leets_project.common.response.GlobalResponse;
    import com.example.leets_project.common.response.SuccessCode;
    import com.example.leets_project.domain.user.service.UserService;
    import com.example.leets_project.domain.user.web.dto.UserCreateRequest;
    import com.example.leets_project.domain.user.web.dto.UserCreateResponse;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @Tag(name ="USER API", description = "유저 생성 및 관리 API")
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/users")
    public class UserController {

        private final UserService userService;

        @Operation(summary = "유저 등록", description = "새로운 유저를 등록합니다.")
        @PostMapping
        public ResponseEntity<GlobalResponse> createUser(@RequestBody @Valid UserCreateRequest request) {

            UserCreateResponse response = userService.createUser(request);

            return GlobalResponse.onSuccess(SuccessCode.USER_CREATE, response);
        }
    }
