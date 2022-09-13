package com.hoppy.app.member.domain;

import com.hoppy.app.meeting.domain.Meeting;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMeeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Meeting meeting;

    public Long getMemberId() {
        return member.getId();
    }

    public Long getMeetingId() {
        return meeting.getId();
    }

    public static MemberMeeting of(Member member, Meeting meeting) {
        return MemberMeeting.builder()
                .member(member)
                .meeting(meeting)
                .build();
    }
}
