package com.chrosciu;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class Ship {
    Field firstField;
    int length;
    Direction direction;

    public List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = 0; fieldIndex < length; ++fieldIndex) {
            fields.add(direction.getFieldShifter().apply(firstField, fieldIndex));
        }
        return fields;
    }

    public List<Field> getAllFieldsWithBorder() {
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = -1; fieldIndex <= length; ++fieldIndex) {
            fields.add(direction.getFieldShifter().apply(getPreBorderFirstField(), fieldIndex));
            fields.add(direction.getFieldShifter().apply(firstField, fieldIndex));
            fields.add(direction.getFieldShifter().apply(getPostBorderFirstField(), fieldIndex));
        }
        return fields;
    }

    private Field getPreBorderFirstField() {
        return direction.getPerpendicularDirection().getFieldShifter().apply(firstField, -1);
    }

    private Field getPostBorderFirstField() {
        return direction.getPerpendicularDirection().getFieldShifter().apply(firstField, 1);
    }
}
