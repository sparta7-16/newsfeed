package com.example.newsfeed.dto.user;

import com.example.newsfeed.entity.User;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignupUserResponseDto {

    private Long userId;
    private String username; // 유저 이름
    private String email; //이메일
    private LocalDateTime createdDate;

    public SignupUserResponseDto(User user){
        this.userId=user.getUserId();
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.createdDate=user.getCreatedDate();
    }
}
