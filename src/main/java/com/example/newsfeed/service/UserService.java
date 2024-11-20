package com.example.newsfeed.service;

import com.example.newsfeed.dto.user.SignupUserRequestDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public String signup(SignupUserRequestDto signupUserRequestDto) {
        User user = new User(signupUserRequestDto);
        User savedUser = userRepository.save(user);
        return "가입완료하였습니다";
    }
}
