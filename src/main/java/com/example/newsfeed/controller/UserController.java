package com.example.newsfeed.controller;

public class UserController {

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        User loginedUser = userService.loginUser(loginRequestDto);

        return new ResponseEntity<>(HttpStatus.OK), "로그인 되었습니다.";
    }
}
