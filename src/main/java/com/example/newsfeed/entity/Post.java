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

    @Column(nullable = true, columnDefinition = "longtext")
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post() {

    }

    // FK키(User에서 @Entity와 @Id설정을 하지 않아서 오류 발생으로 인해 주석처리)
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

}

