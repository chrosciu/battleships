package com.chrosciu;

import lombok.NonNull;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.chrosciu.Direction.HORIZONTAL;
import static com.chrosciu.Direction.VERTICAL;

public class BoardLocator {
    private final int boardSize;
    private final boolean[][] boardFieldsOccupationFlags;

    public BoardLocator(int boardSize) {
        this.boardSize = boardSize;
        this.boardFieldsOccupationFlags = new boolean[boardSize][boardSize];
    }

    public List<Ship> locateShipsWithGivenSizeOnBoard(List<Integer> shipSizes) {
        return shipSizes.stream()
                .map(this::locateShipWithGivenSizeOnBoard)
                .collect(Collectors.toList());
    }

    private Ship locateShipWithGivenSizeOnBoard(Integer shipSize) {
        while (true) {
            Direction direction = new Random().nextBoolean() ? VERTICAL : HORIZONTAL;
            Field firstField = getRandomFirstFieldForShip(shipSize, direction);
            Ship ship = new Ship(firstField, shipSize, direction);
            if (!isCollisionDetectedForShip(ship)) {
                markShipFieldsAsOccupied(ship);
                return ship;
            }
        }
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
        boolean collision = false;
        for (Field field: ship.getAllFieldsWithBorder()) {
            if (field.isOnBoard(boardSize)) {
                if (boardFieldsOccupationFlags[field.getX()][field.getY()]) {
                    collision = true;
                    break;
                }
            }
        }
        return collision;
    }

    private void markShipFieldsAsOccupied(Ship ship) {
        for (Field field: ship.getAllFields()) {
            boardFieldsOccupationFlags[field.getX()][field.getY()] = true;
        }
    }
}
