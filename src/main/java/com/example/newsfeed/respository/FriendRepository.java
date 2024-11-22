package com.example.newsfeed.respository;

import com.example.newsfeed.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    //중복 요청 체크
    boolean existsByToUser_UserIdAndFromUser_UserId(Long toUserId, Long fromUserId);
}
