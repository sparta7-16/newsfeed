package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "post")
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post() {

    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

}

