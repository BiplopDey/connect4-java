package com.minimax.minimax.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    private Column column_size_2;
    private Column column_size_6;
    private Table table2x2;
    private Table table6x7;
    private final Position.STATE PLAYER_1 = Position.STATE.PLAYER_1;
    private final Position.STATE PLAYER_2 = Position.STATE.PLAYER_2;

    @BeforeEach
    void setUp() {
        table2x2 = new Table(2,2);
        column_size_2 = new Column(1, table2x2);

        table6x7 = new Table(6,7);
        column_size_6 = new Column(1, table6x7);
    }

    @Test
    void isNotFull_byDefault() {
        assertFalse(column_size_2.isFull());
    }

    @Test
    void column_list() {
        var sut = List.of(new Position(table2x2).of(0,1),
                           new Position(table2x2).of(1,1));

        assertEquals(sut, column_size_2.getPositions());
    }

    @Test
    void put() {
        column_size_2.put(PLAYER_1);

        assertTrue(column_size_2.getPositions().get(0).isPlayer1());
        assertTrue(column_size_2.getPositions().get(1).isEmpty());
    }

    @Test
    void cant_put_and_throws_ColumnFullException() {
        put(column_size_2, PLAYER_1, PLAYER_1);

        Exception sut = assertThrows(Column.ColumnFullException.class,
                () -> column_size_2.put(PLAYER_2));

        assertEquals("Column 1 is full", sut.getMessage());
    }

    @Test
    void getList() {
        assertEquals(2, column_size_2.getPositions().size());
    }

    @Test
    void isConnect4_false_by_default() {
        assertFalse(column_size_2.isConnect4());
    }

    @Test
    void isConnect4_false(){
        put(column_size_6, PLAYER_1, PLAYER_1, PLAYER_2, PLAYER_1, PLAYER_1);

        assertFalse(column_size_6.isConnect4());
    }

    @Test
    void get_positionPair_of_connect_4() {
        put(column_size_6, PLAYER_1, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);

        var positionPair = new PositionPair(
                table6x7.getPosition(2,1),
                table6x7.getPosition(5,1));
        assertEquals(positionPair, column_size_6.getPositionPair());
    }

    @Test
    void cant_get_positionPair_of_connect_4_and_throws_IllegalStateException() {
        put(column_size_6, PLAYER_1, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1);

        Exception sut = assertThrows(IllegalStateException.class,
                () -> column_size_6.getPositionPair());

        assertEquals("Column is not connect4", sut.getMessage());
    }

    private void put(Column column, Position.STATE... players) {
        for (Position.STATE player: players)
            column.put(player);
    }
}