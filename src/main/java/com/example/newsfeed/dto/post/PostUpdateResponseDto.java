package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.Post;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class PostUpdateResponseDto {

    private final Long userId;
    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime updateDate;

    public PostUpdateResponseDto(Long userId, Long postId, String title, String content, LocalDateTime updateDate) {
        this.userId = userId;
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.updateDate = updateDate;
    }

    public static PostUpdateResponseDto toUpdateDto(Post post) {
        return new PostUpdateResponseDto(
                post.getUser().getUserId(),
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getUpdatedDate()
        );
    }

}
