package com.hoppy.app.like.domain;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.member.domain.Member;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
            columnNames={"member_id", "post_id"}
        )
    }
)
public class MemberPostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private Post post;

    public Long getMemberId() {
        return member.getId();
    }

    public Long getPostId() {
        return post.getId();
    }

    public static MemberPostLike of(Member member, Post post) {
        return MemberPostLike.builder()
                .member(member)
                .post(post)
                .build();
    }
}
