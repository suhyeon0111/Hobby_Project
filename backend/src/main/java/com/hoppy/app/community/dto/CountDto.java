package com.hoppy.app.community.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author 태경 2022-08-06
 */
@Builder
@Getter
public class CountDto {

    private Long id;

    private int count;

    public static CountDto of(Long id, int count) {
        return CountDto.builder()
                .id(id)
                .count(count)
                .build();
    }
}
