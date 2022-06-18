package com.minimax.minimax.connect4.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    Cell cell;
    Table table6x7;
    @BeforeEach
    void setUp() {
        table6x7 = new Table(6, 7);
    }

    @Test
    void position_equals_true() {
        cell = new Cell(table6x7).of(0,0);
        Cell cell2 = new Cell(table6x7).of(0,0);

        assertEquals(cell, cell2);
    }

    @Test
    void invalid_position_and_throws_InvalidPositionException() {
         Exception sut = assertThrows(Cell.InvalidPositionException.class,
                () -> new Cell(table6x7).of(6,0));

         assertEquals("Illegal position: 6,0", sut.getMessage());
    }

    @Test
    void place_player1(){
        cell = new Cell(table6x7).of(0,0);

        cell.placePlayer1();

        assertTrue(cell.isPlayer1());
    }

    @Test
    void place_player2(){
        cell = new Cell(table6x7).of(0,0);

        cell.placePlayer2();

        assertTrue(cell.isPlayer2());
    }

    @Test
    void isEmpty_returns_true(){
        cell = new Cell(table6x7).of(0,0);

        assertTrue(cell.isEmpty());
    }

    @Test
    void toString_returns_correct_string() {
        cell = new Cell(table6x7).of(0, 0);

        assertEquals("Position(0,0)", cell.toString());
    }

}