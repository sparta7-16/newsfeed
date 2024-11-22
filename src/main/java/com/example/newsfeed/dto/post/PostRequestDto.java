package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.User;
import lombok.Getter;

@Getter
public class PostRequestDto {

    private final User user;
    private final String title;
    private final String content;

    public PostRequestDto(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }
}
