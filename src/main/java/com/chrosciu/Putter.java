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
                if (!isCollisionDetectedForShip(ship, boardSize, boardFieldsOccupationFlags)) {
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

    private static boolean isCollisionDetectedForShip(Ship ship, int boardSize, boolean[][] boardFieldsOccupationFlags) {
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = -1; fieldIndex <= ship.getLength(); ++fieldIndex) {
            fields.add(ship.getPreBorderFirstField().shift(fieldIndex, ship.getDirection()));
            fields.add(ship.getFirstField().shift(fieldIndex, ship.getDirection()));
            fields.add(ship.getPostBorderFirstField().shift(fieldIndex, ship.getDirection()));
        }
        boolean collision = false;
        for (Field field: fields) {
            if (isFieldOnBoard(field, boardSize)) {
                if (boardFieldsOccupationFlags[field.getX()][field.getY()]) {
                    collision = true;
                    break;
                }
            }
        }
        return collision;
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
