package com.dongblog.api.controller;

import com.dongblog.api.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class PostController {
    // SSR -> jsp, thymeleaf, mustache, freemarker
            // -> html rendering
    // SPA
    // vue -> vue + SSR -> nuxt
    // react -> react + SSR -> next
            // -> javascript <---> API (json)

    // @RequestMapping(method = RequestMethod.GET, path = "v1/posts") 에전 방식
    @GetMapping("/posts")
    public String get(){
        return "Hello World";
    }


    // Http Method
    // GET, POST, DELETE, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) throws Exception {

        //1. @RequestParam String title, @RequestParam String content
        //2. @RequestParam Map<String, String> params
        // -> String title = params.get("title");
        //3. dto 생성
        log.info("params={}", params.toString());



        /**
         *   데이터를 검증하는 이유
         *          1. client 개발자가 실수로 값을 안보낼 수 있다.
         *          2. client 버그로 값 누락
         *          3. 값을 임의로 조작해서 보낼 수 있다.
         *          4. 디비에 저장할 때 의도치않은 오류 발생.
         *          5. 서버 개발자의 편안함을 위해서
         *
         *  String title = params.getTitle();
         *  if(title == null || title.equals("")){
         *      throw new Exception("타이블 값이 없어요!");
         *  }
         *  String content = params.getContent();
         *   if(content == null || content.equals("")){
         *      //error
         *  }
         *  이런식으로 에러검증을 하게되면 생각보다 검증할 게 많다. (노다가성)
         *  => 스프링에서 제공하는 vaildation 추가.
         */

        // @vaild를 사용했을때 client한테 에러메시지를 보내고 싶다!
        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField(); // title
            String errorMessage = firstFieldError.getDefaultMessage(); // error msg

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);

            // Body = {"title":"must not be blank"}
            // getDefaultMessage => message = "타이블을 입력해주세요."

            return error;
        }
        return Map.of();
    }
}
