package com.chrosciu;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.chrosciu.Direction.HORIZONTAL;
import static com.chrosciu.Direction.VERTICAL;
import static com.chrosciu.Result.FINISHED;
import static com.chrosciu.Result.HIT;
import static com.chrosciu.Result.MISSED;
import static com.chrosciu.Result.SUNK;
import static org.junit.Assert.assertEquals;

public class BoardShooterTest {
    private static final Field VERTICAL_TWO_FIELDS_SHIP_FIRST_FIELD = new Field(3, 4);
    private static final Field VERTICAL_TWO_FIELDS_SHIP_SECOND_FIELD = new Field(3, 5);

    private static final Field ONE_FIELD_SHIP_FIELD = new Field(7, 2);

    private static final Field ANOTHER_ONE_FIELD_SHIP_FIELD = new Field(6, 5);

    private static final Field HORIZONTAL_TWO_FIELDS_SHIP_FIRST_FIELD = new Field(1, 1);
    private static final Field HORIZONTAL_TWO_FIELDS_SHIP_SECOND_FIELD = new Field(2, 1);

    private static final Field FIELD_WITHOUT_SHIP = new Field(1, 2);
    private static final Field ANOTHER_FIELD_WITHOUT_SHIP = new Field(4, 4);

    private static final int ONE_FIELD_SHIP_SIZE = 1;
    private static final int TWO_FIELDS_SHIP_SIZE = 2;

    private static final Ship VERTICAL_TWO_FIELDS_SHIP =
            new Ship(VERTICAL_TWO_FIELDS_SHIP_FIRST_FIELD, TWO_FIELDS_SHIP_SIZE, VERTICAL);
    private static final Ship ONE_FIELD_SHIP =
            new Ship(ONE_FIELD_SHIP_FIELD, ONE_FIELD_SHIP_SIZE, HORIZONTAL);
    private static final Ship ANOTHER_ONE_FIELD_SHIP =
            new Ship(ANOTHER_ONE_FIELD_SHIP_FIELD, ONE_FIELD_SHIP_SIZE, VERTICAL);
    private static final Ship HORIZONTAL_TWO_FIELDS_SHIP =
            new Ship(HORIZONTAL_TWO_FIELDS_SHIP_FIRST_FIELD, TWO_FIELDS_SHIP_SIZE, HORIZONTAL);
    private static final List<Ship> SHIPS =
            Arrays.asList(VERTICAL_TWO_FIELDS_SHIP, ONE_FIELD_SHIP, ANOTHER_ONE_FIELD_SHIP, HORIZONTAL_TWO_FIELDS_SHIP);
    @Test
    public void shouldProperlyCalculateShotResultForGivenSetofShipsOnBoard() {
        //given
        BoardShooter boardShooter = new BoardShooter(SHIPS);

        //when
        Result result = boardShooter.takeShot(FIELD_WITHOUT_SHIP);

        //then
        assertEquals(MISSED, result);

        //when
        result = boardShooter.takeShot(VERTICAL_TWO_FIELDS_SHIP_FIRST_FIELD);

        //then
        assertEquals(HIT, result);

        //when
        result = boardShooter.takeShot(ONE_FIELD_SHIP_FIELD);

        //then
        assertEquals(SUNK, result);

        //when
        result = boardShooter.takeShot(VERTICAL_TWO_FIELDS_SHIP_FIRST_FIELD);

        //then
        assertEquals(HIT, result);

        //when
        result = boardShooter.takeShot(VERTICAL_TWO_FIELDS_SHIP_SECOND_FIELD);

        //then
        assertEquals(SUNK, result);

        //when
        result = boardShooter.takeShot(VERTICAL_TWO_FIELDS_SHIP_SECOND_FIELD);

        //then
        assertEquals(SUNK, result);

        //when
        result = boardShooter.takeShot(ANOTHER_ONE_FIELD_SHIP_FIELD);

        //then
        assertEquals(SUNK, result);

        //when
        result = boardShooter.takeShot(HORIZONTAL_TWO_FIELDS_SHIP_FIRST_FIELD);

        //then
        assertEquals(HIT, result);

        //when
        result = boardShooter.takeShot(HORIZONTAL_TWO_FIELDS_SHIP_SECOND_FIELD);

        //then
        assertEquals(FINISHED, result);

        //when
        result = boardShooter.takeShot(HORIZONTAL_TWO_FIELDS_SHIP_FIRST_FIELD);

        //then
        assertEquals(FINISHED, result);

        //when
        result = boardShooter.takeShot(ANOTHER_FIELD_WITHOUT_SHIP);

        //then
        assertEquals(FINISHED, result);
    }

}
