package com.chrosciu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@Getter
public enum Direction {
    VERTICAL((field, shift) -> new Field(field.getX(), field.getY() + shift),
            (constant, changeable) -> new Field(constant, changeable)),
    HORIZONTAL((field, shift) -> new Field(field.getX() + shift, field.getY()),
            (constant, changeable) -> new Field(changeable, constant));

    private Direction perpendicularDirection;
    private final BiFunction<Field, Integer, Field> fieldShifter;
    private final BiFunction<Integer, Integer, Field> fieldCreator;

    static {
        VERTICAL.perpendicularDirection = HORIZONTAL;
        HORIZONTAL.perpendicularDirection = VERTICAL;
    }
}
