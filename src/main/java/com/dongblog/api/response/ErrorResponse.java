package com.dongblog.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code":"400",
 *     "message":"잘못된 요청입니다.",
 *     "validation":{
 *         "title": "값을 입력해주세요."
 *     }
 *
 * }
 */


@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;

    // map을 사용하는것은 지양 -> 개선하는 방법에 대해서 생각해보자.
    private final Map<String, String> validation = new HashMap<>();

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
