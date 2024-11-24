package com.example.newsfeed.dto.user;

import com.example.newsfeed.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReadUserResponseDto {
    private Long userId;
    private String username;
    private String email;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;


    public ReadUserResponseDto(Long userId, String username, String email, LocalDateTime created_date, LocalDateTime updated_date) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.created_date = created_date;
        this.updated_date = updated_date;

    }

    public ReadUserResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.created_date = user.getCreatedDate();
        this.updated_date = user.getUpdatedDate();


    }

    public static ReadUserResponseDto toUserResponseDto(User user) {
        return new ReadUserResponseDto(user.getUserId(), user.getUsername(), user.getEmail(), user.getCreatedDate(), user.getUpdatedDate());
    }
}
