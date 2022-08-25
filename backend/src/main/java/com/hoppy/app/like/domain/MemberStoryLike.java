package com.hoppy.app.like.domain;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.Story;
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

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString

public class MemberStoryLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Story story;

    public Long getMemberId() {
        return member.getId();
    }

    public static MemberStoryLike of(Member member, Story story) {
        return MemberStoryLike.builder()
                .member(member)
                .story(story)
                .build();
    }
}
