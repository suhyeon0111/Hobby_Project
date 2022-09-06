package com.hoppy.app.community.domain;

import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.domain.MemberReplyLike;
import com.hoppy.app.member.domain.Member;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.*;
import lombok.Builder.Default;
import lombok.ToString.Exclude;
import org.hibernate.annotations.BatchSize;

/**
 * @author 태경 2022-07-21
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Exclude
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @Exclude
    private Post post;

    @OneToMany(mappedBy = "reply", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @Default
    @Exclude
    private Set<ReReply> reReplies = new HashSet<>();

    @OneToMany(mappedBy = "reply", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @Default
    @Exclude
    private Set<MemberReplyLike> likes = new HashSet<>();
}