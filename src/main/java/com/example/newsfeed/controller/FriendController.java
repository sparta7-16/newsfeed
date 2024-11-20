package com.example.newsfeed.controller;

import com.example.newsfeed.dto.friend.FriendRequestDto;
import com.example.newsfeed.dto.friend.FriendResponseDto;
import com.example.newsfeed.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    //친구 요청 보내기
    @PostMapping
    public ResponseEntity<FriendResponseDto> sendFriendRequest(@RequestBody FriendRequestDto requestDto) throws BadRequestException {
        FriendResponseDto response = friendService.sendFriendRequest(requestDto);
        return ResponseEntity.ok(response);
    }

    //친구 삭제
    @DeleteMapping("/{friendId}")
    public ResponseEntity<FriendResponseDto> deleteFriend(@PathVariable Long friendId) throws BadRequestException {
        FriendResponseDto response = friendService.deleteFriend(friendId);
        return ResponseEntity.ok(response);
    }


}
