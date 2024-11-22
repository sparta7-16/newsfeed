package com.example.newsfeed.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserPasswordRequestDto {
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "올바른 비밀번호 형식이 맞지 않습니다")
    private String password;
}
