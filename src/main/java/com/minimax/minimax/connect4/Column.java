package com.minimax.minimax.connect4;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private final List<Position> list;

    public Column(int column, Table table){
        this.list = new ArrayList<>();
        for(int row = 0; row < table.ROWS; row++)
            list.add(table.get(row, column));
    }

    public boolean isFull() {
        return list.stream().filter(p -> p.isEmpty()).count() == 0;
    }

    public Column put(Position.STATE state) {
        for(Position p : list) {
            if(p.isEmpty()) {
                p.place(state);
            return this;
            }
        }
        throw new IllegalArgumentException("Column is full");
    }

    public List<Position> getList() {
        return list;
    }

    public boolean isConnect4() {
        return list.stream().filter(p -> p.isPlayer1()).count() == 4 ||
                list.stream().filter(p -> p.isPlayer2()).count() == 4;
    }
}
