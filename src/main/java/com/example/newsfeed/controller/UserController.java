package com.example.newsfeed.controller;

import com.example.newsfeed.dto.user.ReadUserResponseDto;
import com.example.newsfeed.dto.user.LoginRequestDto;
import com.example.newsfeed.dto.user.SignupUserRequestDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping()
    public ResponseEntity<String> signup(@Validated @RequestBody SignupUserRequestDto signupUserRequestDto) {
        String msg = userService.signup(signupUserRequestDto);
        return new ResponseEntity<>(msg,HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ReadUserResponseDto>> findAll(){
        List<ReadUserResponseDto> responseDtos = userService.findAllUser();
        return new ResponseEntity<>(responseDtos,HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<ReadUserResponseDto> findUserById(@PathVariable Long id) {
        ReadUserResponseDto userById = userService.findUserById(id);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User loginedUser = userService.loginUser(loginRequestDto);

        // 로그인 성공했으니까 Session 등록
        HttpSession session = request.getSession();
        session.setAttribute("SESSION_KEY", loginedUser.getUserId());

        return new ResponseEntity<>("로그인 되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 세션 존재하면 무효화 처리
        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
    }
}
