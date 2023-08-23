package com.dongblog.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    // SSR -> jsp, thymeleaf, mustache, freemarker
            // -> html rendering
    // SPA
    // vue -> vue + SSR -> nuxt.js
    // react -> react + SSR -> next.js
            // -> javascript <---> API (json)

    @GetMapping("/posts")
    public String get(){
        return "Hello World";
    }
}
