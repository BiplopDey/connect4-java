package com.minimax.minimax.connect4.domain;

import com.minimax.minimax.connect4.domain.Position;
import com.minimax.minimax.connect4.domain.PositionPair;
import com.minimax.minimax.connect4.domain.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionPairTest {

    Table table = new Table(6,7);
    Position position1 = table.getPosition(0,0);
    Position position2 = table.getPosition(0,1);

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