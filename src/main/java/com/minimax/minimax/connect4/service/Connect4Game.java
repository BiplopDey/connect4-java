package com.minimax.minimax.connect4.service;

import com.minimax.minimax.connect4.domain.CellPair;
import com.minimax.minimax.connect4.domain.Connect4Table;
import com.minimax.minimax.connect4.domain.Player;

import java.util.List;

public interface Connect4Game {
    void place(Player player, int column);
    Connect4Table getTable();
    Player getWinner();
    STATUS getStatus();
    List<CellPair> getCellsOfConnect4();
    enum STATUS {
        PLAYING,
        DRAW,
        WINNER
    }
}
