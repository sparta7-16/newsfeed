package com.example.newsfeed.controller;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.dto.post.PostUpdateRequestDto;
import com.example.newsfeed.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequestDto, request));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAllPost(@PageableDefault(page = 1) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.findAllPost(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findPostByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.findPostByPostId(postId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody PostUpdateRequestDto updateRequestDto, @PathVariable Long postId, HttpServletRequest request) {
        return ResponseEntity.ok().body(postService.updatePost(postId, updateRequestDto, request));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        postService.deletePost(postId, request);
        return ResponseEntity.ok().body("삭제되었습니다");
    }
}