package com.chrosciu;

import lombok.Value;

@Value
public class Ship {
    Field firstField;
    int length;
    Direction direction;

    public Field getPreBorderFirstField() {
        return firstField.shift(-1, direction.getPerpendicular());
    }

    public Field getPostBorderFirstField() {
        return firstField.shift(1, direction.getPerpendicular());
    }
}
