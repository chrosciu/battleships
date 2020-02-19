package com.chrosciu;

import com.chrosciu.Putter;
import org.apache.commons.lang3.tuple.Pair;
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
        List<Triple<Pair<Integer, Integer>, Integer, Boolean>> ships = new ArrayList<>();

        //when
        Putter.put(SHIPS_SIZES, BOARD_SIZE, ships);

        //then
        assertAllShipsHaveRequestedSizes(ships, SHIPS_SIZES);
        assertAllShipsNotOutsideBoard(ships, BOARD_SIZE);
        assertNoCollisionBetweenShips(ships);
    }

    private void assertAllShipsHaveRequestedSizes(List<Triple<Pair<Integer, Integer>, Integer, Boolean>> ships, List<Integer> shipsSizes) {
        List<Integer> orderedShipsSizes = shipsSizes.stream().sorted().collect(Collectors.toList());
        List<Integer> orderedActualShipSizes = ships.stream().map(Triple::getMiddle).sorted().collect(Collectors.toList());
        Assert.assertEquals(orderedActualShipSizes, orderedShipsSizes);
    }

    private void assertAllShipsNotOutsideBoard(List<Triple<Pair<Integer, Integer>, Integer, Boolean>> ships, int boardSize) {
        for(Triple<Pair<Integer, Integer>, Integer, Boolean> ship : ships) {
            assertShipNotOutsideBoard(ship, boardSize);
        }
    }

    private void assertShipNotOutsideBoard(Triple<Pair<Integer, Integer>, Integer, Boolean> ship, int boardSize) {
        List<Pair<Integer, Integer>> allShipFields = getAllFieldsForShip(ship);
        for (Pair<Integer, Integer> shipField: allShipFields) {
            assertPointNotOutsideBoard(shipField, boardSize);
        }
    }

    private void assertPointNotOutsideBoard(Pair<Integer, Integer> point, int boardSize) {
        int pointHorizontalCoordinate = point.getLeft();
        int pointVerticalCoordinate = point.getRight();
        Assert.assertTrue(pointVerticalCoordinate >= 0 && pointVerticalCoordinate < boardSize);
        Assert.assertTrue(pointHorizontalCoordinate >= 0 && pointHorizontalCoordinate < boardSize);
    }

    private void assertNoCollisionBetweenShips(List<Triple<Pair<Integer, Integer>, Integer, Boolean>> ships) {
        int shipsCount = ships.size();
        for (int i = 0; i < shipsCount; ++i) {
            Triple<Pair<Integer, Integer>, Integer, Boolean> firstShip = ships.get(i);
            List<Pair<Integer, Integer>> firstShipFieldsWithBorder = getAllFieldsForShipWithBorder(firstShip);
            for (int j = i + 1; j < shipsCount; ++j) {
                Triple<Pair<Integer, Integer>, Integer, Boolean> secondShip = ships.get(j);
                List<Pair<Integer, Integer>> secondShipFields = getAllFieldsForShip(secondShip);
                secondShipFields.retainAll(firstShipFieldsWithBorder);
                Assert.assertTrue(secondShipFields.isEmpty());
            }
        }
    }

    private List<Pair<Integer, Integer>> getAllFieldsForShip(Triple<Pair<Integer, Integer>, Integer, Boolean> ship) {
        Pair<Integer, Integer> firstField = ship.getLeft();
        int shipLength = ship.getMiddle();
        boolean vertical = ship.getRight();
        List<Pair<Integer, Integer>> fields = new ArrayList<>();
        for (int fieldIndex = 0; fieldIndex < shipLength; ++fieldIndex) {
            if (vertical) {
                fields.add(Pair.of(firstField.getLeft(), firstField.getRight() + fieldIndex));
            } else {
                fields.add(Pair.of(firstField.getLeft() + fieldIndex, firstField.getRight()));
            }
        }
        return fields;
    }

    private List<Pair<Integer, Integer>> getAllFieldsForShipWithBorder(Triple<Pair<Integer, Integer>, Integer, Boolean> ship) {
        Pair<Integer, Integer> firstField = ship.getLeft();
        int shipLength = ship.getMiddle();
        boolean vertical = ship.getRight();
        List<Pair<Integer, Integer>> fields = new ArrayList<>();
        for (int fieldIndex = -1; fieldIndex <= shipLength; ++fieldIndex) {
            if (vertical) {
                fields.add(Pair.of(firstField.getLeft() - 1, firstField.getRight() + fieldIndex));
                fields.add(Pair.of(firstField.getLeft(), firstField.getRight() + fieldIndex));
                fields.add(Pair.of(firstField.getLeft() + 1, firstField.getRight() + fieldIndex));
            } else {
                fields.add(Pair.of(firstField.getLeft() + fieldIndex, firstField.getRight() - 1));
                fields.add(Pair.of(firstField.getLeft() + fieldIndex, firstField.getRight()));
                fields.add(Pair.of(firstField.getLeft() + fieldIndex, firstField.getRight() + 1));
            }
        }
        return fields;
    }

}
