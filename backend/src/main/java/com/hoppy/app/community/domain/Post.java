package com.hoppy.app.community.domain;

import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.meeting.domain.Meeting;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * @author 태경 2022-07-21
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @Exclude
    private Meeting meeting;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @Default
    @Exclude
    private Set<Reply> replies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @Default
    @Exclude
    private Set<MemberPostLike> likes = new HashSet<>();
}
