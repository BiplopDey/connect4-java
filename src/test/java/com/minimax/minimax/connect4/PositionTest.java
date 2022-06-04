package com.minimax.minimax.connect4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    Position position;
    //test position equals
    @Test
    void position_equals_true() {
        var table = new Table(6,7);
        position = new Position(table).of(0,0);
        Position position2 = new Position(table).of(0,0);
        assertEquals(position, position2);
    }

    @Test
    void invalid_position_and_throws_InvalidPositionException() {
        var table = new Table(6,7);
        Exception exception = assertThrows(Position.InvalidPositionException.class,
                () -> new Position(table).of(6,0));
        assertEquals("Illegal position: 6,0", exception.getMessage());
    }

    @Test
    void place_token_in_table(){
        var table = new Table(6,7);
        position = new Position(table).of(0,0);
        position.placePlayer1();
        assertTrue(position.isPlayer1());
    }

    @Test
    void isEmpty_returns_true(){
        var table = new Table(6,7);
        position = new Position(table).of(0,0);
        assertTrue(position.isEmpty());
    }

    //test toString
    @Test
    void toString_returns_correct_string() {
        var table = new Table(6, 7);
        position = new Position(table).of(0, 0) ;
        assertEquals("Position(0,0)", position.toString());
    }


}