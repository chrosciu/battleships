package com.chrosciu.battleships.shooter;

import com.chrosciu.battleships.model.Field;
import com.chrosciu.battleships.model.Result;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.chrosciu.battleships.model.Result.HIT;
import static com.chrosciu.battleships.model.Result.MISSED;
import static com.chrosciu.battleships.model.Result.SUNK;

@Getter
class ShipAsFields {
    private final List<FieldWithHitMark> fields;

    ShipAsFields() {
        fields = new ArrayList<>();
    }

    void addField(FieldWithHitMark field) {
        fields.add(field);
    }

    public boolean allFieldsHit() {
        return fields.stream().allMatch(FieldWithHitMark::isHit);
    }

    public Result takeShot(Field field) {
        Result result = MISSED;
        for (FieldWithHitMark fieldWithHitMark: fields) {
            if (fieldWithHitMark.getField().equals(field)) {
                fieldWithHitMark.markAsHit();
                result = HIT;
            }
            if (result != MISSED) {
                break;
            }
        }
        if (HIT == result) {
            if (allFieldsHit()) {
                result = SUNK;
            }
        }
        return result;
    }
}
