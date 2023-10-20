package com.dongblog.api.service;


import com.dongblog.api.domain.Post;
import com.dongblog.api.repository.PostRepository;
import com.dongblog.api.request.PostCreate;
import com.dongblog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        // postCreate (일반클래스) -> entity

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return response;

    }


    public List<PostResponse> getList() {
//        return postRepository.findAll().stream()
//                .map(post -> PostResponse.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent()).build())
//                .collect(Collectors.toList());
                return postRepository.findAll().stream()
                        .map(PostResponse::new)
                        .collect(Collectors.toList());
    }
}
