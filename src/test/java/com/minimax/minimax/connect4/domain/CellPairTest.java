package com.minimax.minimax.connect4.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellPairTest {

    Table table = new Table(6,7);
    Cell cell1 = table.getCell(0,0);
    Cell cell2 = table.getCell(0,1);

    @Test
    void positionPair_equals_true() {
        var positionPair = new CellPair(cell1, cell2);
        var positionPair2 = new CellPair(cell1, cell2);
        assertEquals(positionPair, positionPair2);
    }

    //test toString
    @Test
    void toString_returns_correct_string() {
        var positionPair = new CellPair(cell1, cell2);
        assertEquals("[Position(0,0), Position(0,1)]", positionPair.toString());
    }

}