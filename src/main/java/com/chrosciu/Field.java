package com.chrosciu;

import lombok.NonNull;
import lombok.Value;

@Value
public class Field {
    int x;
    int y;

    public Field shift(int shift, @NonNull Direction direction) {
        switch (direction) {
            case VERTICAL:
                return new Field(x, y + shift);
            case HORIZONTAL:
                return new Field(x + shift, y);
            default:
                throw new IllegalStateException();
        }
    }
}
