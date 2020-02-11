package com.chrosciu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@Getter
public enum Direction {
    VERTICAL((field, shift) -> new Field(field.getX(), field.getY() + shift)),
    HORIZONTAL((field, shift) -> new Field(field.getX() + shift, field.getY()));

    private Direction perpendicularDirection;
    private final BiFunction<Field, Integer, Field> fieldShifter;

    static {
        VERTICAL.perpendicularDirection = HORIZONTAL;
        HORIZONTAL.perpendicularDirection = VERTICAL;
    }
}
