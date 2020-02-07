package com.chrosciu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

public class Shooter {

    @Getter
    @EqualsAndHashCode
    static class FieldWithHitMark {
        private final Field field;
        private boolean hit;

        FieldWithHitMark(Field field) {
            this.field = field;
            this.hit = false;
        }

        void markAsHit() {
            this.hit = true;
        }
    }

    @Value
    static class ShipAsFields {
        List<FieldWithHitMark> fields;

        ShipAsFields() {
            fields = new ArrayList<>();
        }

        void addField(FieldWithHitMark field) {
            fields.add(field);
        }
    }

    private List<ShipAsFields> shipsAsFields = new ArrayList<>();

    /**
     * Initialize shooter with given list of ships on board
     *
     * @param input - list of ships. Each ship is described by first field coordinate, length and orientation
     */
    public Shooter(List<Ship> input) {
        for (int i = 0; i < input.size(); ++i) {
            ShipAsFields shipAsFields = new ShipAsFields();
            for (int j = 0; j < input.get(i).getLength(); ++j) {
                if (input.get(i).isVertical()) {
                    shipAsFields.addField(new FieldWithHitMark(new Field(input.get(i).getFirstField().getX(), input.get(i).getFirstField().getY() + j)));
                } else {
                    shipAsFields.addField(new FieldWithHitMark(new Field(input.get(i).getFirstField().getX() + j, input.get(i).getFirstField().getY())));
                }
            }
            shipsAsFields.add(shipAsFields);
        }
    }

    /**
     * Take shot for given field and return shot result
     *
     * @param field - field coordinates
     * @return - shot result: 0 - no hit, 1 - ship hit, 2 - ship sunk, 3 - all ships sunk
     */
    public int shoot(Field field) {
        int rv = 0;
        //iterate through all ships
        for (int i = 0; i < shipsAsFields.size() && 0 == rv; ++i) {
            //iterate through all ship fields
            for (int j = 0; j < shipsAsFields.get(i).getFields().size() && 0 == rv; ++j) {
                //if any of ship fields is equal to passed field - mark as hit
                if (shipsAsFields.get(i).getFields().get(j).getField().equals(field)) {
                    shipsAsFields.get(i).getFields().get(j).markAsHit();
                    rv = 1;
                }
            }
            //if ship is hit - check if it is sunk
            if (1 == rv) {
                //iterate through all fields and check if they are all hit
                boolean a = true;
                for (int j = 0; j < shipsAsFields.get(i).getFields().size() && a; ++j) {
                    a &= shipsAsFields.get(i).getFields().get(j).isHit();
                }
                if (a) {
                    rv = 2;
                }
            }
        }
        //check if all ships are sunk
        boolean a = true;
        for (int i = 0; i < shipsAsFields.size() && a; ++i) {
            for (int j = 0; j < shipsAsFields.get(i).getFields().size() && a; ++j) {
                a &= shipsAsFields.get(i).getFields().get(j).isHit();
            }
        }
        if (a) {
            rv = 3;
        }
        return rv;
    }
}
