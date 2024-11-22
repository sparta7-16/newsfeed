package com.example.newsfeed.service;


import com.example.newsfeed.dto.friend.FriendResponseDto;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.respository.FriendRepository;
import com.example.newsfeed.respository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //친구 요청 보내기
    public FriendResponseDto sendFriendRequest(Long toUserId, Long fromUserId) throws BadRequestException {
        // 자기 자신에게 친구 요청 금지
        if (toUserId.equals(fromUserId)) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        // 중복 요청 채크
        if(friendRepository.existsByToUser_UserIdAndFromUser_UserId(toUserId, fromUserId)) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 친구 요청을 보냈습니다.");

        }

        //사용자 확인
        User toUser = userRepository.findById(toUserId).orElseThrow(()-> new  ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 받은 사용자가 존재하지 않습니다"));
        User fromUser = userRepository.findById(fromUserId).orElseThrow(()-> new  ResponseStatusException(HttpStatus.BAD_REQUEST, "요청을 보낸 사용자가 존재하지 않습니다"));

        //friend 엔티티 생성
        Friend friend = new Friend();
        friend.setToUser(toUser);
        friend.setFromUser(fromUser);
        friend.setAreWeFriend(false); //초기 상태: 친구 아님
        friendRepository.save(friend);

        return new FriendResponseDto(" 요청 완료 되었습니다.");
    }

    //친구 요청 목록 확인
    public List<FriendResponseDto> getFriendsRequests(Long userId) {
        //로그인 사용자가 받은 친구 요청 목록 조회
        List<Friend> requests = friendRepository.findByToUser_UserIdAndAreWeFriend(userId, false);

        return requests.stream()
                .map(request -> new FriendResponseDto(
                        "요청 보낸 사용자: " + request.getFromUser().getUsername()))
                .collect(Collectors.toList());
    }

    //친구 요청 수락
    public FriendResponseDto acceptFriendRequest(Long friendId) throws BadRequestException {
        //요청 확인
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(()-> new  ResponseStatusException(HttpStatus.BAD_REQUEST, "친구 요청을 찾을 수 없습니다"));


        friend.setAreWeFriend(true);
        friendRepository.save(friend);

        return new FriendResponseDto(friend.getFromUser().getUsername() + "와 친구가 되었습니다");
    }

    //친구 요청 거절
    public FriendResponseDto declineFriendRequest(Long friendId) throws BadRequestException {
        //요청 확인
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(()-> new  ResponseStatusException(HttpStatus.BAD_REQUEST, "친구 요청을 찾을 수 없습니다"));

        //요청 삭지
        friendRepository.delete(friend);
        return new FriendResponseDto(friend.getFromUser().getUsername() + "의 요청을 거절했습니다.");
    }

    //친구 삭제
    public FriendResponseDto deleteFriend(Long friendId) throws BadRequestException {
        Friend friend = friendRepository.findById(friendId).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "친구 관계를 찾을 수 없습니다."));

        friendRepository.delete(friend);
        return new FriendResponseDto("삭제 완료되었습니다.");
    }

}
