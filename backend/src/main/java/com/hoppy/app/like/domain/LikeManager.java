package com.hoppy.app.like.domain;

import com.hoppy.app.member.domain.Member;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * @author 태경 2022-07-22
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LikeManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "likeManager")
    @Exclude
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "likeManager")
    @Default
    @Exclude
    private Set<MemberMeetingLike> meetingLikes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "likeManager")
    @Default
    @Exclude
    private Set<MemberPostLike> postLikes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "likeManager")
    @Default
    @Exclude
    private Set<MemberReplyLike> replyLikes = new HashSet<>();
}
