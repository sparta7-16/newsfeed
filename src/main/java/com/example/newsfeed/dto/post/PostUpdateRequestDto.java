package com.example.newsfeed.dto.post;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {

    private final String title;
    private final String content;

    public PostUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
