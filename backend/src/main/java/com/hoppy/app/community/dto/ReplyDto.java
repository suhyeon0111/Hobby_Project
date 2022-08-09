package com.hoppy.app.community.dto;

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
public class ReplyDto {

    private String ownerProfileUrl;

    private String ownerName;

    private String content;

    private boolean liked;

    private int likeCount;
}
