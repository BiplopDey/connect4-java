package com.minimax.minimax.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    Column column;
    Table table;
    @BeforeEach
    void setUp() {
        table = new Table(2,2);
        column = new Column(1, table);

    }

    @Test
    void isNotFull_byDefault() {
        assertFalse(column.isFull());
    }

    @Test
    void column_list() {
        var list = List.of(new Position(table).of(0,1),
                           new Position(table).of(1,1));
        assertEquals(list, column.getPositions());
    }

    @Test
    void put() {
        column.put(Position.STATE.PLAYER_1);
        System.out.println(column.getPositions());
        assertTrue(column.getPositions().get(0).isPlayer1());
        assertTrue(column.getPositions().get(1).isEmpty());
    }

    @Test
    void cant_put_and_throws_ColumnFullException() {
        column.put(Position.STATE.PLAYER_1).put(Position.STATE.PLAYER_1);
        Exception exception = assertThrows(Column.ColumnFullException.class,
                () -> column.put(Position.STATE.PLAYER_2));
        assertEquals("Column is full", exception.getMessage());
    }

    @Test
    void getList() {
        assertEquals(2, column.getPositions().size());
    }

    @Test
    void isConnect4_false_by_default() {
        assertFalse(column.isConnect4());
    }

    @Test
    void isConnect4_false(){
        var table = new Table(6,7);
        var column = new Column(1, table);
        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_2);
        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_1);
        assertFalse(column.isConnect4());
    }

    @Test
    void get_positionPair_of_connect_4() {
        var table = new Table(6, 7);
        var column = new Column(1, table);

        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_2);
        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_1);

        var positionPair = new PositionPair(table.get(2,1),
                table.get(5,1));
        assertEquals(positionPair, column.getPositionPair());
    }

    @Test
    void cant_get_positionPair_of_connect_4_and_throws_IllegalStateException() {
        var table = new Table(6, 7);
        var column = new Column(1, table);

        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_2);
        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_1);
        column.put(Position.STATE.PLAYER_1);

        Exception exception = assertThrows(IllegalStateException.class,
                () -> column.getPositionPair());
        assertEquals("Column is not connect4", exception.getMessage());
    }
}