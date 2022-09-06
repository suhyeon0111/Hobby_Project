package com.hoppy.app.like.domain;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.StoryReReply;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"member_id", "reReply_id"}
                )
        }
)
public class MemberStoryReReplyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    @JoinColumn(name = "reReply_id")
    private StoryReReply reReply;

    public static MemberStoryReReplyLike of(Member member, StoryReReply reReply) {
        return MemberStoryReReplyLike.builder()
                .member(member)
                .reReply(reReply)
                .build();
    }
}
