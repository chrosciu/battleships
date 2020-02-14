package com.chrosciu;

import lombok.Value;
import lombok.With;

@Value
@With
public class Field {
    int x;
    int y;

    public boolean isOnBoard(int boardSize) {
        return (x >= 0 && x < boardSize && y >=0 && y < boardSize);
    }
}
