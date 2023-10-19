package com.dongblog.api.domain;


import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }


//    public String getTitle(){
        // 서비스의 정책을 넣지마세요!!! 절때!!!!!
//        return this.title.substring(0, 10);
//    }
    // 그러면 어떻게 해야할까?
    // 응답 클래스를 만드세요! -> PostResponse

}
