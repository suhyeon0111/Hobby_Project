package com.hoppy.app.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginMemberDto {

    private Long id;
    private String email;
    private String username;
    private String profileUrl;
    private String intro;
}
