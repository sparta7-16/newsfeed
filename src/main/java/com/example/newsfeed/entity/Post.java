package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor
public class Post extends BaseEntity{

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = true, name = "title")
    private String title;

    @Column(nullable = true, name = "content")
    private String content;

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }



    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}