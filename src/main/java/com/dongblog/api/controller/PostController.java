package com.dongblog.api.controller;

import com.dongblog.api.domain.Post;
import com.dongblog.api.request.PostCreate;
import com.dongblog.api.response.PostResponse;
import com.dongblog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

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
    public Map<String, String> post(@RequestBody @Valid PostCreate params) throws Exception {

        //1. @RequestParam String title, @RequestParam String content
        //2. @RequestParam Map<String, String> params
        // -> String title = params.get("title");
        //3. dto 생성
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
        // => 일단, 구현은 했지만, 아래의 코드는 문제가 있다!!!
        // 1. 에러 검증이 있을때마다 추가를 해야한다.
        // 2. 검증 부분에서 버그가 발생할 여지가 있다.
        // 3. 응답값에 HashMap을 사용했다 -> 응답 클래스를 만들어주는게 좋다.
        // 4. 여러개의 응답처리가 힘들다.
        // => @ControllerAdvice 추가  , BindingResult result 제거
//        if(result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            FieldError firstFieldError = fieldErrors.get(0);
//            String fieldName = firstFieldError.getField(); // title
//            String errorMessage = firstFieldError.getDefaultMessage(); // error msg
//
//            Map<String, String> error = new HashMap<>();
//            error.put(fieldName, errorMessage);
//
//            // Body = {"title":"must not be blank"}
//            // getDefaultMessage => message = "타이블을 입력해주세요."
//
//            return error;
//        }

        log.info("params={}", params.toString());




        return Map.of();
    }

    @PostMapping("/write")
    public void write(@RequestBody @Valid PostCreate params) throws Exception {
        postService.write(params);
        // return Map.of();

        // case 1 . 저장한 데이터 엔티티 -> response로 응답하기.
        // case 2. 저장한 데이터 primary_id -> response로 응답하기.
        // case 3.
        // post에서 응답을 안준다.(나이스한 케이스) -> Http 상태코드! ( Post 요청 : 200, 201 )
        //  => 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 잘 관리함.

        // Bad Case : 서버에서 반드시 ~ 할겁니다.! -> 서버에서는 유연하게 대응하자.
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
        PostResponse response = postService.get(postId);
        return response;
    }
}
