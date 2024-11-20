package com.example.newsfeed.entity;

import com.example.newsfeed.dto.user.SignupUserRequestDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @Column(name = "user_name")
    private String username; // 유저 이름
    private String email; //이메일
    private String password; //패스워드
    public User(SignupUserRequestDto signupUserRequestDto) {
        this.username=signupUserRequestDto.getUsername();
        this.email=signupUserRequestDto.getEmail();
        this.password=signupUserRequestDto.getPassword();
    }
}
