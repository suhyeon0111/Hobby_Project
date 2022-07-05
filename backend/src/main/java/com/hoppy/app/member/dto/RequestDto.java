package com.hoppy.app.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 수정 요청이 들어올 경우 @RequestBody 의 인자로 받기 위한 RequestDto 선언
 * Postman 사용해서 'localhost:8080/api/mypage/update' 으로 PUT 요청해서 넘겼지만, Content-type not support 에러
 * 장시간 해결하지 못해서 우선 RequestParam 으로 일일히 수정할 값들 받음
 */

@Getter
@Setter
@Builder
public class RequestDto {

    private String username;
    private String profileUrl;
    private String intro;

    @Builder
    public RequestDto(String username, String profileUrl, String intro) {
        this.username = username;
        this.profileUrl = profileUrl;
        this.intro = intro;
    }
}
