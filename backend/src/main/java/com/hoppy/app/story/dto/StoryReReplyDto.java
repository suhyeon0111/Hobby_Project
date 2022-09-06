package com.hoppy.app.story.dto;

import com.hoppy.app.story.domain.story.StoryReReply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoryReReplyDto {

    private String profileUrl;

    private String username;

    private String content;

    private boolean liked;

    private int likeCount;

    public static StoryReReplyDto of(StoryReReply storyReReply) {
        return StoryReReplyDto.builder()
                .profileUrl(storyReReply.getMember().getProfileImageUrl())
                .username(storyReReply.getMember().getUsername())
                .content(storyReReply.getContent())
                .build();
    }
}
