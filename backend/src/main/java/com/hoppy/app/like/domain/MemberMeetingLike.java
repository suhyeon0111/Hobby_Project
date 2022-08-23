package com.hoppy.app.like.domain;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.Member;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"member_id", "meeting_id"}
                )
        }
)
public class MemberMeetingLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private Meeting meeting;

    public Long getMemberId() {
        return member.getId();
    }

    public Long getMeetingId() {
        return meeting.getId();
    }

    public static MemberMeetingLike of(Member member, Meeting meeting) {
        return MemberMeetingLike.builder()
                .member(member)
                .meeting(meeting)
                .build();
    }
}
