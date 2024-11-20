package com.example.newsfeed.service;


import com.example.newsfeed.dto.friend.FriendRequestDto;
import com.example.newsfeed.dto.friend.FriendResponseDto;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.respository.FriendRepository;
import com.example.newsfeed.respository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //친구 요청 보내기
    public FriendResponseDto sendFriendRequest(FriendRequestDto requestDto) throws BadRequestException {
        Long toUserId = requestDto.getToUserId();
        Long fromUserId = requestDto.getFromUserId();

        // 중복 요청 채크
        if(friendRepository.existsByFriendId(toUserId,fromUserId)){
            throw new BadRequestException("이미 친구 요청을 보냈습니다");
        }

        //사용자 확인
        User toUser = userRepository.findById(toUserId).orElseThrow(()->new BadRequestException("요청 받은 사용자가 존재하지 않습니다"));
        User fromUser = userRepository.findById(fromUserId).orElseThrow(()->new BadRequestException("요청을 보낸 사용자가 존재하지 않습니다"));

        //friend 엔티티 생성
        Friend friend = new Friend();
        friend.setToUser(toUser);
        friend.setFromUser(fromUser);
        friend.setAreWeFriend(false); //초기 상태: 친구 아님
        friendRepository.save(friend);

        return new FriendResponseDto(" 요청 완료 되었습니다.");
    }

    //친구 삭제
    public FriendResponseDto deleteFriend(Long friendId) throws BadRequestException {
        Friend friend = friendRepository.findById(friendId).orElseThrow(()->new BadRequestException("친구 관계를 찾을 수 없습니다."));

        friendRepository.delete(friend);
        return new FriendResponseDto("삭제 완료되었습니다.");
    }

}
