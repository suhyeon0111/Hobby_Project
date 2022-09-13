package com.hoppy.app.story.dto;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.domain.story.StoryReReply;
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
public class StoryReReplyRequestDto {
    private String content;

//    @Builder.Default
//    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

    public StoryReReply toEntity(Member member, StoryReply reply) {
        return StoryReReply.builder()
                .content(content)
                .member(member)
                .reply(reply)
//                .createdDate(createdDate)
                .build();
    }
}
