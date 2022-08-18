package com.hoppy.app.story.dto;

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
public class PagingStoryDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private String filename;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static PagingStoryDto from(Story story) {
        return PagingStoryDto.builder()
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
