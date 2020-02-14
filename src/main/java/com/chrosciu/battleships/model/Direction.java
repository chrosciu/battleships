package com.chrosciu.battleships.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@Getter
public enum Direction {
    VERTICAL((field, shift) -> field.withY(field.getY() + shift),
            (constant, changeable) -> new Field(constant, changeable)),
    HORIZONTAL((field, shift) -> field.withX(field.getX() + shift),
            (constant, changeable) -> new Field(changeable, constant));

    private Direction perpendicularDirection;
    private final BiFunction<Field, Integer, Field> fieldShifter;
    private final BiFunction<Integer, Integer, Field> fieldCreator;

    static {
        VERTICAL.perpendicularDirection = HORIZONTAL;
        HORIZONTAL.perpendicularDirection = VERTICAL;
    }
}
