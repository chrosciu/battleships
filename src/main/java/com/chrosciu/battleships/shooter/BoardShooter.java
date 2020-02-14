package com.chrosciu.battleships.shooter;

import com.chrosciu.battleships.model.Field;
import com.chrosciu.battleships.model.Result;
import com.chrosciu.battleships.model.Ship;

import java.util.List;
import java.util.stream.Collectors;

import static com.chrosciu.battleships.model.Result.FINISHED;
import static com.chrosciu.battleships.model.Result.HIT;
import static com.chrosciu.battleships.model.Result.MISSED;
import static com.chrosciu.battleships.model.Result.SUNK;

public class BoardShooter {
    private final List<ShipAsFields> shipsAsFields;

    public BoardShooter(List<Ship> ships) {
        shipsAsFields = ships.stream().map(this::convertShipToShipWithFieldsForm).collect(Collectors.toList());
    }

    private ShipAsFields convertShipToShipWithFieldsForm(Ship ship) {
        ShipAsFields shipAsFields = new ShipAsFields();
        for (Field field : ship.getAllFields()) {
            shipAsFields.addField(new FieldWithHitMark(field));
        }
        return shipAsFields;
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
                if (shipAsFields.allFieldsHit()) {
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

    private boolean allFieldsInAllShipsHit() {
        return shipsAsFields.stream().allMatch(ShipAsFields::allFieldsHit);
    }
}
