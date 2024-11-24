package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Friend {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    // 친구 요청을 받은 사용자 (Foreign Key)
    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    // 친구 요청을 한 사용자 (Foreign Key)
    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    // 친구 관계 여부
    @Column(nullable = false)
    private boolean friendStatus;

    public boolean getFriendStatus() {
        return friendStatus;
    }


}
