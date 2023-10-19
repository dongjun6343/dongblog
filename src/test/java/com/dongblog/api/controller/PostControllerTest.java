package com.dongblog.api.controller;

import com.dongblog.api.domain.Post;
import com.dongblog.api.repository.PostRepository;
import com.dongblog.api.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository; // 테스트 시에만 필드주입


    // 메서드들이 수행하기 전에 실행해준다.
    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 Hello World를 출력한다.")
    void test() throws Exception {

        // given
        // PostCreate request = new PostCreate("글 제목입니다.", "글 내용입니다.");
        // 생성자의 파라미터 순서가 바뀌면 버그를 찾기 힘들다 -> 빌더패턴 사용!

        PostCreate request = PostCreate.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("title", "글 제목입니다.")
//                        .param("content", "글 내용입니다."))
//                        Parameters = {title=[글 제목입니다.], content=[글 내용입니다.]}
//                        Headers = [Content-Type:"application/x-www-form-urlencoded;charset=UTF-8"]
//                        Body = null
//                        -> 키-벨류 데이터의 한계

                        // json으로 변경하자! Body에 값이 들어감 -> 따라서 controller에서 @RequestBody추가.
                        // Body = {"title": "글 제목입니다.", "content": "글 내용입니다."}
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }
    /**
     * 만약 사용자가 추가된다면?
     * 키-벨류 데이터는 한계가 있다.
     * => json 데이터로 통신하게 됨.
     * {
     *      "title":"xxx",
     *      "content":"xxx",
     *      "user":{
     *              "id":"xxx", ...
     *      }
     * }
     */


    @Test
    @DisplayName("/posts 요청 시 글 제목은 필수값입니다.")
    void test1() throws Exception {
        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\": \"\", \"content\": \"글 내용입니다.\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/write 요청 시 DB에 저장된다.")
    void test2() throws Exception {
        // when
        mockMvc.perform(post("/write")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다.\", \"content\": \"글 내용입니다.\"}"))
                .andExpect(status().isOk())
                .andDo(print());
        // then
        assertEquals(1L,postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("글 내용입니다.", post.getContent());
    }
    // @SpringBootTest -> controller - service - repository 레이어 추가를 했기 때문에 변경이 필요.

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);



        // 클라이언트 요구사항
        // json 응답에서 title값 길이를 최대 10글자로 해주세요. ( 보통은 클라이언트에서 하는게 맞긴 함 )


        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }
}