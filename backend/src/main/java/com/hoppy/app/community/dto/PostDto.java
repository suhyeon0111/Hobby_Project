package com.hoppy.app.community.dto;

import com.hoppy.app.community.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 태경 2022-08-05
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private String memberProfileUrl;

    private String memberName;

    private String title;

    private String content;

    private boolean liked;

    private int likeCount;

    private int replyCount;

    public static PostDto postToPostDto(Post post, boolean liked, int likeCount) {
        return PostDto.builder()
                .memberProfileUrl(post.getOwner().getProfileImageUrl())
                .memberName(post.getOwner().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .liked(liked)
                .likeCount(likeCount)
                .replyCount(post.getReplies().size())
                .build();
    }
}
