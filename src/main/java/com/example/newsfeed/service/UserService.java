package com.example.newsfeed.service;

import com.example.newsfeed.config.PasswordEncoder;
import com.example.newsfeed.dto.user.*;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.respository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signup(SignupUserRequestDto signupUserRequestDto) {
        User user = new User(signupUserRequestDto,passwordEncoder.encode(signupUserRequestDto.getPassword()));
        if(userRepository.existsByEmail(signupUserRequestDto.getEmail())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "중복된 사용자 입니다");
        }
        userRepository.save(user);
        return "가입완료하였습니다";
    }


    public List<ReadUserResponseDto> findAllUser() {
        List<User> users = userRepository.findAllByUserStatus("Y");
        return users.stream().map(ReadUserResponseDto::toUserResponseDto).toList();
    }

    public ReadUserResponseDto findUserById(Long id) {
        User user = userRepository.findByUserIdAndUserStatus(id, "Y");
        return new ReadUserResponseDto(user);
    }

    public User loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());

        if (user == null || !Objects.equals(user.getPassword(), loginRequestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자 이메일 혹은 잘못된 비밀번호입니다");
        }
        return user;
    }

    @Transactional
    public void updateUser(Long id, UpdateUserRequestDto requestDto, HttpServletRequest request) {

        User user = userRepository.findByIdOrElseThrow(id);
        if (user.getUserId() == null || !passwordEncoder.matches(requestDto.getPassword(),user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 잘못된 비밀번호입니다");

        }
        HttpSession session = request.getSession(false);
        session.setAttribute("loginUser", user.getUserId());
        user.updateUser(requestDto.getUsername());
        userRepository.save(user);

    }


    public void deleteUser(Long id, DeleteRequestDto requestDto, HttpServletRequest request) {
        User user = userRepository.findByIdOrElseThrow(id);
        if (user.getUserId() == null || !passwordEncoder.matches( requestDto.getPassword(),user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 잘못된 비밀번호입니다");

        }
        HttpSession session = request.getSession(false);
        session.setAttribute("loginUser", user.getUserId());
        user.setUserStatus("N");
        user.setLeave_date(LocalDateTime.now());
        userRepository.save(user);
    }

}
