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
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupUserResponseDto signup(SignupUserRequestDto signupUserRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 형식 입니다");
        }
        User user = new User(signupUserRequestDto,passwordEncoder.encode(signupUserRequestDto.getPassword()));
        if(userRepository.existsByEmail(signupUserRequestDto.getEmail())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "중복된 사용자 입니다");
        }
        User savedUser = userRepository.save(user);
        return new SignupUserResponseDto(savedUser);
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

        if (user == null || !passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자 이메일 혹은 잘못된 비밀번호입니다");
        }

        return user;
    }

    @Transactional
    public void updateUser(UpdateUserRequestDto requestDto,BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경하실 이름을 입력해주세요");
        }
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        User user = userRepository.findByIdOrElseThrow(userId);

        if (user.getUserStatus().equals("N")||!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 정보 입니다");

        }

        user.updateUser(requestDto.getUsername());
        userRepository.save(user);

    }
    @Transactional
    public void updateUserPassword(UpdateUserPasswordRequestDto requestDto, BindingResult bindingResult,HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바른 비밀번호를  입력해주세요");
        }
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        User user = userRepository.findByIdOrElseThrow(userId);
        if (user.getUserStatus().equals("N") || passwordEncoder.matches( requestDto.getPassword(),user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "중복된 비밀번호 입니다");

        }
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        user.updateUserPassword(encodePassword);
        userRepository.save(user);

    }

    @Transactional
    public void deleteUser(DeleteRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        User user = userRepository.findByIdOrElseThrow(userId);
        if (user.getUserStatus().equals("N") || !passwordEncoder.matches( requestDto.getPassword(),user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 정보 입니다");

        }

        user.setUserStatus("N");
        user.setLeaveDate(LocalDateTime.now());
        userRepository.save(user);
    }


}
