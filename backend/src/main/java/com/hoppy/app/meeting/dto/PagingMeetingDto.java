package com.hoppy.app.meeting.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 태경 2022-07-04
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingMeetingDto {

    private List<MeetingDto> meetingList;

    private String nextPagingUrl;

    public static PagingMeetingDto of(List<MeetingDto> meetingList, String nextPagingUrl) {
        return PagingMeetingDto.builder()
                .meetingList(meetingList)
                .nextPagingUrl(nextPagingUrl)
                .build();
    }
}
