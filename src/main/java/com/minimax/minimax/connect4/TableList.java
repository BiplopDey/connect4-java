package com.minimax.minimax.connect4;

import java.util.List;

public abstract class TableList {
    protected final List<Position> list;
    private PositionPair positionPair;

    public TableList(List<Position> list) {
        this.list = list;
    }

    private boolean areConnected(Position.STATE player, Position... positions) {
        for(Position p : positions)
            if(p.getValue() != player.getValue())
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

    public List<Position> getList() {
        return list;
    }

    //toString list
    @Override
    public String toString() {
        return list.toString();
    }


}
