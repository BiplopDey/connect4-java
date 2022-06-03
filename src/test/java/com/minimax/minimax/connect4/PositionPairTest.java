package com.minimax.minimax.connect4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionPairTest {

    @Test
    void positionPair_equals_true() {
        var table = new Table(6,7);
        var position1 = new Position(0,0, table);
        var position2 = new Position(0,1, table);

        var positionPair = new PositionPair(position1, position2);
        var positionPair2 = new PositionPair(position1, position2);
        assertEquals(positionPair, positionPair2);
    }

    //test toString
    @Test
    void toString_returns_correct_string() {
        var table = new Table(6,7);
        var position1 = new Position(0,0, table);
        var position2 = new Position(0,1, table);

        var positionPair = new PositionPair(position1, position2);
        assertEquals("[Position(0,0), Position(0,1)]", positionPair.toString());
    }

}