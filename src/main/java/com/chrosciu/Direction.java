package com.chrosciu;

import lombok.Getter;

@Getter
public enum Direction {
    VERTICAL,
    HORIZONTAL;

    private Direction perpendicular;

    static {
        VERTICAL.perpendicular = HORIZONTAL;
        HORIZONTAL.perpendicular = VERTICAL;
    }
}
