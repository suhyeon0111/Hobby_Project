package com.hoppy.app.meeting.domain;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberMeeting;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Exclude
    private Member owner;

    @Column(nullable = false)
    @Builder.Default
    private String url = "default-url";

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Integer memberLimit;

    @Column
    @Setter
    @Builder.Default
    private Boolean fullFlag = false;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @Builder.Default
    @Exclude
    private Set<MemberMeeting> participants = new HashSet<>();

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @Builder.Default
    @Exclude
    private Set<MemberMeetingLike> likes = new HashSet<>();

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @Builder.Default
    @Exclude
    private Set<Post> posts = new HashSet<>();

    public Boolean isFull() {
        return this.fullFlag;
    }

    public void addParticipant(MemberMeeting participant) {
        participants.add(participant);
    }

    public static Meeting of(CreateMeetingDto dto, Member owner) {
        return Meeting.builder()
                .owner(owner)
                .url(dto.getFilename())
                .title(dto.getTitle())
                .content(dto.getContent())
                .memberLimit(dto.getMemberLimit())
                .category(Category.intToCategory(dto.getCategory()))
                .build();
    }
}
