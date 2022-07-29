package com.hoppy.app.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {

    HEALTH(1),
    ART(2),
    MUSIC(3),
    LIFE(4),
    FOOD(5),
    TRIP(6),
    ERROR(9999),
    ;

    private int num;

    public static Category intToCategory(int categoryNum) {

        for(Category c : Category.values()) {
            if(c.getNum() == categoryNum) {
                return c;
            }
        }
        return ERROR;
    }
}
