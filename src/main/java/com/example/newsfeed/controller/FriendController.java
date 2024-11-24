package com.example.newsfeed.controller;

import com.example.newsfeed.dto.friend.FriendRequestDto;
import com.example.newsfeed.dto.friend.FriendResponseDto;
import com.example.newsfeed.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    //친구 요청 보내기
    @PostMapping
    public ResponseEntity<FriendResponseDto> sendFriendRequest(@RequestBody FriendRequestDto requestDto,
                                                               HttpServletRequest request){
        //세션에서 사용자 ID가져오기
        Long fromUserId = (Long) request.getSession().getAttribute("SESSION_KEY");

        FriendResponseDto response = friendService.sendFriendRequest(requestDto.getToUserId(), fromUserId);
        return ResponseEntity.ok(response);
    }

    //친구 요청 목록 확인
    @GetMapping("/requests")
    public ResponseEntity<List<FriendResponseDto>> getFriendRequests(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("SESSION_KEY");
        List<FriendResponseDto> friendRequests = friendService.getFriendsRequests(userId);
        return ResponseEntity.ok(friendRequests);
    }

    //친구 요청 수락
    @PostMapping("/{friendId}/accept")
    public ResponseEntity<FriendResponseDto> acceptFriend(@PathVariable Long friendId, HttpServletRequest request)  {
        Long currentUserId = (Long) request.getSession().getAttribute("SESSION_KEY");
        FriendResponseDto response = friendService.acceptFriendRequest(friendId, currentUserId);
        return ResponseEntity.ok(response);
    }

    //친구 요청 거절
    @DeleteMapping("/{friendId}/decline")
    public ResponseEntity<FriendResponseDto> declineFriend(@PathVariable Long friendId, HttpServletRequest request) {
        Long currentUserId = (Long) request.getSession().getAttribute("SESSION_KEY");
        FriendResponseDto response = friendService.declineFriendRequest(friendId, currentUserId);
        return ResponseEntity.ok(response);
    }


    //친구 삭제
    @DeleteMapping("/{friendId}")
    public ResponseEntity<FriendResponseDto> deleteFriend(@PathVariable Long friendId, HttpServletRequest request) {
        Long currentUserId = (Long) request.getSession().getAttribute("SESSION_KEY");
        FriendResponseDto response = friendService.deleteFriend(friendId, currentUserId);
        return ResponseEntity.ok(response);
    }


}