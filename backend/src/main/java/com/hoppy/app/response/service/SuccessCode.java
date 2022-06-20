package com.hoppy.app.response.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SuccessCode {
    //    Auth
    SIGN_SUCCESS("회원가입 성공", 200),
    LOGIN_SUCCESS("로그인 성공", 200),
    LOGOUT_SUCCESS("로그아웃 성공", 200),
    REISSUE_SUCCESS("토큰 재발급 성공", 200),

    HEALTH_CHECK_SUCCESS("정상 동작 중", 200),
    ;

    private final String message;
    private final int status;
}