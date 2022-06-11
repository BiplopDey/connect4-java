package com.minimax.minimax.connect4.domain;

public interface Connect4Table {
    boolean isConnect4();
    void placePlayer2AtColumn(int column);
    void placePlayer1AtColumn(int column);
}
