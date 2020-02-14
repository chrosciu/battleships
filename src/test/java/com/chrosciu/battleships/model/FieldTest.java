package com.chrosciu.battleships.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldTest {
    private static final int SOME_VERTICAL_COORDINATE = 4;
    private static final int SOME_HORIZONTAL_COORDINATE = 5;

    private static final int SOME_BOARD_SIZE = 6;
    private static final int SOME_OTHER_BOARD_SIZE = 2;

    private Field field;

    @Before
    public void setup() {
        field = new Field(SOME_VERTICAL_COORDINATE, SOME_HORIZONTAL_COORDINATE);
    }

    @Test
    public void shouldReturnProperVerticalCoordinate() {
        //when
        int x = field.getX();

        //then
        Assert.assertEquals(SOME_VERTICAL_COORDINATE, x);
    }

    @Test
    public void shouldReturnProperHorizontalCoordinate() {
        //when
        int y = field.getY();

        //then
        Assert.assertEquals(SOME_HORIZONTAL_COORDINATE, y);
    }

    @Test
    public void twoFieldsWithTheSameCoordinatesShouldBeEqual() {
        //given
        Field fieldWithTheSameCoordinates = new Field(SOME_VERTICAL_COORDINATE, SOME_HORIZONTAL_COORDINATE);

        //then
        Assert.assertEquals(field, fieldWithTheSameCoordinates);
    }

    @Test
    public void shouldReturnTrueIfFieldIsWithinGivenBoard() {
        //then
        Assert.assertTrue(field.isOnBoard(SOME_BOARD_SIZE));
    }

    @Test
    public void shouldReturnFalseIfFieldIsNotWithinGivenBoard() {
        //then
        Assert.assertFalse(field.isOnBoard(SOME_OTHER_BOARD_SIZE));
    }

    @Test
    public void shouldTreatFieldWithNegativeCoordinateAsNotWithinGivenBoard() {
        //given
        Field fieldWithNegativeCoordinate = new Field(-1, 2);

        //then
        Assert.assertFalse(fieldWithNegativeCoordinate.isOnBoard(SOME_BOARD_SIZE));
    }

}
