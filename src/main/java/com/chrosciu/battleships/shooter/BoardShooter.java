package com.chrosciu.battleships.shooter;

import com.chrosciu.battleships.model.Field;
import com.chrosciu.battleships.model.Result;
import com.chrosciu.battleships.model.Ship;

import java.util.List;
import java.util.stream.Collectors;

import static com.chrosciu.battleships.model.Result.FINISHED;
import static com.chrosciu.battleships.model.Result.MISSED;

public class BoardShooter {
    private final List<ShipAsFields> shipsAsFields;
    private boolean finished;

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
        if (finished) {
            return FINISHED;
        }
        Result result = MISSED;
        for (ShipAsFields shipAsFields: shipsAsFields) {
            result = shipAsFields.takeShot(field);
            if (result != MISSED) {
                break;
            }
        }
        if (allFieldsInAllShipsHit()) {
            finished = true;
            result = FINISHED;
        }
        return result;
    }

    private boolean allFieldsInAllShipsHit() {
        return shipsAsFields.stream().allMatch(ShipAsFields::allFieldsHit);
    }
}
