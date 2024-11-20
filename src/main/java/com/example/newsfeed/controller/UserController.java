package com.example.newsfeed.controller;

import com.example.newsfeed.dto.user.ReadUserResponseDto;
import com.example.newsfeed.dto.user.SignupUserRequestDto;
import com.example.newsfeed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
