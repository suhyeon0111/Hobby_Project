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
public class PagingStoryDto {

    private List<StoryDetailDto> storyList;
    private List<StoryDto> storyDtoList;
    private String nextPagingUrl;

    public static PagingStoryDto of(List<StoryDto> storyDtoList, String nextPagingUrl) {
        return PagingStoryDto.builder()
                .storyDtoList(storyDtoList)
                .nextPagingUrl(nextPagingUrl)
                .build();
    }
}
