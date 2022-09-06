package com.hoppy.app.community.dto;

import com.hoppy.app.community.domain.ReReply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 태경 2022-08-09
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReReplyDto {

    private long id;

    private String authorProfileUrl;

    private String authorName;

    private String content;

    private boolean liked;

    private int likeCount;

    public static ReReplyDto of(ReReply reReply) {
        return ReReplyDto.builder()
                .id(reReply.getId())
                .authorProfileUrl(reReply.getAuthor().getProfileImageUrl())
                .authorName(reReply.getAuthor().getUsername())
                .content(reReply.getContent())
                .build();
    }
}
