package com.example.newsfeed.controller;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.dto.post.PostUpdateRequestDto;
import com.example.newsfeed.respository.PostRepository;
import com.example.newsfeed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll(){
        return ResponseEntity.ok().body(postService.findAll());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.findById(postId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody PostUpdateRequestDto updateRequestDto,
                                                      @PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.updatePost(postId, updateRequestDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().body("삭제되었습니다");
    }
}