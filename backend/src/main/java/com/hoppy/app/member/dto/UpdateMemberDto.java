package com.hoppy.app.member.dto;

import com.hoppy.app.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

/**
 *  단순 프로필 조회와 프로필 수정 시 필요로 하는 인스턴스가 다르므로 [MyProfileDto, UserProfileDto, UpdateMemberDto], Dto를 3가지로 선언
 *  설계 이후 간소화할 필요가 있을 듯.
 */

public class UpdateMemberDto {

    private String username;
    private String profileUrl;
    private String intro;

    public static UpdateMemberDto of(Member member) {
        return UpdateMemberDto.builder()
                .username(member.getUsername())
                .profileUrl(member.getProfileImageUrl())
                .intro(member.getIntro())
                .build();
    }
}
