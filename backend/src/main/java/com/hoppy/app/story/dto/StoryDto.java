package com.hoppy.app.story.dto;

import com.hoppy.app.story.domain.story.Story;
import java.time.LocalDateTime;
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
public class StoryDto {

    private Long id;

    private String profileUrl;

    private String username;

    private String title;

    private String content;

    private boolean liked;

    private int likeCount;

    private int replyCount;

    private LocalDateTime createdDate;

    public static StoryDto of(Story story) {
//        public static StoryDto of(Story story, boolean liked, int likeCount, int replyCount) {
        return StoryDto.builder()
                .id(story.getId())
                .profileUrl(story.getMember().getProfileImageUrl())
                .username(story.getMember().getUsername())
                .title(story.getTitle())
                .content(story.getContent())
//                .likeCount(likeCount)
                .likeCount(story.getLikes().size())
                .replyCount(story.getReplies().size())
//                .replyCount(replyCount)
                .createdDate(story.getCreatedDate())
                .build();
    }
}
