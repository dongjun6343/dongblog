package com.dongblog.api.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {


    @Id
    @GeneratedValue
    private Long Id;
    private String title;
    @Lob
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
