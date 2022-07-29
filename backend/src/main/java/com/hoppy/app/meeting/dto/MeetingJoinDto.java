package com.hoppy.app.meeting.dto;

import com.hoppy.app.meeting.domain.Meeting;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 태경 2022-07-29
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingJoinDto {

    @NotNull(message = "모임 id가 필요합니다")
    private Long meetingId;
}
