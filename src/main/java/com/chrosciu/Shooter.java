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

    public Shooter(List<Ship> ships) {
        for (int i = 0; i < ships.size(); ++i) {
            ShipAsFields shipAsFields = new ShipAsFields();
            for (int j = 0; j < ships.get(i).getLength(); ++j) {
                if (ships.get(i).isVertical()) {
                    shipAsFields.addField(new FieldWithHitMark(new Field(ships.get(i).getFirstField().getX(), ships.get(i).getFirstField().getY() + j)));
                } else {
                    shipAsFields.addField(new FieldWithHitMark(new Field(ships.get(i).getFirstField().getX() + j, ships.get(i).getFirstField().getY())));
                }
            }
            shipsAsFields.add(shipAsFields);
        }
    }

    public Result shoot(Field field) {
        Result result = MISSED;
        for (int i = 0; i < shipsAsFields.size() && MISSED == result; ++i) {
            for (int j = 0; j < shipsAsFields.get(i).getFields().size() && MISSED == result; ++j) {
                if (shipsAsFields.get(i).getFields().get(j).getField().equals(field)) {
                    shipsAsFields.get(i).getFields().get(j).markAsHit();
                    result = HIT;
                }
            }
            if (HIT == result) {
                boolean a = true;
                for (int j = 0; j < shipsAsFields.get(i).getFields().size() && a; ++j) {
                    a &= shipsAsFields.get(i).getFields().get(j).isHit();
                }
                if (a) {
                    result = SUNK;
                }
            }
        }
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
