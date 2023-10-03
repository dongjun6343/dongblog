package com.dongblog.api.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    String content;
}