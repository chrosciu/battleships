package com.chrosciu;


import org.apache.commons.lang3.tuple.Triple;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PutterTest {

    private static final List<Integer> SHIPS_SIZES = Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);
    private static final int BOARD_SIZE = 10;

    @Test
    public void shouldPutShipsWithGivenSizesOnBoardWithoutCollision() {
        //given
        List<Triple<Field, Integer, Boolean>> ships = new ArrayList<>();

        //when
        Putter.put(SHIPS_SIZES, BOARD_SIZE, ships);

        //then
        assertAllShipsHaveRequestedSizes(ships, SHIPS_SIZES);
        assertAllShipsNotOutsideBoard(ships, BOARD_SIZE);
        assertNoCollisionBetweenShips(ships);
    }

    private void assertAllShipsHaveRequestedSizes(List<Triple<Field, Integer, Boolean>> ships, List<Integer> shipsSizes) {
        List<Integer> orderedShipsSizes = shipsSizes.stream().sorted().collect(Collectors.toList());
        List<Integer> orderedActualShipSizes = ships.stream().map(Triple::getMiddle).sorted().collect(Collectors.toList());
        Assert.assertEquals(orderedActualShipSizes, orderedShipsSizes);
    }

    private void assertAllShipsNotOutsideBoard(List<Triple<Field, Integer, Boolean>> ships, int boardSize) {
        for(Triple<Field, Integer, Boolean> ship : ships) {
            assertShipNotOutsideBoard(ship, boardSize);
        }
    }

    private void assertShipNotOutsideBoard(Triple<Field, Integer, Boolean> ship, int boardSize) {
        List<Field> allShipFields = getAllFieldsForShip(ship);
        for (Field shipField: allShipFields) {
            assertFieldNotOutsideBoard(shipField, boardSize);
        }
    }

    private void assertFieldNotOutsideBoard(Field point, int boardSize) {
        int pointHorizontalCoordinate = point.getX();
        int pointVerticalCoordinate = point.getY();
        Assert.assertTrue(pointVerticalCoordinate >= 0 && pointVerticalCoordinate < boardSize);
        Assert.assertTrue(pointHorizontalCoordinate >= 0 && pointHorizontalCoordinate < boardSize);
    }

    private void assertNoCollisionBetweenShips(List<Triple<Field, Integer, Boolean>> ships) {
        int shipsCount = ships.size();
        for (int i = 0; i < shipsCount; ++i) {
            Triple<Field, Integer, Boolean> firstShip = ships.get(i);
            List<Field> firstShipFieldsWithBorder = getAllFieldsForShipWithBorder(firstShip);
            for (int j = i + 1; j < shipsCount; ++j) {
                Triple<Field, Integer, Boolean> secondShip = ships.get(j);
                List<Field> secondShipFields = getAllFieldsForShip(secondShip);
                secondShipFields.retainAll(firstShipFieldsWithBorder);
                Assert.assertTrue(secondShipFields.isEmpty());
            }
        }
    }

    private List<Field> getAllFieldsForShip(Triple<Field, Integer, Boolean> ship) {
        Field firstField = ship.getLeft();
        int shipLength = ship.getMiddle();
        boolean vertical = ship.getRight();
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = 0; fieldIndex < shipLength; ++fieldIndex) {
            if (vertical) {
                fields.add(new Field(firstField.getX(), firstField.getY() + fieldIndex));
            } else {
                fields.add(new Field(firstField.getX() + fieldIndex, firstField.getY()));
            }
        }
        return fields;
    }

    private List<Field> getAllFieldsForShipWithBorder(Triple<Field, Integer, Boolean> ship) {
        Field firstField = ship.getLeft();
        int shipLength = ship.getMiddle();
        boolean vertical = ship.getRight();
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = -1; fieldIndex <= shipLength; ++fieldIndex) {
            if (vertical) {
                fields.add(new Field(firstField.getX() - 1, firstField.getY() + fieldIndex));
                fields.add(new Field(firstField.getX(), firstField.getY() + fieldIndex));
                fields.add(new Field(firstField.getX() + 1, firstField.getY() + fieldIndex));
            } else {
                fields.add(new Field(firstField.getX() + fieldIndex, firstField.getY() - 1));
                fields.add(new Field(firstField.getX() + fieldIndex, firstField.getY()));
                fields.add(new Field(firstField.getX() + fieldIndex, firstField.getY() + 1));
            }
        }
        return fields;
    }

}
