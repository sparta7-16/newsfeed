package com.example.newsfeed.dto.post;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private final String title;
    private final String content;

    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
