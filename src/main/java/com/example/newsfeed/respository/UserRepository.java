package com.example.newsfeed.respository;

import com.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    List<User> findAllByUserStatus(String y);



    User findByUserIdAndUserStatus(Long id, String y);
}
