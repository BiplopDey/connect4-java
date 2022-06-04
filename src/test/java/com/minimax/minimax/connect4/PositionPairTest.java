package com.minimax.minimax.connect4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionPairTest {

    Table table = new Table(6,7);
    Position position = new Position(table);
    Position position1 = position.of(0,0);
    Position position2 = position.of(0,1);

    @Test
    void positionPair_equals_true() {
        var positionPair = new PositionPair(position1, position2);
        var positionPair2 = new PositionPair(position1, position2);
        assertEquals(positionPair, positionPair2);
    }

    //test toString
    @Test
    void toString_returns_correct_string() {
        var positionPair = new PositionPair(position1, position2);
        assertEquals("[Position(0,0), Position(0,1)]", positionPair.toString());
    }

}