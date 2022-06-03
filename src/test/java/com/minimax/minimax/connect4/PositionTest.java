package com.minimax.minimax.connect4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    Position position;
    //test position equals
    @Test
    void position_equals_true() {
        var table = new Table(6,7);
        position = new Position(0,0, table);
        Position position2 = new Position(0,0, table);
        assertEquals(position, position2);
    }

    //test toString
    @Test
void toString_returns_correct_string() {
        var table = new Table(6,7);
        position = new Position(0,0, table);
        assertEquals("Position(0,0)", position.toString());
    }

}