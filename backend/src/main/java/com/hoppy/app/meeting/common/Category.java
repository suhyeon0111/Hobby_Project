package com.hoppy.app.meeting.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {

    HEALTH,
    ART,
    MUSIC,
    LIFE,
    FOOD,
    TRIP
    ;
}