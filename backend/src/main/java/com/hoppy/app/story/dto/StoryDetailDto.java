package com.hoppy.app.story.dto;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.BaseTimeEntity;
import com.hoppy.app.story.domain.story.Story;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryDetailDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private Long memberId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static StoryDetailDto of(Story story, Member member) {
        return StoryDetailDto.builder()
                .id(story.getId()).
                title(story.getTitle())
                .content(story.getContent())
                .memberId(member.getId())
                .username(member.getUsername())
                .createdDate(story.getCreatedDate())
                .modifiedDate(story.getModifiedDate()).build();
    }
}
