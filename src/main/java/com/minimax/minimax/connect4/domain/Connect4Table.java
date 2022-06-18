package com.minimax.minimax.connect4.domain;

import java.util.List;

public interface Connect4Table {
    boolean isConnect4();
    void placePlayer2AtColumn(int column) throws ColumnFullException, InvalidColumnIndexException;
    void placePlayer1AtColumn(int column) throws ColumnFullException, InvalidColumnIndexException;
    char[][] getTable();
    List<CellPair> getCellsOfConnect4();

    boolean isFull();
    List<Column> getAllColumns();
    class InvalidColumnIndexException extends IllegalArgumentException {
        public InvalidColumnIndexException(String message) {
            super(message);
        }
    }
}
