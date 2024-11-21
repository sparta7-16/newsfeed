package com.example.newsfeed.dto.user;

import com.example.newsfeed.entity.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ReadUserResponseDto {
    private Long userId;
    private String username;
    private String email;
    private LocalDate created_date;
    private LocalDate updated_date;
    private LocalDateTime leave_date;
    private String userStatus;

    public ReadUserResponseDto(Long userId, String username, String email, LocalDate created_date, LocalDate updated_date,LocalDateTime leave_date,String userStatus) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.leave_date=leave_date;
        this.userStatus=userStatus;
    }
    public ReadUserResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.created_date = user.getCreatedDate();
        this.updated_date = user.getUpdatedDate();
        this.leave_date=user.getLeave_date();
        this.userStatus=user.getUserStatus();

    }

    public static ReadUserResponseDto toUserResponseDto(User user) {
        return new ReadUserResponseDto(user.getUserId(), user.getUsername(), user.getEmail(), user.getCreatedDate(), user.getUpdatedDate(),user.getLeave_date(), user.getUserStatus());
    }
}
