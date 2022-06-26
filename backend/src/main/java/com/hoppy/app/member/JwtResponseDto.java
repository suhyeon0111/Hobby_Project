package com.hoppy.app.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtResponseDto {

    @JsonProperty
    private final String jwt;
}
