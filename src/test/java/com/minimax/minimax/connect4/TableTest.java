package com.minimax.minimax.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table table;

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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> table.get(7,0).placePlayer1());
        assertThrows(IllegalArgumentException.class, () -> table.get(6,0).placePlayer1());

        assertEquals("Illegal position: 7,0", exception.getMessage());
    }
}