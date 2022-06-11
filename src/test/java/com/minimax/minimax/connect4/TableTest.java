package com.minimax.minimax.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table table6x7, table2x2, table;

    private final Position.STATE PLAYER_1 = Position.STATE.PLAYER_1;
    private final Position.STATE PLAYER_2 = Position.STATE.PLAYER_2;
    private final Position.STATE EMPTY = Position.STATE.EMPTY;

    @BeforeEach
    void setUp() {
        table6x7 = new Table(6,7);
        table2x2 = new Table(2,2);
    }

    @Test
    void set_rowAndColumn(){
        assertEquals(6, table6x7.ROWS);
        assertEquals(7, table6x7.COLUMNS);
    }

    @Test
    void table_is_initialized_with_empty(){
        for(int i = 0; i < table6x7.ROWS; i++)
            for(int j = 0; j < table6x7.COLUMNS; j++)
                assertTrue(table6x7.getPosition(i,j).isEmpty());
    }

    @Test
    void place_token_in_table(){
        var sut = table6x7.getPosition(0,1);

        assertEquals(new Position(table6x7).of(0,1), sut);
    }

    @Test
    void place_token_in_illegal_position(){
        Exception sut = assertThrows(IllegalArgumentException.class,
                () -> table6x7.getPosition(7,0).placePlayer1());
        assertThrows(IllegalArgumentException.class,
                () -> table6x7.getPosition(6,0).placePlayer1());

        assertEquals("Illegal position: 7,0", sut.getMessage());
    }

    @Test
    void cant_place_token_in_invalid_column(){
        Exception sut = assertThrows(IllegalArgumentException.class,
                () -> table6x7.placePlayer1AtColumn(7));

        assertEquals("Illegal column: 7", sut.getMessage());
    }

    @Test
    void place_token_in_valid_column(){
        putInColumn(table6x7, 0, PLAYER_1, PLAYER_1, PLAYER_2);
        table6x7.placePlayer2AtColumn(1);

        assertTrue(table6x7.getPosition(0,0).isPlayer1());
        assertTrue(table6x7.getPosition(1,0).isPlayer1());
        assertTrue(table6x7.getPosition(2,0).isPlayer2());
        assertTrue(table6x7.getPosition(3,0).isEmpty());

        assertTrue(table6x7.getPosition(0,1).isPlayer2());
        assertTrue(table6x7.getPosition(1,1).isEmpty());
    }

    @Test
    void cant_place_token_in_column_full(){
        putInColumn(table2x2, 1, PLAYER_1, PLAYER_1);

        Exception sut = assertThrows(Column.ColumnFullException.class,
                () -> table2x2.placePlayer1AtColumn(1));

        assertEquals("Column 1 is full", sut.getMessage());
    }

    @Test
    void is_connect_4_by_column_only_player1(){
        table = new Table(4,4);

        putInColumn(table,1, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);

        assertTrue(table.isConnect4());
    }

    @Test
    void is_connect_4_by_diagonal_positive_slope() {
        table = new Table(4, 4);

        putInAllColumns(table,'X','X','X','O');
        putInAllColumns(table,' ','X','X','O');
        putInAllColumns(table,' ',' ','X','O');
        putInAllColumns(table,' ',' ',' ','X');

        assertTrue(table.isConnect4());
        assertThat(table.getPositionsOfConnect4()).hasSize(1);
        assertEquals("[Position(0,0), Position(3,3)]",
                table.getPositionsOfConnect4().get(0).toString());
    }

    @Test
    void is_connect_4_by_diagonal_positive_slope_shifted() {
        table = new Table(5, 5);

        putInAllColumns(table,' ','X','X','X','O');
        putInAllColumns(table,' ',' ','X','X','O');
        putInAllColumns(table,' ',' ',' ','X','O');
        putInAllColumns(table,' ',' ',' ',' ','X');

        assertTrue(table.isConnect4());
        assertThat(table.getPositionsOfConnect4()).hasSize(1);
        assertEquals("[Position(0,1), Position(3,4)]",
                table.getPositionsOfConnect4().get(0).toString());
    }

    @Test
    void is_connect_4_by_diagonal_negative_slope() {
        table = new Table(4, 4);

        putInAllColumns(table,'X','X','X','O');
        putInAllColumns(table,'X','X','O','O');
        putInAllColumns(table,'X','O','X','O');
        putInAllColumns(table,'O',' ',' ',' ');

        assertTrue(table.isConnect4());
        assertThat(table.getPositionsOfConnect4()).hasSize(1);
        assertEquals("[Position(3,0), Position(0,3)]",
                table.getPositionsOfConnect4().get(0).toString());
    }
    @Test
    void toString_print_table(){
        putInAllColumns(table2x2, PLAYER_1, PLAYER_2);

        var expected =  "[ ,  ]\n" +
                        "[X, O]\n";
        assertEquals(expected, table2x2.toString());
    }

    @Test
    void is_connect_4_by_row_only_player1(){
        table = new Table(5,6);

        putInAllColumns(table, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1, EMPTY);

        assertTrue(table.isConnect4());
    }

    @Test
    void is_not_connect_4_by_column(){
        table = new Table(6,4);

        putInColumn(table, 0, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_2, PLAYER_1);

        assertFalse(table.isConnect4());
    }

    @Test
    void is_not_connect_4_by_row(){
        table = new Table(6,5);

        putInAllColumns(table, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_2, PLAYER_1);

        assertFalse(table.isConnect4());
    }

    @Test
    void cat_get_position_of_connect_4() {
        table = new Table(6, 4);
        putInColumn(table, 0, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_2);

        Exception sut = assertThrows(IllegalStateException.class,
                () -> table.getPositionsOfConnect4());

        assertEquals("No connect 4 found", sut.getMessage());
    }

    @Test
    void get_positions_of_connect4_column(){
        table = new Table(6, 4);

        putInColumn(table, 0, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);
        putInColumn(table, 1, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);

        var positionPairColumn0 = new PositionPair(
                table.getPosition(1,0),
                table.getPosition(4,0));
        var positionPairColumn1 = new PositionPair(
                table.getPosition(1,1),
                table.getPosition(4,1));
        assertEquals(2, table.getPositionsOfConnect4().size());
        assertEquals(positionPairColumn0, table.getPositionsOfConnect4().get(0));
        assertEquals(positionPairColumn1, table.getPositionsOfConnect4().get(1));
    }

    @Test
    void get_positions_of_connect4_row(){
        table = new Table(6, 5);

        putInAllColumns(table, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);
        putInAllColumns(table, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);

        var positionPair = new PositionPair(table.getPosition(0,1),
                table.getPosition(0,4));
        var positionPair2 = new PositionPair(table.getPosition(1,1),
                table.getPosition(1,4));
        assertEquals(2, table.getPositionsOfConnect4().size());
        assertEquals(positionPair, table.getPositionsOfConnect4().get(0));
        assertEquals(positionPair2, table.getPositionsOfConnect4().get(1));
    }

    private void putInColumn(Table table, int columnIndex, Position.STATE... players) {
        var column = table.getColumn(columnIndex);
        for (Position.STATE player: players)
            column.put(player);
    }

    private void putInAllColumns(Table table, Character... states) {
        for (int i = 0; i < states.length; i++){
            if (states[i].equals('X'))
                table.placePlayer1AtColumn(i);
            if (states[i].equals('O'))
                table.placePlayer2AtColumn(i);
        }
    }
    private void putInAllColumns(Table table, Position.STATE... states) {
        for (int i = 0; i < states.length; i++){
            if (states[i] == PLAYER_1)
                table.placePlayer1AtColumn(i);
            if (states[i] == PLAYER_2)
                table.placePlayer2AtColumn(i);
        }
    }
}