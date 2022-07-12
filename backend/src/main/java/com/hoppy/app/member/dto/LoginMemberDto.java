package com.hoppy.app.member.dto;

import com.hoppy.app.member.domain.Member;
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

    private LoginMemberDto(Long id, String email, String username, String profileUrl, String intro) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.profileUrl = profileUrl;
        this.intro = intro;
    }

    public static LoginMemberDto of(Member member) {
        return new LoginMemberDto(member.getId(), member.getEmail(), member.getUsername(), member.getProfileImageUrl(), member.getIntro());
    }
}
