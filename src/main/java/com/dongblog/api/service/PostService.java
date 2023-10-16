package com.dongblog.api.service;


import com.dongblog.api.domain.Post;
import com.dongblog.api.repository.PostRepository;
import com.dongblog.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        // postCreate (일반클래스) -> entity

        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        postRepository.save(post);
    }
}
