package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostPageableResponseDto {
    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime updateDate;

    public PostPageableResponseDto(Post entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.updateDate = entity.getUpdatedDate();
    }

}

