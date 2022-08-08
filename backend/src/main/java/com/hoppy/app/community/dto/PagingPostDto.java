package com.hoppy.app.community.dto;

import com.hoppy.app.meeting.dto.MeetingDto;
import com.hoppy.app.meeting.dto.PagingMeetingDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 태경 2022-08-06
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingPostDto {

    private List<PostDto> postList;

    private String nextPagingUrl;

    public static PagingPostDto of(List<PostDto> postList, String nextPagingUrl) {
        return PagingPostDto.builder()
                .postList(postList)
                .nextPagingUrl(nextPagingUrl)
                .build();
    }
}
