package com.example.newsfeed.service;

import com.example.newsfeed.dto.user.ReadUserResponseDto;
import com.example.newsfeed.dto.user.SignupUserRequestDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public String signup(SignupUserRequestDto signupUserRequestDto) {
        User user = new User(signupUserRequestDto);
        User savedUser = userRepository.save(user);
        return "가입완료하였습니다";
    }

    public List<ReadUserResponseDto> findAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(ReadUserResponseDto::toUserResponseDto).toList();
    }

    public ReadUserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id).get();
        return new ReadUserResponseDto(user);
    }
}
