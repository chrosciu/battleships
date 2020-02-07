package com.chrosciu;

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
        List<Ship> ships = new ArrayList<>();

        //when
        Putter.put(SHIPS_SIZES, BOARD_SIZE, ships);

        //then
        assertAllShipsHaveRequestedSizes(ships, SHIPS_SIZES);
        assertAllShipsNotOutsideBoard(ships, BOARD_SIZE);
        assertNoCollisionBetweenShips(ships);
    }

    private void assertAllShipsHaveRequestedSizes(List<Ship> ships, List<Integer> shipsSizes) {
        List<Integer> orderedShipsSizes = shipsSizes.stream().sorted().collect(Collectors.toList());
        List<Integer> orderedActualShipSizes = ships.stream().map(Ship::getLength).sorted().collect(Collectors.toList());
        Assert.assertEquals(orderedActualShipSizes, orderedShipsSizes);
    }

    private void assertAllShipsNotOutsideBoard(List<Ship> ships, int boardSize) {
        for(Ship ship : ships) {
            assertShipNotOutsideBoard(ship, boardSize);
        }
    }

    private void assertShipNotOutsideBoard(Ship ship, int boardSize) {
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

    private void assertNoCollisionBetweenShips(List<Ship> ships) {
        int shipsCount = ships.size();
        for (int i = 0; i < shipsCount; ++i) {
            Ship firstShip = ships.get(i);
            List<Field> firstShipFieldsWithBorder = getAllFieldsForShipWithBorder(firstShip);
            for (int j = i + 1; j < shipsCount; ++j) {
                Ship secondShip = ships.get(j);
                List<Field> secondShipFields = getAllFieldsForShip(secondShip);
                secondShipFields.retainAll(firstShipFieldsWithBorder);
                Assert.assertTrue(secondShipFields.isEmpty());
            }
        }
    }

    private List<Field> getAllFieldsForShip(Ship ship) {
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = 0; fieldIndex < ship.getLength(); ++fieldIndex) {
            if (ship.isVertical()) {
                fields.add(new Field(ship.getFirstField().getX(), ship.getFirstField().getY() + fieldIndex));
            } else {
                fields.add(new Field(ship.getFirstField().getX() + fieldIndex, ship.getFirstField().getY()));
            }
        }
        return fields;
    }

    private List<Field> getAllFieldsForShipWithBorder(Ship ship) {
        List<Field> fields = new ArrayList<>();
        for (int fieldIndex = -1; fieldIndex <= ship.getLength(); ++fieldIndex) {
            if (ship.isVertical()) {
                fields.add(new Field(ship.getFirstField().getX() - 1, ship.getFirstField().getY() + fieldIndex));
                fields.add(new Field(ship.getFirstField().getX(), ship.getFirstField().getY() + fieldIndex));
                fields.add(new Field(ship.getFirstField().getX() + 1, ship.getFirstField().getY() + fieldIndex));
            } else {
                fields.add(new Field(ship.getFirstField().getX() + fieldIndex, ship.getFirstField().getY() - 1));
                fields.add(new Field(ship.getFirstField().getX() + fieldIndex, ship.getFirstField().getY()));
                fields.add(new Field(ship.getFirstField().getX() + fieldIndex, ship.getFirstField().getY() + 1));
            }
        }
        return fields;
    }

}
