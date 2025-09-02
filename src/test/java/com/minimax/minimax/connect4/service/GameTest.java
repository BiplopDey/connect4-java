package com.minimax.minimax.connect4.service;

import com.minimax.minimax.connect4.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {
    Game game;
    Connect4Table table;
    @BeforeEach
    void setUp() {
        table = mock(Table.class);
        game = new Game( table );
    }


    @Test
    void column_is_invalid_and_throw_InvalidColumnIndexException() {
        game.place(Player.PLAYER_1, 1);

        verify(table).placePlayer1AtColumn(0);
    }

    @Test
    void player1_is_winner() {
        Cell cell = mock(Cell.class);
        when(cell.isPlayer1()).thenReturn(true);
        when(table.getCellsOfConnect4()).thenReturn(List.of(new CellPair(cell, cell)));

        assertEquals(Player.PLAYER_1, game.getWinner());
    }

    @Test
    void player2_is_winner() {
        Cell cell = mock(Cell.class);
        when(cell.isPlayer1()).thenReturn(false);
        when(table.getCellsOfConnect4()).thenReturn(List.of(new CellPair(cell, cell)));
        assertEquals(Player.PLAYER_2, game.getWinner());
    }

    @Test
    void game_status_is_draw() {
        when(table.isConnect4()).thenReturn(false);
        when(table.isFull()).thenReturn(true);

        assertEquals(Game.STATUS.DRAW, game.getStatus());
    }

    @Test
    void game_status_is_playing() {
        when(table.isConnect4()).thenReturn(false);
        when(table.isFull()).thenReturn(false);

        assertEquals(Game.STATUS.PLAYING, game.getStatus());
    }

    @Test
    void game_status_winner(){
        when(table.isConnect4()).thenReturn(true);

        assertEquals(Game.STATUS.WINNER, game.getStatus());
        verify(table, times(0)).isFull();
    }


}
