package com.example.newsfeed.service;

import com.example.newsfeed.dto.post.*;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.respository.PostRepository;
import com.example.newsfeed.respository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    public PostCreatedDateResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        User user = findusers(request);
        if (postRequestDto.getTitle() == null || postRequestDto.getContent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시물의 작성을 완료해주세요");
        } else if (postRequestDto.getTitle().isEmpty() || postRequestDto.getContent().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시물의 작성을 완료해주세요");
        }
        Post post = new Post(postRequestDto.getContent(), postRequestDto.getContent(), user);
        Post savePost = postRepository.save(post);
        return PostCreatedDateResponseDto.createPostToDto(savePost);
    }

    @Transactional
    public List<PostResponseDto> findAllPost(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<Post> postsPages = postRepository.findPostByUser_UserStatus(pageRequest, "Y");
        return postsPages.stream().map(PostResponseDto::toDto).toList();

    }

    @Transactional
    public PostResponseDto findPostByPostId(Long postId) {
        Post post = findPostById(postId);
        if (post.getUser().getUserStatus().equals("N")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다");
        }
        return PostResponseDto.toDto(findPostById(postId));
    }

    @Transactional
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 값입니다"));
    }

    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto updateRequestDto, HttpServletRequest request) {
        User user = findusers(request);
        Post findPost = findPostById(postId);
        if (user.getUserStatus().equals("N") || !(user.getUserId().equals(findPost.getUser().getUserId()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인의 게시물만 수정할 수 있습니다.");
        }

        if (updateRequestDto.getTitle() != null && updateRequestDto.getContent() != null) {
            findPost.update(updateRequestDto.getTitle(), updateRequestDto.getContent());
        } else if (updateRequestDto.getTitle() != null) {
            findPost.updateTitle(updateRequestDto.getTitle());
        } else if (updateRequestDto.getContent() != null) {
            findPost.updateContent(updateRequestDto.getContent());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        }
        return PostUpdateResponseDto.toUpdateDto(findPostById(postId));
    }

    @Transactional
    public void deletePost(Long postId, HttpServletRequest request) {
        User user = findusers(request);
        Post post = findPostById(postId);
        if (user.getUserStatus().equals("N")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 삭제된 게시물입니다.");
        } else if (!(user.getUserId().equals(post.getUser().getUserId()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인의 게시물만 삭제할 수 있습니다.");
        }
        postRepository.deleteById(postId);
    }

    public User findusers(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("SESSION_KEY");
        User user = userRepository.findByIdOrElseThrow(userId);
        return user;
    }
}
