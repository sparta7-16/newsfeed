package com.example.newsfeed.service;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.dto.post.PostUpdateRequestDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.respository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

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
    public Page<Post> getPostList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createdDate")));
        return postRepository.findAll(pageable);
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
        if (updateRequestDto.getTitle() != null && updateRequestDto.getContent() != null) {
            post.update(updateRequestDto.getTitle(), updateRequestDto.getContent());
        } else if (updateRequestDto.getTitle() != null && updateRequestDto.getContent() == null) {
            post.updateTitle(updateRequestDto.getTitle());
        } else if (updateRequestDto.getTitle() == null && updateRequestDto.getContent() != null) {
            post.updateContent(updateRequestDto.getContent());
        }
        return PostResponseDto.toDto(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = findPostById(postId);
        postRepository.deleteById(postId);
    }


}
