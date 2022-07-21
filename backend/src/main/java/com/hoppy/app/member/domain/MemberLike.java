package com.hoppy.app.member.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class MemberLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "memberLike")
    @Exclude
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberLike")
    @Default
    @Exclude
    private Set<MemberMeetingLike> meetingLikes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberLike")
    @Default
    @Exclude
    private Set<MemberPostLike> postLikes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberLike")
    @Default
    @Exclude
    private Set<MemberReplyLike> replyLikes = new HashSet<>();
}
