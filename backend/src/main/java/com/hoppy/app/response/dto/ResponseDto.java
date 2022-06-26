package com.hoppy.app.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto {
    private int status;
    private String message;
    private Data responseData;

    public static ResponseDto commonResponse(int status, String msg) {
        return new ResponseDto(status, msg, new Data());
    }
    public static ResponseDto commonResponse(int status, String msg, Data responseData) {
        return new ResponseDto(status, msg, responseData);
    }

    // 기본적으로 ResponseData를 상속받아 필요한 데이터 타입의 shape을 정의하고, ResponseDto에 주입하여 응답 Dto로 사용한다.
}