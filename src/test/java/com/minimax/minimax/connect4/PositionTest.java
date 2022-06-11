package com.minimax.minimax.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    Position position;
    Table table6x7;
    @BeforeEach
    void setUp() {
        table6x7 = new Table(6, 7);
    }

    @Test
    void position_equals_true() {
        position = new Position(table6x7).of(0,0);
        Position position2 = new Position(table6x7).of(0,0);

        assertEquals(position, position2);
    }

    @Test
    void invalid_position_and_throws_InvalidPositionException() {
         Exception sut = assertThrows(Position.InvalidPositionException.class,
                () -> new Position(table6x7).of(6,0));

         assertEquals("Illegal position: 6,0", sut.getMessage());
    }

    @Test
    void place_token_in_table(){
        position = new Position(table6x7).of(0,0);

        position.placePlayer1();

        assertTrue(position.isPlayer1());
    }

    @Test
    void isEmpty_returns_true(){
        position = new Position(table6x7).of(0,0);

        assertTrue(position.isEmpty());
    }

    @Test
    void toString_returns_correct_string() {
        position = new Position(table6x7).of(0, 0);

        assertEquals("Position(0,0)", position.toString());
    }

}