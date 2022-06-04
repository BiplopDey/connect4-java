package com.minimax.minimax.connect4;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


public class PositionPair {
    private final List<Position> list = new ArrayList<>(2);

    public PositionPair(Position position, Position position1) {
        list.add(position);
        list.add(position1);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    //equals the elements of the list
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionPair that = (PositionPair) o;
        return list.equals(that.list);
    }
}
