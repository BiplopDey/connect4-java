package com.minimax.minimax.connect4.service;

import com.minimax.minimax.connect4.domain.ColumnFullException;
import com.minimax.minimax.connect4.domain.Table;
import com.minimax.minimax.connect4.service.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameTest {
    Game game;
    Table table;
    @BeforeEach
    void setUp() {
        table = mock(Table.class);
        game = new Game( table );
    }

    @Test
    void column_is_full_and_throws_ColumnFullException() {
        Exception sut = assertThrows(ColumnFullException.class,
                () -> game.placePlayer1AtColumn(0));

    }
}