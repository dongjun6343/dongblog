package com.dongblog.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/posts 요청 시 Hello World를 출력한다.")
    void test() throws Exception {
        // 글 제목
        // 글 내용

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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"글 제목입니다.\", \"content\": \"글 내용입니다.\"}"))
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
        // 글 제목
        // 글 내용

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\", \"content\": \"글 내용입니다.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }
}