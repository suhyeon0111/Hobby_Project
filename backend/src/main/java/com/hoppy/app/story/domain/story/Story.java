package com.hoppy.app.story.domain.story;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Story extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String username;

    @Column(nullable = false)
    private String content;

    @Column
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @Exclude
//    @JsonIgnore
//    @JoinColumn(name = "memberId")
    private Member member;

    private boolean deleted;

    public static Story of(UploadStoryDto dto, Member member) {
        return Story.builder()
                .member(member)
                .username(member.getUsername())
                .title(dto.getTitle())
                .content(dto.getContent())
                .deleted(false)
                .build();
    }
}
