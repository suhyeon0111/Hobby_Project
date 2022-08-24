package com.hoppy.app.story.dto;

import com.hoppy.app.story.domain.story.Story;
import java.time.LocalDateTime;
import java.util.List;
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
    private String filename;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static StoryDetailDto from(Story story) {
        return StoryDetailDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .content(story.getContent())
                .username(story.getMember().getUsername())
                .filename(story.getFilePath())
                .createdDate(story.getCreatedDate())
                .modifiedDate(story.getModifiedDate())
                .build();
    }
}