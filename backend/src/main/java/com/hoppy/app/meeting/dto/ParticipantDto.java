package com.hoppy.app.meeting.dto;

import com.hoppy.app.member.domain.Member;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 태경 2022-07-18
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {

    private Long id;

    private String url;

    private String name;

    private Boolean owner;

    public static ParticipantDto memberToParticipantDto(Member member, Long ownerId) {
        return ParticipantDto.builder()
                .id(member.getId())
                .url(member.getProfileImageUrl())
                .name(member.getUsername())
                .owner(Objects.equals(member.getId(), ownerId))
                .build();
    }
}
