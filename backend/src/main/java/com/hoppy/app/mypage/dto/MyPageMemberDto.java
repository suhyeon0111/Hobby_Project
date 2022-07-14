package com.hoppy.app.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPageMemberDto {

    private String username;
    private String profileUrl;
    private String intro;

}
