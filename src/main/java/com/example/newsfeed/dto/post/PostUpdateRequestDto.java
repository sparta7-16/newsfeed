package com.example.newsfeed.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUpdateRequestDto {

    private final String title;
    private final String content;
    private final LocalDateTime updateDate;

    public PostUpdateRequestDto(String title, String content, LocalDateTime updateDate) {
        this.title = title;
        this.content = content;
        this.updateDate = updateDate;
    }
}