package com.hoppy.app.story.domain.story;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name="memberId")
    private Long memberId;

    /**
     * 파일 업로드 기능 추가 필요
     */

    public static Story of(UploadStoryDto dto, Member member) {
        return Story.builder()
                .username(member.getUsername())
                .memberId(member.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
}
