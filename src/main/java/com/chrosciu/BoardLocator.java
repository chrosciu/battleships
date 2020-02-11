package com.chrosciu;

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

    private Field getRandomFirstFieldForShip(int shipSize, Direction direction) {
        int constantCoordinate = new Random().nextInt(boardSize);
        int changeableCoordinate = new Random().nextInt(boardSize - shipSize);
        return direction.getFieldCreator().apply(constantCoordinate, changeableCoordinate);
    }

    private boolean isCollisionDetectedForShip(Ship ship) {
        return ship.getAllFieldsWithBorder().stream()
                .filter(field -> field.isOnBoard(boardSize))
                .anyMatch(field -> boardFieldsOccupationFlags[field.getX()][field.getY()]);
    }

    private void markShipFieldsAsOccupied(Ship ship) {
        for (Field field: ship.getAllFields()) {
            boardFieldsOccupationFlags[field.getX()][field.getY()] = true;
        }
    }
}
