package com.hoppy.app.community.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 태경 2022-08-09
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {

    private long id;

    private String ownerProfileUrl;

    private String ownerName;

    private String title;

    private String content;

    private boolean liked;

    private int likeCount;

    private int replyCount;

     private List<ReplyBoxDto> replies;
}
