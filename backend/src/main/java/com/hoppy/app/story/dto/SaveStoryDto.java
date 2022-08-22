package com.hoppy.app.story.dto;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.Story;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 스토리 업로드, 업데이트 시 단순히 스토리 정보를 반환하기 위한 Dto
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveStoryDto {

    private Long id;

    private String profileUrl;

    private String title;

    private String content;

    private String username;

    private String filename;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public static SaveStoryDto of(Story story, Member member) {
        return SaveStoryDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .content(story.getContent())
                .filename(story.getFilePath())
                .username(member.getUsername())
                .createdDate(story.getCreatedDate())
                .modifiedDate(story.getModifiedDate())
                .build();
    }
}