package com.example.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserRequestDto {
    @NotBlank(message = "이름을 입력해주세요")
    private String username;

    private String password;
}
