package com.hoppy.app.story.dto;

import com.hoppy.app.story.domain.story.StoryReReply;
import com.hoppy.app.story.domain.story.StoryReply;
import java.util.List;
import java.util.stream.Collectors;
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
public class StoryReplyDto {

    private String profileUrl;

    private String username;

    private String content;

    private boolean liked;

    private int likeCount;

    private List<StoryReReplyDto> reReplies;

    public static StoryReplyDto of(StoryReply storyReply) {
        return StoryReplyDto.builder()
                .profileUrl(storyReply.getMember().getProfileImageUrl())
                .username(storyReply.getMember().getUsername())
                .content(storyReply.getContent())
                .reReplies(storyReply.getReReplies().stream().map(StoryReReplyDto::of).collect(
                        Collectors.toList()))
                .build();
    }
}
