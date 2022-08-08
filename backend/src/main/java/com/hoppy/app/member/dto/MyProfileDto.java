package com.hoppy.app.member.dto;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.StoryDetailDto;
import java.util.HashSet;
import java.util.List;
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
    private List<StoryDetailDto> myStories;


    public static MyProfileDto of(Member member, List<StoryDetailDto> storyDetails) {

        return MyProfileDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .username(member.getUsername())
                .profileUrl(member.getProfileImageUrl())
                .intro(member.getIntro())
                .deleted(member.isDeleted())
                .myMeetings(member.getMyMeetings())
                .myStories(storyDetails)
                .build();
    }

}
