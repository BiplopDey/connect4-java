package com.minimax.minimax.connect4.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table table6x7, table2x2, table;
    private final Player PLAYER_1 = Player.PLAYER_1;
    private final Player PLAYER_2 = Player.PLAYER_2;

    @BeforeEach
    void setUp() {
        table6x7 = new Table(6,7);
        table2x2 = new Table(2,2);
    }

    @Test
    void set_rowAndColumn(){
        assertEquals(6, table6x7.getRowSize());
        assertEquals(7, table6x7.getColumnSize());
    }

    @Test
    void set_rowAndColumnTableList(){
        var table = List.of(
                List.of(PLAYER_1),
                List.of(PLAYER_1),
                List.of(PLAYER_1, PLAYER_2));

        var sut = new Table(2, table);

        assertEquals(2, sut.getRowSize());
        assertEquals(3, sut.getColumnSize());
        assertTrue(sut.getCell(0, 0).isPlayer1());
        assertTrue(sut.getCell(1, 0).isEmpty());
        assertTrue(sut.getCell(0, 1).isPlayer1());
    }

    @Test
    void allColumnsShoulBeLessThanColumnSizeSpecified(){
        var table = List.of(
                List.of(PLAYER_1),
                List.of(PLAYER_1, PLAYER_2, PLAYER_1));

        Exception sut = assertThrows( IllegalArgumentException.class, () -> new Table(2, table));
        assertEquals("All columns length should be less than row size specified", sut.getMessage());
    }

    @Test
    void table_is_initialized_with_empty(){
        for(int i = 0; i < table6x7.getRowSize(); i++)
            for(int j = 0; j < table6x7.getColumnSize(); j++)
                assertTrue(table6x7.getCell(i,j).isEmpty());
    }

    @Test
    void place_token_in_table(){
        var sut = table6x7.getCell(0,1);

        assertEquals(new Cell(table6x7).of(0,1), sut);
    }

    @Test
    void place_token_in_invalid_row(){
        Exception sut = assertThrows(IllegalArgumentException.class,
                () -> table6x7.getCell(6,0).placePlayer1());

        assertEquals("Illegal row: 6", sut.getMessage());
    }

    @Test
    void cant_place_token_in_invalid_column(){
        Exception sut = assertThrows(Connect4Table.InvalidColumnIndexException.class,
                () -> table6x7.placePlayer1AtColumn(7));

        assertEquals("Illegal column: 7", sut.getMessage());
    }

    @Test
    void place_token_in_valid_column(){
        putInColumn(table6x7, 0, PLAYER_1, PLAYER_1, PLAYER_2);
        table6x7.placePlayer2AtColumn(1);

        assertTrue(table6x7.getCell(0,0).isPlayer1());
        assertTrue(table6x7.getCell(1,0).isPlayer1());
        assertTrue(table6x7.getCell(2,0).isPlayer2());
        assertTrue(table6x7.getCell(3,0).isEmpty());

        assertTrue(table6x7.getCell(0,1).isPlayer2());
        assertTrue(table6x7.getCell(1,1).isEmpty());
    }

    @Test
    void cant_place_token_in_column_full(){
        putInColumn(table2x2, 1, PLAYER_1, PLAYER_1);

        Exception sut = assertThrows(ColumnFullException.class,
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

        putRow(table,'X','X','X','O');
        putRow(table,' ','X','X','O');
        putRow(table,' ',' ','X','O');
        putRow(table,' ',' ',' ','X');

        assertTrue(table.isConnect4());
        assertThat(table.getCellsOfConnect4()).hasSize(1);
        assertEquals("[Position(0,0), Position(3,3)]",
                table.getCellsOfConnect4().get(0).toString());
    }

    @Test
    void is_connect_4_by_diagonal_positive_slope_shifted() {
        table = new Table(5, 5);

        putRow(table,' ','X','X','X','O');
        putRow(table,' ',' ','X','X','O');
        putRow(table,' ',' ',' ','X','O');
        putRow(table,' ',' ',' ',' ','X');

        assertTrue(table.isConnect4());
        assertThat(table.getCellsOfConnect4()).hasSize(1);
        assertEquals("[Position(0,1), Position(3,4)]",
                table.getCellsOfConnect4().get(0).toString());
    }

    @Test
    void is_connect_4_by_diagonal_negative_slope() {
        table = new Table(4, 4);

        putRow(table,'X','X','X','O');
        putRow(table,'X','X','O','O');
        putRow(table,'X','O','X','O');
        putRow(table,'O',' ',' ',' ');

        assertTrue(table.isConnect4());
        assertThat(table.getCellsOfConnect4()).hasSize(1);
        assertEquals("[Position(3,0), Position(0,3)]",
                table.getCellsOfConnect4().get(0).toString());
    }

    @Test
    void toString_print_table(){
        putRow(table2x2, PLAYER_1, PLAYER_2);

        var expected =  "[ ,  ]\n" +
                        "[X, O]\n";
        assertEquals(expected, table2x2.toString());
    }

    @Test
    void is_full(){
        putRow(table2x2, PLAYER_1, PLAYER_2);
        putRow(table2x2, PLAYER_1, PLAYER_2);
        assertTrue(table2x2.isFull());
    }

    @Test
    void is_not_full(){
        putRow(table2x2, PLAYER_1, PLAYER_2);
        assertFalse(table2x2.isFull());
    }

    @Test
    void is_connect_4_by_row_only_player1(){
        table = new Table(5,6);

        putRow(table, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);

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

        putRow(table, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_2, PLAYER_1);

        assertFalse(table.isConnect4());
    }

    @Test
    void cat_get_position_of_connect_4() {
        table = new Table(6, 4);
        putInColumn(table, 0, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_2);

        Exception sut = assertThrows(IllegalStateException.class,
                () -> table.getCellsOfConnect4());

        assertEquals("No connect 4 found", sut.getMessage());
    }

    @Test
    void get_positions_of_connect4_column(){
        table = new Table(6, 4);

        putInColumn(table, 0, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);
        putInColumn(table, 1, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);

        var positionPairColumn0 = new CellPair(
                table.getCell(1,0),
                table.getCell(4,0));
        var positionPairColumn1 = new CellPair(
                table.getCell(1,1),
                table.getCell(4,1));
        assertEquals(2, table.getCellsOfConnect4().size());
        assertEquals(positionPairColumn0, table.getCellsOfConnect4().get(0));
        assertEquals(positionPairColumn1, table.getCellsOfConnect4().get(1));
    }

    @Test
    void get_positions_of_connect4_row(){
        table = new Table(6, 5);

        putRow(table, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);
        putRow(table, PLAYER_2, PLAYER_1, PLAYER_1, PLAYER_1, PLAYER_1);

        var positionPair = new CellPair(table.getCell(0,1),
                table.getCell(0,4));
        var positionPair2 = new CellPair(table.getCell(1,1),
                table.getCell(1,4));
        assertEquals(2, table.getCellsOfConnect4().size());
        assertEquals(positionPair, table.getCellsOfConnect4().get(0));
        assertEquals(positionPair2, table.getCellsOfConnect4().get(1));
    }

    private void putInColumn(Table table, int columnIndex, Player... players) {
        var column = table.getColumn(columnIndex);
        for (Player player: players)
            column.put(player);
    }

    private void putRow(Table table, Character... states) {
        for (int i = 0; i < states.length; i++){
            if (states[i].equals('X'))
                table.placePlayer1AtColumn(i);
            if (states[i].equals('O'))
                table.placePlayer2AtColumn(i);
        }
    }
    private void putRow(Table table, Player... players) {
        for (int i = 0; i < players.length; i++){
            if (players[i] == PLAYER_1)
                table.placePlayer1AtColumn(i);
            if (players[i] == PLAYER_2)
                table.placePlayer2AtColumn(i);
        }
    }
}
