package com.chrosciu;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.chrosciu.Result.FINISHED;
import static com.chrosciu.Result.HIT;
import static com.chrosciu.Result.MISSED;
import static com.chrosciu.Result.SUNK;

@Getter
@EqualsAndHashCode
class FieldWithHitMark {
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

@Getter
class ShipAsFields {
    private final List<FieldWithHitMark> fields;

    ShipAsFields() {
        fields = new ArrayList<>();
    }

    void addField(FieldWithHitMark field) {
        fields.add(field);
    }
}

public class Shooter {
    private List<ShipAsFields> shipsAsFields = new ArrayList<>();

    public Shooter(List<Ship> ships) {
        for (Ship ship: ships) {
            ShipAsFields shipAsFields = new ShipAsFields();
            for (int j = 0; j < ship.getLength(); ++j) {
                if (Direction.VERTICAL == ship.getDirection()) {
                    shipAsFields.addField(new FieldWithHitMark(new Field(ship.getFirstField().getX(), ship.getFirstField().getY() + j)));
                } else {
                    shipAsFields.addField(new FieldWithHitMark(new Field(ship.getFirstField().getX() + j, ship.getFirstField().getY())));
                }
            }
            shipsAsFields.add(shipAsFields);
        }
    }

    public Result takeShot(Field field) {
        Result result = MISSED;
        for (ShipAsFields shipAsFields: shipsAsFields) {
            for (FieldWithHitMark fieldWithHitMark: shipAsFields.getFields()) {
                if (fieldWithHitMark.getField().equals(field)) {
                    fieldWithHitMark.markAsHit();
                    result = HIT;
                }
                if (result != MISSED) {
                    break;
                }
            }
            if (HIT == result) {
                if (allFieldsInShipHit(shipAsFields)) {
                    result = SUNK;
                }
            }
            if (result != MISSED) {
                break;
            }
        }
        if (allFieldsInAllShipsHit()) {
            result = FINISHED;
        }
        return result;
    }

    private boolean allFieldsInShipHit(ShipAsFields shipAsFields) {
        boolean allFieldsInShipHit = true;
        for (FieldWithHitMark fieldWithHitMark: shipAsFields.getFields()) {
            allFieldsInShipHit &= fieldWithHitMark.isHit();
            if (!allFieldsInShipHit) {
                break;
            }
        }
        return allFieldsInShipHit;
    }

    private boolean allFieldsInAllShipsHit() {
        boolean allFieldsInAllShipsHit = true;
        for (ShipAsFields shipAsFields: shipsAsFields) {
            for (FieldWithHitMark fieldWithHitMark: shipAsFields.getFields()) {
                allFieldsInAllShipsHit &= fieldWithHitMark.isHit();
                if (!allFieldsInAllShipsHit) {
                    break;
                }
            }
            if (!allFieldsInAllShipsHit) {
                break;
            }
        }
        return allFieldsInAllShipsHit;
    }
}
