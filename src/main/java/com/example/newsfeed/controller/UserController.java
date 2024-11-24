package com.example.newsfeed.controller;

import com.example.newsfeed.dto.user.*;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupUserResponseDto> signup(@Validated @RequestBody SignupUserRequestDto signupUserRequestDto, BindingResult bindingResult, HttpServletRequest request) {

        HttpSession session = request.getSession();

        // 로그인 상태 확인
        if (session.getAttribute("SESSION_KEY") != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 로그인 된 상태입니다");
        }
        SignupUserResponseDto signupUser = userService.signup(signupUserRequestDto, bindingResult);
        return new ResponseEntity<>(signupUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReadUserResponseDto>> findAll() {
        List<ReadUserResponseDto> responseDtos = userService.findAllUser();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadUserResponseDto> findUserById(@PathVariable Long id) {
        ReadUserResponseDto userById = userService.findUserById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PatchMapping("/usernames")
    public ResponseEntity<String> updateUser(@Validated @RequestBody UpdateUserRequestDto requestDto, BindingResult bindingResult, HttpServletRequest request) {

        userService.updateUser(requestDto, bindingResult, request);
        return new ResponseEntity<>("수정되었습니다", HttpStatus.OK);
    }

    @PatchMapping("/passwords")
    public ResponseEntity<String> updateUserPassword(@Validated @RequestBody UpdateUserPasswordRequestDto requestDto, BindingResult bindingResult, HttpServletRequest request) {

        userService.updateUserPassword(requestDto, bindingResult, request);
        return new ResponseEntity<>("수정되었습니다", HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        userService.deleteUser(requestDto, userId);
        session.invalidate();
        return new ResponseEntity<>("탈퇴완료하였습니다", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User loginedUser = userService.loginUser(loginRequestDto);

        // 로그인 성공했으니까 Session 생성
        HttpSession session = request.getSession();

        // 로그인 상태 확인
        if (session.getAttribute("SESSION_KEY") != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 로그인 된 상태입니다");
        }

        // Session 등록
        session.setAttribute("SESSION_KEY", loginedUser.getUserId());
        return ResponseEntity.ok().body("로그인되었습니다");
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 세션 존재하면 무효화 처리
        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
    }
}