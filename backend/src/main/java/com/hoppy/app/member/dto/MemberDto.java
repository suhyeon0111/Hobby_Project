package com.hoppy.app.member.dto;

import com.hoppy.app.login.oauth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberDto {

    private String email;
    private String username;
    private String profileUrl;
    private String socialId;
    private String jwt;

//    public static MemberDto of(Member member) {
//
//        return MemberDto.builder()
//                .email(member.getEmail())
//                .username(member.getUsername())
//                .socialId(member.getSocialId())
//                .build();
//    }
}
