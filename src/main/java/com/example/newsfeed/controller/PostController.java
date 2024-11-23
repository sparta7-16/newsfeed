package com.example.newsfeed.controller;

import com.example.newsfeed.dto.post.PostPageableResponseDto;
import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.dto.post.PostUpdateRequestDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> pageList(@PageableDefault(page = 1)Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.getPostList(pageable));
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