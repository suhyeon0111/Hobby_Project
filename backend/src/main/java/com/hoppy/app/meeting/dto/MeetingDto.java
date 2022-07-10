package com.hoppy.app.meeting.dto;

import com.hoppy.app.meeting.domain.Meeting;
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
public class MeetingDto {

    private Long id;

    private String url;

    private String title;

    private String content;

    private Integer participants;

    private Boolean liked;

    public static MeetingDto meetingToMeetingDto(Meeting meeting, Boolean liked) {
        return MeetingDto.builder()
                .id(meeting.getId())
                .url(meeting.getUrl())
                .title(meeting.getTitle())
                .content(meeting.getContent())
                .participants(meeting.getParticipants().size())
                .liked(liked)
                .build();
    }
}
