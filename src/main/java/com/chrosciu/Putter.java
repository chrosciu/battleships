package com.chrosciu;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.chrosciu.Direction.HORIZONTAL;
import static com.chrosciu.Direction.VERTICAL;

public class Putter {
    private final int boardSize;
    private final boolean[][] boardFieldsOccupationFlags;

    public Putter(int boardSize) {
        this.boardSize = boardSize;
        this.boardFieldsOccupationFlags = new boolean[boardSize][boardSize];
    }

    public List<Ship> putShipsWithGivenSizeOnBoard(List<Integer> shipSizes) {
        List<Ship> ships = new ArrayList<>();
        for (int shipSize: shipSizes) {
            for (;;) {
                Direction direction = new Random().nextBoolean() ? VERTICAL : HORIZONTAL;
                Field firstField = getRandomFirstFieldForShip(shipSize, direction);
                Ship ship = new Ship(firstField, shipSize, direction);
                if (!isCollisionDetectedForShip(ship)) {
                    markShipFieldsAsOccupied(ship);
                    ships.add(new Ship(firstField, shipSize, direction));
                    break;
                }
            }
        }
        return ships;
    }

    private Field getRandomFirstFieldForShip(int shipSize, @NonNull Direction direction) {
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

    private boolean isCollisionDetectedForShip(Ship ship) {
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = -1; fieldIndex <= ship.getLength(); ++fieldIndex) {
            fields.add(ship.getPreBorderFirstField().shift(fieldIndex, ship.getDirection()));
            fields.add(ship.getFirstField().shift(fieldIndex, ship.getDirection()));
            fields.add(ship.getPostBorderFirstField().shift(fieldIndex, ship.getDirection()));
        }
        boolean collision = false;
        for (Field field: fields) {
            if (isFieldOnBoard(field)) {
                if (boardFieldsOccupationFlags[field.getX()][field.getY()]) {
                    collision = true;
                    break;
                }
            }
        }
        return collision;
    }

    private boolean isFieldOnBoard(Field field) {
        int x = field.getX();
        int y = field.getY();
        return (x >= 0 && x < boardSize && y >=0 && y < boardSize);
    }

    private void markShipFieldsAsOccupied(Ship ship) {
        for (int fieldIndex = 0; fieldIndex < ship.getLength(); ++fieldIndex) {
            Field shipField = ship.getFirstField().shift(fieldIndex, ship.getDirection());
            boardFieldsOccupationFlags[shipField.getX()][shipField.getY()] = true;
        }
    }
}
