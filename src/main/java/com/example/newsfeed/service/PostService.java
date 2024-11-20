package com.example.newsfeed.service;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.dto.post.PostUpdateRequestDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.respository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto.getContent(), postRequestDto.getContent());

        Post savePost = postRepository.save(post);
        return PostResponseDto.toDto(savePost);
    }

    @Transactional
    public List<PostResponseDto> findAll() {
        List<Post> post = postRepository.findAll();
        return post
                .stream()
                .map(PostResponseDto :: toDto)
                .toList();
    }

    @Transactional
    public PostResponseDto findById(Long postId) {
        return PostResponseDto.toDto(findPostById(postId));
    }

    @Transactional
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 값입니다"));
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto updateRequestDto) {
        Post post = findPostById(postId);
        post.update(updateRequestDto.getTitle(), updateRequestDto.getContent());
        return PostResponseDto.toDto(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = findPostById(postId);
        postRepository.deleteById(postId);
    }


}
