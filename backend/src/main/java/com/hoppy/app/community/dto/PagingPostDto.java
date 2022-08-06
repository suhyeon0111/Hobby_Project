package com.hoppy.app.community.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 태경 2022-08-06
 */
@Getter
public class PagingPostDto extends PagingDto<PostDto> {

    public PagingPostDto(List<PostDto> postDtos, String nextPagingUrl) {
        super(postDtos, nextPagingUrl);
    }
}
