package com.dongblog.api.service;

import com.dongblog.api.domain.Post;
import com.dongblog.api.repository.PostRepository;
import com.dongblog.api.request.PostCreate;
import com.dongblog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clear(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test(){

        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();


        // when
        postService.write(postCreate);


        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());

    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {

        //given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        // Long postId = 1L; 해당 글을 위에서 저장해줘야 한다.

        //when
        PostResponse post = postService.get(requestPost.getId());

        //then
        assertNotNull(post);
        assertEquals(1L, postRepository.count());
        assertEquals("foo", post.getTitle());
        assertEquals("bar", post.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test3() {
        //given
//        Post requestPost1 = Post.builder()
//                .title("foo1")
//                .content("bar1")
//                .build();
//        postRepository.save(requestPost1);
//
//        Post requestPost2 = Post.builder()
//                .title("foo2")
//                .content("bar2")
//                .build();
//        postRepository.save(requestPost2);


        // 한번에 저장
        postRepository.saveAll(List.of(
                Post.builder()
                .title("foo1")
                .content("bar1")
                .build(),
                Post.builder()
                .title("foo2")
                .content("bar2")
                .build()
        ));


        //when
        List<PostResponse> list = postService.getList();

        //then
        assertEquals(2L, list.size());
    }

}