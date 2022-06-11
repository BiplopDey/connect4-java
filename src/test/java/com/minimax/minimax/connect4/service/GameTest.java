package com.minimax.minimax.connect4.service;

import com.minimax.minimax.connect4.domain.ColumnFullException;
import com.minimax.minimax.connect4.domain.Table;
import com.minimax.minimax.connect4.service.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game( new Table(6, 7) );
    }

    @Test
    void column_is_full_and_throws_ColumnFullException() {

        Exception sut = assertThrows(ColumnFullException.class,
                () -> game.placePlayer1AtColumn(0));

    }
}