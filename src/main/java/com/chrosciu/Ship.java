package com.chrosciu;

import lombok.Value;

@Value
public class Ship {
    Field firstField;
    int length;
    Direction direction;

    public Field getPreBorderFirstField() {
        switch (direction) {
            case VERTICAL:
                return firstField.shift(-1, Direction.HORIZONTAL);
            case HORIZONTAL:
                return firstField.shift(-1, Direction.VERTICAL);
            default:
                throw new IllegalStateException();
        }
    }

    public Field getPostBorderFirstField() {
        switch (direction) {
            case VERTICAL:
                return firstField.shift(1, Direction.HORIZONTAL);
            case HORIZONTAL:
                return firstField.shift(1, Direction.VERTICAL);
            default:
                throw new IllegalStateException();
        }
    }
}
