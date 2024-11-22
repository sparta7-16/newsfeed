package com.example.newsfeed.respository;

import com.example.newsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}