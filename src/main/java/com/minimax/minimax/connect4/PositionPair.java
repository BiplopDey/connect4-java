package com.minimax.minimax.connect4;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class PositionPair {
    private final List<Position> list = new ArrayList<>(2);
    public PositionPair(Position position, Position position1) {
        list.add(position);
        list.add(position1);
    }

    // toString list
    @Override
    public String toString() {
        return list.toString();
    }
}
