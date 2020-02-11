package com.chrosciu;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

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

    public List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = 0; fieldIndex < length; ++fieldIndex) {
            fields.add(firstField.shift(fieldIndex, direction));
        }
        return fields;
    }

    public List<Field> getAllFieldsWithBorder() {
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = -1; fieldIndex <= length; ++fieldIndex) {
            fields.add(getPreBorderFirstField().shift(fieldIndex, direction));
            fields.add(firstField.shift(fieldIndex, direction));
            fields.add(getPostBorderFirstField().shift(fieldIndex, direction));
        }
        return fields;
    }
}
