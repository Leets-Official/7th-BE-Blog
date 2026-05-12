package com.example.week2.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @Email
    private String email;

    @NotBlank
    private String password;

}