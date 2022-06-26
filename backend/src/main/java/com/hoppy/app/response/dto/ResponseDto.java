package com.hoppy.app.response.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto {
    private int status;
    private String message;
    private Object data;

    public static ResponseDto commonResponse(int status, String msg) {
        return new ResponseDto(status, msg, new ArrayList<>());
    }
    public static ResponseDto commonResponse(int status, String msg, Object data) {
        return new ResponseDto(status, msg, data);
    }
}