package com.chrosciu.battleships.shooter;

import com.chrosciu.battleships.model.Field;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
