package com.chrosciu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

import static com.chrosciu.Result.FINISHED;
import static com.chrosciu.Result.HIT;
import static com.chrosciu.Result.MISSED;
import static com.chrosciu.Result.SUNK;

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

    public Result shoot(Field field) {
        Result result = MISSED;
        //iterate through all ships
        for (int i = 0; i < shipsAsFields.size() && MISSED == result; ++i) {
            //if any of ship fields is equal to passed field - mark as hit
            for (int j = 0; j < shipsAsFields.get(i).getFields().size() && MISSED == result; ++j) {
                //if any of ship fields is equal to passed field - mark as hit
                if (shipsAsFields.get(i).getFields().get(j).getField().equals(field)) {
                    shipsAsFields.get(i).getFields().get(j).markAsHit();
                    result = HIT;
                }
            }
            //if ship is hit - check if it is sunk
            if (HIT == result) {
                //iterate through all fields and check if they are all hit
                boolean a = true;
                for (int j = 0; j < shipsAsFields.get(i).getFields().size() && a; ++j) {
                    a &= shipsAsFields.get(i).getFields().get(j).isHit();
                }
                if (a) {
                    result = SUNK;
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
            result = FINISHED;
        }
        return result;
    }
}
