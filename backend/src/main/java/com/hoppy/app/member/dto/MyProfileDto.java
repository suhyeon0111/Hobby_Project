package com.hoppy.app.member.dto;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.story.domain.story.Story;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MyProfileDto {

    private Long id;
    private String email;
    private String username;
    private String profileUrl;
    private String intro;
    private boolean deleted;
    private Set<MemberMeeting> myMeetings = new HashSet<>();
    private Set<Story> myStories = new HashSet<>();

    public static MyProfileDto of(Member member) {

        return MyProfileDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .username(member.getUsername())
                .profileUrl(member.getProfileImageUrl())
                .intro(member.getIntro())
                .deleted(member.isDeleted())
                .myMeetings(member.getMyMeetings())
                .myStories(member.getMyStories())
                .build();
    }

    public Set<Long> extractMeetingId(Set<MemberMeeting> meetings) {
        return meetings.stream().map(MemberMeeting::getMeetingId).collect(Collectors.toSet());
    }

}
