package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private final Long postId;
    private final String title;
    private final String content;
//    BaseEntity의 부재로 주석처리
//    private final LocalDateTime createdDate;
//    private final LocalDateTime updateDate;

    public PostResponseDto(Long PostId, String title, String content/*,LocalDateTime createdDate, LocalDateTime updateDate*/) {
        this.postId = PostId;
        this.title = title;
        this.content = content;
//        BaseEntity의 부재로 주석처리
//        this.createdDate = createdDate;
//        this.updateDate = updateDate;
    }

    public static PostResponseDto toDto(Post post) {
        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent()
        );
    }
}

