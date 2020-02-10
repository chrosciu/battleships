package com.chrosciu;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.chrosciu.Direction.HORIZONTAL;
import static com.chrosciu.Direction.VERTICAL;

public class Putter {
    public static List<Ship> putShipsWithGivenSizeOnBoard(List<Integer> shipSizes, int boardSize) {
        List<Ship> ships = new ArrayList<>();
        boolean[][] boardFieldsOccupationFlags = new boolean[boardSize][boardSize];
        for (int shipSize: shipSizes) {
            for (;;) {
                Direction direction = new Random().nextBoolean() ? VERTICAL : HORIZONTAL;
                Field firstField = getRandomFirstFieldForShip(boardSize, shipSize, direction);
                Ship ship = new Ship(firstField, shipSize, direction);
                int a = VERTICAL == direction ? firstField.getX() : firstField.getY();
                int b = VERTICAL == direction ? firstField.getY() : firstField.getX();
                boolean collision = false;
                for (int k = -1; k <= shipSize; ++k) {
                    if (b + k < 0) {
                        continue;
                    }
                    if (b + k >= boardSize) {
                        continue;
                    }
                    if (VERTICAL == direction) {
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
                    markShipFieldsAsOccupied(ship, boardFieldsOccupationFlags);
                    ships.add(new Ship(firstField, shipSize, direction));
                    break;
                }
            }
        }
        return ships;
    }

    private static Field getRandomFirstFieldForShip(int boardSize, int shipSize, @NonNull Direction direction) {
        int constantCoordinate = new Random().nextInt(boardSize);
        int changeableCoordinate = new Random().nextInt(boardSize - shipSize);
        switch (direction) {
            case VERTICAL:
                return new Field(constantCoordinate, changeableCoordinate);
            case HORIZONTAL:
                return new Field(changeableCoordinate, constantCoordinate);
            default:
                throw new IllegalStateException();
        }
    }

    private static boolean isCollisionDetected(Field firstField, int shipSize, @NonNull Direction direction) {
        //TODO
        return false;
    }

    private static boolean isFieldOnBoard(Field field, int boardSize) {
        int x = field.getX();
        int y = field.getY();
        return (x >= 0 && x < boardSize && y >=0 && y < boardSize);
    }

    private static void markShipFieldsAsOccupied(Ship ship, boolean[][] boardFieldsOccupationFlags) {
        for (int fieldIndex = 0; fieldIndex < ship.getLength(); ++fieldIndex) {
            Field shipField = ship.getFirstField().shift(fieldIndex, ship.getDirection());
            boardFieldsOccupationFlags[shipField.getX()][shipField.getY()] = true;
        }
    }




}
