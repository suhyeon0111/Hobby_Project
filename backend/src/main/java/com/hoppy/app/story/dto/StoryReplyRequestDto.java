package com.hoppy.app.story.dto;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.domain.story.StoryReply;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoryReplyRequestDto {

    private Long id;
    private String content;
    private Member member;
    private Story story;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

    public StoryReply toEntity() {
        StoryReply reply = StoryReply.builder()
                .id(id)
                .content(content)
                .member(member)
                .story(story)
                .createDate(createdDate)
                .build();
        return reply;
    }
}
