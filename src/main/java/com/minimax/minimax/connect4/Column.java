package com.minimax.minimax.connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Column {
    private final List<Position> list;
    private PositionPair positionPair;
    public Column(int column, Table table){
        this.list = IntStream.range(0, table.ROWS)
                .mapToObj(row -> table.get(row, column))
                .collect(Collectors.toList());
    }

    public boolean isFull() {
        return list.stream().noneMatch(Position::isEmpty);
    }

    public Column put(Position.STATE state) {
        for(Position p : list) {
            if(p.isEmpty()) {
                p.place(state);
            return this;
            }
        }
        throw new ColumnFullException("Column is full");
    }

    public List<Position> getList() {
        return list;
    }

    private boolean areConnected(Position.STATE player, Position... positions) {
        for(Position p : positions)
            if(p.getValue() != player.getState())
                return false;
        return true;
    }

    public boolean isConnect4() {
        for(int i = 0; i < list.size() - 3; i++) {
            Position p1 = list.get(i);
            Position p2 = list.get(i + 1);
            Position p3 = list.get(i + 2);
            Position p4 = list.get(i + 3);
            if (areConnected(Position.STATE.PLAYER_1, p1, p2, p3, p4)
                    || areConnected(Position.STATE.PLAYER_2, p1, p2, p3, p4)){
                positionPair = new PositionPair(p1, p4);
                return true;
            }
        }
        return false;
    }

    public PositionPair getPositionPair() {
        if(!isConnect4())
            throw new IllegalStateException("Column is not connect4");
        return positionPair;
    }

    public static class ColumnFullException extends RuntimeException {
        public ColumnFullException(String message) {
            super(message);
        }
    }
}
