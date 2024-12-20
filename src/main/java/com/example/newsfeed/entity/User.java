package com.example.newsfeed.entity;

import com.example.newsfeed.dto.user.SignupUserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name")
    private String username; // 유저 이름
    private String email; //이메일
    private String password; //패스워드
    @Setter
    @Column(name = "leave_date")
    private LocalDateTime leaveDate; // 탈퇴날짜
    @Column(name = "user_status")
    @Setter
    private String userStatus = "Y"; // 회원상태
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "toUser")
    private List<Friend> sendFriends = new ArrayList<>();
    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    private List<Friend> receiveFriends = new ArrayList<>();

    public User(SignupUserRequestDto signupUserRequestDto, String password) {
        this.username = signupUserRequestDto.getUsername();
        this.email = signupUserRequestDto.getEmail();
        this.password = password;
    }


    public void updateUser(String username) {

        this.username = username;

    }

    public void updateUserPassword(String password) {


        this.password = password;

    }
}
