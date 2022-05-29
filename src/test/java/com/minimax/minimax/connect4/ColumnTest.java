package com.minimax.minimax.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    Column column;
    Position position;

    @BeforeEach
    void setUp() {
        var table = new Table(2,2);
        column = new Column(1, table);

    }

    @Test
    void isFull() {
        assertFalse(column.isFull());
    }

    @Test
    void put() {
        column.put(Position.STATE.PLAYER_1);
        assertTrue(column.getList().get(0).isPlayer1());
        assertTrue(column.getList().get(1).isEmpty());
    }

    @Test
    void cant_put_and_throws_ColumnFullException() {
        column.put(Position.STATE.PLAYER_1).put(Position.STATE.PLAYER_1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> column.put(Position.STATE.PLAYER_2));
        assertEquals("Column is full", exception.getMessage());
    }

    @Test
    void getList() {
        assertEquals(2, column.getList().size());
    }


}