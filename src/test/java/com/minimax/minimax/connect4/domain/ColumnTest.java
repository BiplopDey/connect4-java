package com.minimax.minimax.connect4.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    private Column column_size_2;
    private Column column_size_6;
    private Table table2x2;
    private Table table6x7;
    private final Player PLAYER_1 = Player.PLAYER_1;
    private final Player PLAYER_2 = Player.PLAYER_2;

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
        assertEquals(0, column_size_2.size());
    }

    @Test
    void put() {
        column_size_2.put(PLAYER_1);
        assertEquals(1, column_size_2.size());
        assertTrue(column_size_2.getCells().get(0).isPlayer1());
    }

    @Test
    void cant_put_and_throws_ColumnFullException() {
        put(column_size_2, PLAYER_1, PLAYER_1);

        Exception sut = assertThrows(ColumnFullException.class,
                () -> column_size_2.put(PLAYER_2));

        assertEquals("Column 1 is full", sut.getMessage());
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

        var positionPair = new CellPair(
                table6x7.getCell(2,1),
                table6x7.getCell(5,1));
        assertEquals(positionPair, column_size_6.getCellPair());
    }

    @Test
    void cant_get_positionPair_of_connect_4_and_throws_IllegalStateException() {
        put(column_size_6, PLAYER_1, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1);

        Exception sut = assertThrows(IllegalStateException.class,
                () -> column_size_6.getCellPair());

        assertEquals("Column is not connect4", sut.getMessage());
    }

    private void put(Column column, Player... players) {
        for (Player player: players)
            column.put(player);
    }


}
