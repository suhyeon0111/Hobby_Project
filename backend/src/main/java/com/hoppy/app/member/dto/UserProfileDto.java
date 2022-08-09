package com.hoppy.app.member.dto;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.StoryDetailDto;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
/**
 * 마이페이지 조회와 상대방 페이지 조회에서 공개되는 것들이 다를 수 있으므로 별도 선언
 * 공개할 내용들은 추가적인 고민 필요
 * MyProfile =  id, email, username, profileUrl, intro
 * UserProfile = username, profileUrl, intro
 */
public class UserProfileDto {

    private String username;
    private String profileUrl;
    private String intro;
    private Set<MemberMeeting> userMeetings = new HashSet<>();

    public static UserProfileDto of(Member member) {
        return UserProfileDto.builder()
                .username(member.getUsername())
                .profileUrl(member.getProfileImageUrl())
                .intro(member.getIntro())
                .userMeetings(member.getMyMeetings())
                .build();
    }
}
