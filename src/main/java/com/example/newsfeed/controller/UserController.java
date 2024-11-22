package com.example.newsfeed.controller;

import com.example.newsfeed.config.PasswordEncoder;
import com.example.newsfeed.dto.user.*;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Validated @RequestBody SignupUserRequestDto signupUserRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 형식 입니다");
        }
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
    @PatchMapping("/usernames")
    public ResponseEntity<String> updateUser( @Validated @RequestBody UpdateUserRequestDto requestDto, BindingResult bindingResult,HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경하실 이름을 입력해주세요");
        }
        userService.updateUser(requestDto,request);
        return new ResponseEntity<>("수정되었습니다",HttpStatus.OK);
    }
    @PatchMapping("/passwords")
    public ResponseEntity<String> updateUserPassword( @Validated @RequestBody UpdateUserPasswordRequestDto requestDto, BindingResult bindingResult,HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바른 비밀번호를  입력해주세요");
        }

        userService.updateUserPassword(requestDto,request);
        return new ResponseEntity<>("수정되었습니다",HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser( @RequestBody DeleteRequestDto requestDto, HttpServletRequest request) {
        userService.deleteUser(requestDto,request);
        return new ResponseEntity<>("탈퇴완료하였습니다",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User loginedUser = userService.loginUser(loginRequestDto);

        // 로그인 성공했으니까 Session 등록
        HttpSession session = request.getSession();
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
