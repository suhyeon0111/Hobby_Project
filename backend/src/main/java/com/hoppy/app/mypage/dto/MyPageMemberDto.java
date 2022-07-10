package com.hoppy.app.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyPageMemberDto {

    private String username;
    private String profileUrl;
    private String intro;
}
