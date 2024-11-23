package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
public class PostResponseDto {


    private final Long userId;
    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime updateDate;

    public PostResponseDto(Long userId, Long postId, String title, String content, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.userId = userId;
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public static PostResponseDto toDto(Post post) {
        return new PostResponseDto(
                post.getUser().getUserId(),
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedDate(),
                post.getUpdatedDate()
        );
    }
}
