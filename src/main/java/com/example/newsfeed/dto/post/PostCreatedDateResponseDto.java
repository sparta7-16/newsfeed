package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCreatedDateResponseDto {

    private final Long userId;
    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;


    public PostCreatedDateResponseDto(Long userId, Long postId, String title, String content, LocalDateTime createdDate) {
        this.userId = userId;
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;

    }

    public static PostCreatedDateResponseDto createPostToDto(Post post) {
        return new PostCreatedDateResponseDto(
                post.getUser().getUserId(),
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedDate()
        );
    }
}
