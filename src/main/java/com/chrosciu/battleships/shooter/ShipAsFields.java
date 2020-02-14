package com.chrosciu.battleships.shooter;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
        boolean allFieldsInShipHit = true;
        for (FieldWithHitMark fieldWithHitMark: fields) {
            allFieldsInShipHit &= fieldWithHitMark.isHit();
            if (!allFieldsInShipHit) {
                break;
            }
        }
        return allFieldsInShipHit;
    }
}
