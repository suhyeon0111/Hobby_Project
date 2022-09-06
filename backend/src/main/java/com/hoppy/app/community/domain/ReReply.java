package com.hoppy.app.community.domain;

import com.hoppy.app.like.domain.MemberReReplyLike;
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
import javax.persistence.ManyToOne;
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
import org.hibernate.annotations.BatchSize;

/**
 * @author 태경 2022-07-23
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ReReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Exclude
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @Exclude
    private Reply reply;

    @OneToMany(mappedBy = "reReply", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @Default
    @Exclude
    private Set<MemberReReplyLike> likes = new HashSet<>();
}
