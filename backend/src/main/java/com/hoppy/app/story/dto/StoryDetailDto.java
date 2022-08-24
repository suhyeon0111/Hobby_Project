package com.hoppy.app.story.dto;

import com.hoppy.app.story.domain.story.Story;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 스토리 리스트에 표기될 단순 정보를 포함한 좋아요 수, 댓글 수를 반환하는 Dto
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryDetailDto {

    private Long id;

    private String profileUrl;

    private String title;

    private String content;

    private String username;

    private String filename;

    private boolean liked;

    private int likeCount;

    private int replyCount;

    private LocalDateTime createdDate;

    private List<StoryReplyDto> replies;

    public static StoryDetailDto from(Story story) {
        return StoryDetailDto.builder()
                .id(story.getId())
                .profileUrl(story.getMember().getProfileImageUrl())
                .title(story.getTitle())
                .content(story.getContent())
                .username(story.getMember().getUsername())
                .filename(story.getFilePath())
                .createdDate(story.getCreatedDate())
                .build();
    }
}