package com.example.newsfeed.respository;

import com.example.newsfeed.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    //중복 요청 체크
    boolean existsByToUser_UserIdAndFromUser_UserId(Long toUserId, Long fromUserId);

    // 특정 사용자가 받은 친구 요청 목록 (아직 수락하지 않은 요청)
    List<Friend> findByToUser_UserIdAndFriendStatus(Long toUserId, Boolean areWeFriend);

    // 특정 사용자가 보낸 친구 요청 목록 (아직 수락하지 않은 요청)
    List<Friend> findByFromUser_UserIdAndFriendStatus(Long fromUserId, Boolean areWeFriend);

    // 친구 관계 체크
    boolean existsByToUser_UserIdAndFromUser_UserIdAndFriendStatus(Long toUserId, Long fromUserId, Boolean areWeFriend);
}

