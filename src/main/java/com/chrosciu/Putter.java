package com.chrosciu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Putter {
    public static List<Ship> putShipsWithGivenSizeOnBoard(List<Integer> shipSizes, int boardSize) {
        List<Ship> ships = new ArrayList<>();
        boolean[][] boardFieldsOccupationFlags = new boolean[boardSize][boardSize];
        for (int i = 0; i < shipSizes.size(); ++i) {
            for (;;) {
                boolean f = new Random().nextBoolean();
                int a = new Random().nextInt(boardSize);
                int b = new Random().nextInt(boardSize - shipSizes.get(i));
                boolean collision = false;
                for (int k = -1; k <= shipSizes.get(i); ++k) {
                    if (b + k < 0) {
                        continue;
                    }
                    if (b + k >= boardSize) {
                        continue;
                    }
                    if (f) {
                        if (a - 1 >= 0) {
                            if (boardFieldsOccupationFlags[a - 1][b + k]) {
                                collision = true;
                                break;
                            }
                        }
                        if (boardFieldsOccupationFlags[a][b + k]) {
                            collision = true;
                            break;
                        }
                        if (a + 1 < boardSize) {
                            if (boardFieldsOccupationFlags[a + 1][b + k]) {
                                collision = true;
                                break;
                            }
                        }
                    } else {
                        if (a - 1 >= 0) {
                            if (boardFieldsOccupationFlags[b + k][a - 1]) {
                                collision = true;
                                break;
                            }
                        }
                        if (boardFieldsOccupationFlags[b + k][a]) {
                            collision = true;
                            break;
                        }
                        if (a + 1 < boardSize) {
                            if (boardFieldsOccupationFlags[b + k][a + 1]) {
                                collision = true;
                                break;
                            }
                        }
                    }
                }
                if (!collision) {
                    for (int k = 0; k < shipSizes.get(i); ++k) {
                        if (f) {
                            boardFieldsOccupationFlags[a][b + k] = true;
                        } else {
                            boardFieldsOccupationFlags[b + k][a] = true;
                        }
                    }
                    ships.add(new Ship(f ? new Field(a, b) : new Field(b, a), shipSizes.get(i), f));
                    break;
                }
            }
        }
        return ships;
    }
}
