package com.minimax.minimax.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table table;
    Table smallTable;
    @BeforeEach
    void setUp() {
        table = new Table(6,7);
    }

    @Test
    void set_rowAndColumn(){

        assertEquals(6, table.ROWS);
        assertEquals(7, table.COLUMNS);
    }

    @Test
    void table_is_initialized_with_empty(){
        for(int i = 0; i < table.ROWS; i++){
            for(int j = 0; j < table.COLUMNS; j++){
                assertTrue(table.get(i,j).isEmpty());
            }
        }
    }

    @Test
    void place_token_in_table(){
        table.get(0,0).placePlayer1();
        assertTrue(table.get(0,0).isPlayer1());

        table.get(0,1).placePlayer2();
        assertTrue(table.get(0,1).isPlayer2());
    }

    @Test
    void place_token_in_illegal_position(){
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> table.get(7,0).placePlayer1());
        assertThrows(IllegalArgumentException.class,
                () -> table.get(6,0).placePlayer1());

        assertEquals("Illegal position: 7,0", exception.getMessage());
    }

    @Test
    void cant_place_token_in_invalid_column(){
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> table.placePlayer1AtColumn(7));
        assertEquals("Illegal column: 7", exception.getMessage());
    }

    @Test
    void place_token_in_valid_column(){
        table.placePlayer1AtColumn(0);
        assertTrue(table.get(0,0).isPlayer1());
        assertFalse(table.get(1,0).isPlayer1());
        table.placePlayer1AtColumn(0);
        assertTrue(table.get(0,0).isPlayer1());
        assertTrue(table.get(1,0).isPlayer1());

        table.placePlayer2AtColumn(0);
        assertTrue(table.get(2,0).isPlayer2());
        table.placePlayer2AtColumn(1);
        assertTrue(table.get(0,1).isPlayer2());
    }

    @Test
    void cant_place_token_in_column_full(){
        smallTable = new Table(2,2);

        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(1);

        Exception exception = assertThrows(Column.ColumnFullException.class,
                () -> smallTable.placePlayer1AtColumn(1));
        assertEquals("Column 1 is full", exception.getMessage());
    }

    @Test
    void is_connect_4_by_column_only_player1(){
        smallTable = new Table(4,4);

        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);

        assertTrue(smallTable.isConnect4());
    }

    @Test
    void is_connect_4_by_diagonal_positive_slope(){
        smallTable = new Table(4,4);

        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(2);
        smallTable.placePlayer2AtColumn(3);

        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(2);
        smallTable.placePlayer2AtColumn(3);

        smallTable.placePlayer1AtColumn(2);
        smallTable.placePlayer2AtColumn(3);

        smallTable.placePlayer1AtColumn(3);

        System.out.println(smallTable.toString());
        assertTrue(smallTable.isConnect4());
    }
    //toString is
    @Test
    void toString_print_table(){
        smallTable = new Table(2,2);

        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer2AtColumn(1);

        assertEquals("[ ,  ]\n[X, O]\n", smallTable.toString());
    }

    @Test
    void is_connect_4_by_row_only_player1(){
        smallTable = new Table(5,5);

        smallTable.placePlayer2AtColumn(0);
        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(2);
        smallTable.placePlayer1AtColumn(3);
        smallTable.placePlayer1AtColumn(4);

        assertTrue(smallTable.isConnect4());
    }

    @Test
    void is_not_connect_4_by_column(){
        smallTable = new Table(6,4);

        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer2AtColumn(0);
        smallTable.placePlayer1AtColumn(0);

        assertFalse(smallTable.isConnect4());
    }

    @Test
    void is_not_connect_4_by_row(){
        smallTable = new Table(6,5);

        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(2);
        smallTable.placePlayer2AtColumn(3);
        smallTable.placePlayer1AtColumn(4);

        assertFalse(smallTable.isConnect4());
    }

    @Test
    void cat_get_position_of_connect_4() {
        smallTable = new Table(6, 4);

        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer2AtColumn(0);

        Exception exception = assertThrows(IllegalStateException.class,
                () -> smallTable.getPositionsOfConnect4());
        assertEquals("No connect 4 found", exception.getMessage());
    }

    @Test
    void get_positions_of_connect4_column(){
        smallTable = new Table(6, 4);

        smallTable.placePlayer2AtColumn(0);
        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);
        smallTable.placePlayer1AtColumn(0);

        smallTable.placePlayer2AtColumn(1);
        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(1);

        assertEquals(2, smallTable.getPositionsOfConnect4().size());
        var positionPair = new PositionPair(new Position(1,0, smallTable),
                new Position(4,0, smallTable));
        var positionPair2 = new PositionPair(new Position(1,1, smallTable),
                new Position(4,1, smallTable));
        assertEquals(positionPair, smallTable.getPositionsOfConnect4().get(0));
        assertEquals(positionPair2, smallTable.getPositionsOfConnect4().get(1));
    }

    @Test
    void get_positions_of_connect4_row(){
        smallTable = new Table(6, 5);

        smallTable.placePlayer2AtColumn(0);
        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(2);
        smallTable.placePlayer1AtColumn(3);
        smallTable.placePlayer1AtColumn(4);

        smallTable.placePlayer2AtColumn(0);
        smallTable.placePlayer1AtColumn(1);
        smallTable.placePlayer1AtColumn(2);
        smallTable.placePlayer1AtColumn(3);
        smallTable.placePlayer1AtColumn(4);

        assertEquals(2, smallTable.getPositionsOfConnect4().size());
        var positionPair = new PositionPair(new Position(0,1, smallTable),
                new Position(0,4, smallTable));
        var positionPair2 = new PositionPair(new Position(1,1, smallTable),
                new Position(1,4, smallTable));
        assertEquals(positionPair, smallTable.getPositionsOfConnect4().get(0));
        assertEquals(positionPair2, smallTable.getPositionsOfConnect4().get(1));
    }


}