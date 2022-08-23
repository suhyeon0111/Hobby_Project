package com.hoppy.app.like.domain;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.member.domain.Member;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 태경 2022-07-23
 */
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
public class MemberReReplyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private ReReply reReply;

    public Long getMemberId() {
        return member.getId();
    }

    public Long getReReplyId() {
        return reReply.getId();
    }

    public static MemberReReplyLike of(Member member, ReReply reReply) {
        return MemberReReplyLike.builder()
                .member(member)
                .reReply(reReply)
                .build();
    }
}
