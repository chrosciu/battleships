package com.chrosciu.battleships.locator;

import com.chrosciu.battleships.model.Field;
import com.chrosciu.battleships.model.Ship;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class BoardLocatorTest {

    private static final List<Integer> SHIPS_SIZES = Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);
    private static final int BOARD_SIZE = 10;

    @Test
    public void shouldLocateShipsWithGivenSizesOnBoardWithoutCollision() {
        //given
        BoardLocator boardLocator = new BoardLocator(BOARD_SIZE);
        //when
        List<Ship> ships = boardLocator.locateShipsWithGivenSizeOnBoard(SHIPS_SIZES);

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
        for (Ship ship : ships) {
            assertShipNotOutsideBoard(ship, boardSize);
        }
    }

    private void assertShipNotOutsideBoard(Ship ship, int boardSize) {
        List<Field> allShipFields = ship.getAllFields();
        for (Field shipField: allShipFields) {
            assertFieldNotOutsideBoard(shipField, boardSize);
        }
    }

    private void assertFieldNotOutsideBoard(Field field, int boardSize) {
        assertTrue(field.isOnBoard(boardSize));
    }

    private void assertNoCollisionBetweenShips(List<Ship> ships) {
        int shipsCount = ships.size();
        for (int i = 0; i < shipsCount; ++i) {
            Ship firstShip = ships.get(i);
            List<Field> firstShipFieldsWithBorder = firstShip.getAllFieldsWithBorder();
            for (int j = i + 1; j < shipsCount; ++j) {
                Ship secondShip = ships.get(j);
                List<Field> secondShipFields = secondShip.getAllFields();
                secondShipFields.retainAll(firstShipFieldsWithBorder);
                assertTrue(secondShipFields.isEmpty());
            }
        }
    }


}
