package com.hoppy.app.like.domain;

import com.hoppy.app.community.domain.Post;
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
 * @author 태경 2022-07-22
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
                        columnNames={"member_id", "reply_id"}
                )
        }
)
public class MemberReplyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private Reply reply;

    public Long getMemberId() {
        return member.getId();
    }

    public Long getReplyId() {
        return reply.getId();
    }

    public static MemberReplyLike of(Member member, Reply reply) {
        return MemberReplyLike.builder()
                .member(member)
                .reply(reply)
                .build();
    }
}