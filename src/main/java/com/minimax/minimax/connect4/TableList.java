package com.minimax.minimax.connect4;

import java.util.Arrays;
import java.util.List;

public class TableList {
    protected final List<Position> list;
    private PositionPair positionPair;

    public TableList(List<Position> list) {
        this.list = list;
    }

    private boolean areConnected(Position.STATE player, Position... positions) {
        return Arrays.stream(positions).allMatch(p -> p.is(player));
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

    public List<Position> getPositions() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    public Position get(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }

    //equals the elements of the list
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableList that = (TableList) o;
        return list.equals(that.list);
    }
}
