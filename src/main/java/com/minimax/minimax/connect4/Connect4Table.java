package com.minimax.minimax.connect4;

public interface Connect4Table {
    boolean isConnect4();
    void placeAtColumn(int columnIndex, Position.STATE player);
    
}
