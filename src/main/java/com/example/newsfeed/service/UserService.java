package com.example.newsfeed.service;

import com.example.newsfeed.dto.user.ReadUserResponseDto;
import com.example.newsfeed.dto.user.SignupUserRequestDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.newsfeed.dto.user.LoginRequestDto;
import org.springframework.http.HttpStatus;
import java.util.Objects;
import org.springframework.web.server.ResponseStatusException;

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

    public User loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());

        if (user == null || !Objects.equals(user.getPassword(), loginRequestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자 이름 혹은 잘못된 비밀번호입니다.");
        }
        return user;
    }
}
